package com.ar.farmguard.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.app.presentation.util.UiText
import com.ar.farmguard.app.utils.COORDINATES_KEY
import com.ar.farmguard.core.domain.location.LocationProvider
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.home.domain.model.HomeState
import com.ar.farmguard.news.domian.repository.NewsRepository
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.weather.domain.models.response.CurrentWeather
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastAstro
import com.ar.farmguard.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val locationProvider: LocationProvider,
    private val dataSore: DataStore<Preferences>,
    private val weatherRepository: WeatherRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {

    val l = Logger.withTag("HomeViewModel")

    private val coordinateKey = stringPreferencesKey(COORDINATES_KEY)

    private val currentWeather = weatherRepository.forecast



    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.combine(currentWeather) { state, currentWeather ->
        state.copy(
            currentWeather = currentWeather?.current ?: CurrentWeather(),
            location = currentWeather?.location ?: LocationInfo(),
            astro = currentWeather?.forecast?.forecastData?.first()?.astro ?: ForecastAstro()
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())


    init {
        viewModelScope.launch {
            val weatherDeferred = async(Dispatchers.IO) { if (currentWeather.value == null) getWeather() }
            val newsDeferred = async(Dispatchers.IO) { getStateNews("haryana") }
            val breakingNewsDeferred = async(Dispatchers.IO) { getBreakingNews() }
            val headlinesDeferred = async(Dispatchers.IO) { getTopHeadlines() }

            weatherDeferred.await()
            newsDeferred.await()
            breakingNewsDeferred.await()
            headlinesDeferred.await()
        }
    }

    private fun getWeather(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val location = locationProvider.getCurrentLocation()
                if(location == null){
                    _homeState.value = _homeState.value.copy(
                        message = Message(
                            key = MessageKey.FORECAST_INFO,
                            uiText = UiText.DynamicString("We Don't Have Your Location Yet"),
                            status = MessageStatus.ERROR
                        )
                    )
                    if(currentWeather.value == null) {
                        fetchWeatherData(null)
                    }
                }else{

                    val coordinates = "${location.latitude},${location.longitude}"

                    setCoordinates(coordinates)

                    if(currentWeather.value == null) {
                        fetchWeatherData(coordinates)
                    }
                }

            }
        }
    }


    private fun fetchWeatherData(coordinates: String? = null){
        var coordinate = coordinates
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val dataset = dataSore.data.first()


                if(dataset.contains(coordinateKey)){

                    _homeState.value = _homeState.value.copy(
                        isWeatherLoading = true
                    )


                    if(coordinate == null ){
                        coordinate = getCoordinates()
                    }

                    coordinate?.let {
                        weatherRepository.fetchWeather(
                            coordinate = it,
                        ).onSuccess { data ->
                            _homeState.value = _homeState.value.copy(
                                isWeatherLoading = false
                            )
                        }.onError {
                            _homeState.value = _homeState.value.copy(
                                message = Message(
                                    key = MessageKey.FORECAST_INFO,
                                    uiText = it.toUiText(),
                                    status = MessageStatus.ERROR
                                )
                            )
                        }
                    }
                }
            }
        }
    }


    private fun setCoordinates(coordinates: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataSore.edit {
                    it[coordinateKey] = coordinates
                }
            }
        }
    }


    private suspend fun getCoordinates(): String? {
        return withContext(Dispatchers.IO){
            dataSore.data.first()[coordinateKey]
        }
    }


    private fun getStateNews(state: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                newsRepository.getStateNews(state = state)
                    .onSuccess {
                        _homeState.value = _homeState.value.copy(
                            stateNews = it
                        )
                    }.onError {
                        _homeState.value = _homeState.value.copy(
                            message = Message(
                                key = MessageKey.NEWS_INFO,
                                uiText = it.toUiText(),
                                status = MessageStatus.ERROR
                            )
                        )

                    }
            }
        }
    }

    private fun getBreakingNews(
        tryCount: Int = 0
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                newsRepository.getBreakingNews()
                    .onSuccess {
                        _homeState.value = _homeState.value.copy(
                            breakingNewsList = it
                        )
                    }.onError {
                        if(tryCount < 3){
                            getBreakingNews()
                        }
                    }
            }
        }
    }

    private fun getTopHeadlines(tryCount: Int = 0){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                newsRepository.getTopHeadlines()
                    .onSuccess {
                        _homeState.value = _homeState.value.copy(
                            headlines = it
                        )
                    }
                    .onError{
                        if(tryCount < 3){
                            getTopHeadlines()
                        }
                    }
            }
        }
    }


}