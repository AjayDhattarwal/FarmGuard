package com.ar.farmguard.weather.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.farmguard.app.utils.COORDINATES_KEY
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.weather.domain.models.WeatherState
import com.ar.farmguard.weather.domain.models.response.CurrentWeather
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastAstro
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastData
import com.ar.farmguard.weather.domain.models.response.toGeminiWeatherRequest
import com.ar.farmguard.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherViewModel(
    private val repository: WeatherRepository,
    private val dataSore: DataStore<Preferences>
): ViewModel() {

    private val currentWeather = repository.forecast

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState.combine(currentWeather) { state, currentWeather ->
        state.copy(
            currentWeather = currentWeather?.current ?: CurrentWeather(),
            location = currentWeather?.location ?: LocationInfo(),
            forecast = currentWeather?.forecast?.forecastData?.drop(1) ?: emptyList(),
            currentForecast = currentWeather?.forecast?.forecastData?.first() ?: ForecastData()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), WeatherState())

    private val coordinateKey = stringPreferencesKey(COORDINATES_KEY)


    init {
        if(currentWeather.value == null){
            fetchWeatherData()
        }else{
            fetchWeatherNote()
        }
    }


    private fun fetchWeatherData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val dataset = dataSore.data.first()


                if(dataset.contains(coordinateKey)){

                    _weatherState.value = _weatherState.value.copy(
                        isLoading = true
                    )

                    val coordinates = dataset.get(coordinateKey)?.split(",")
                    val latitude = coordinates?.first()
                    val longitude = coordinates?.last()

                    if(latitude != null && longitude != null){
                        repository.fetchWeather(
                            coordinate = latitude,
                        ).onSuccess { data ->
                            _weatherState.value = _weatherState.value.copy(
                                isLoading = false
                            )
                            fetchWeatherNote()

                        }.onError {
                            val message = Message(
                                key = MessageKey.FORECAST_INFO,
                                uiText = it.toUiText(),
                                status = MessageStatus.ERROR
                            )

                            _weatherState.value = _weatherState.value.copy(
                                message = message
                            )
                            _weatherState.value = _weatherState.value.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    private fun fetchWeatherLocation(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.getLocationInfo(
                    latitude = "29.3404384",
                    longitude = "75.3290616",
                    lang = "en"
                ).onSuccess {

                }.onError {

                }
            }
        }
    }


    private fun fetchWeatherNote(){
        viewModelScope.launch {
            val data = _weatherState.value.forecast.toGeminiWeatherRequest()
            withContext(Dispatchers.IO){
                repository.getWeatherNote(
                    data
                ).onSuccess {
                    _weatherState.value = _weatherState.value.copy(
                        weatherSummary = it
                    )
                }.onError {

                }
            }
        }
    }



}