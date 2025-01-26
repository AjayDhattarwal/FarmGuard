@file:OptIn(FlowPreview::class, FlowPreview::class)

package com.ar.farmguard.marketprice.presentation.market_home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.app.presentation.util.UiText
import com.ar.farmguard.app.utils.APMC_KEY
import com.ar.farmguard.app.utils.LANGUAGE_KEY
import com.ar.farmguard.app.utils.STATE_KEY
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.getDateSevenDaysBack
import com.ar.farmguard.core.presentation.getTodayDate
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.marketprice.domain.model.state.CommodityState
import com.ar.farmguard.marketprice.domain.model.state.MarketState
import com.ar.farmguard.marketprice.domain.model.state.UserCommodityState
import com.ar.farmguard.marketprice.domain.model.dto.toTradeDataRequest
import com.ar.farmguard.marketprice.domain.model.remote.EnamApmc
import com.ar.farmguard.marketprice.domain.model.remote.EnamState
import com.ar.farmguard.marketprice.domain.repository.EnamMandiRepository
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketCommodityViewModel (
    private val repository: EnamMandiRepository,
    private val dataStore: DataStore<Preferences>
): ViewModel() {


    private val commodityState = stringPreferencesKey(STATE_KEY)
    private val commodityApmc = stringPreferencesKey(APMC_KEY)
    private val commodityLanguage = stringPreferencesKey(LANGUAGE_KEY)

    private var cacheState = EnamState()
    private var cacheApmc = mutableMapOf<String, EnamApmc>()


    private val l = Logger.withTag("MarketPriceViewModel")

    private val _userDataState = MutableStateFlow(UserCommodityState())
    val userDataState = _userDataState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()


    private val _message = MutableStateFlow<Message?>(null)
    val message = _message.asStateFlow()

    private var _data  =  MutableStateFlow<List<CommodityState>>(emptyList())
    private val data = _data.asStateFlow()


    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    private val _state = MutableStateFlow(MarketState())
    val state = combine(_state, _searchQuery, data) { state, query, data ->
        state.copy(
            commodityWithHistory = if (query.isEmpty()) {
                 data
            } else {
                data.filter { it.matchesSearchQuery(query) }
            }
        )
    }.debounce(200L).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MarketState()
    )



    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val sharedPref = dataStore.data.first()
                val stateLocation = sharedPref[commodityState] ?: ""
                val apmc = sharedPref[commodityApmc] ?: ""

                _state.value = MarketState(
                    state = stateLocation,
                    apmc = apmc,
                    language = sharedPref[commodityLanguage] ?: "en",
                    fromDate = getDateSevenDaysBack(),
                    toDate = getTodayDate(),
                    isUserDataAvailable = apmc.isNotEmpty() && stateLocation.isNotEmpty()
                )


                if(_state.value.isUserDataAvailable){
                    getTradeList()
                }else{
                    getStateList()
                }
            }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearData(){
        _searchQuery.value = ""
        _data.value = emptyList()
        getStateList()
        _state.value = _state.value.copy(
            isUserDataAvailable = false
        )

    }


    fun saveUserInfo(key: String, value: String){
        viewModelScope.launch {
            when (key){
                "state" -> {
                    _userDataState.value = _userDataState.value.copy(
                        state = value
                    )

                    val stateId = cacheState.data.find{ states ->
                        states.name.equals(value, ignoreCase = true)
                    }?.stateId

                    stateId?.let { getApmcList(it) }
                }
                "apmc" -> {
                    _userDataState.value = _userDataState.value.copy(
                        apmc = value
                    )

                }
                "language" -> _userDataState.value = _userDataState.value.copy(language = value)

                "submit" -> {
                    _isLoading.value = true
                    _state.value = _state.value.copy(
                        state = _userDataState.value.state,
                        apmc = _userDataState.value.apmc,
                        language = if(_userDataState.value.language == "English") "en" else "hi"
                    )
                    saveUserData()

                }
            }
        }
    }


    private suspend fun saveUserData() {
        withContext(Dispatchers.IO){
            launch{
                dataStore.edit { preferences ->
                    preferences[commodityLanguage] = _state.value.language
                    preferences[commodityApmc] = _state.value.apmc
                    preferences[commodityState] = _state.value.state
                }
                _state.value = _state.value.copy(
                    isUserDataAvailable = true
                )
            }
            launch {
                getTradeList()
            }
        }
    }

    private fun getTradeList() {
        viewModelScope.launch {
            val requestData = _state.value.toTradeDataRequest()

            withContext(Dispatchers.IO) {
                repository.getTradeList(requestData)
                    .onSuccess { response ->
                        if(response != null){
                            _data.value = response
                        }else{
                            _message.value = Message(
                                key = MessageKey.MARKET_HOME_INFO,
                                uiText = UiText.DynamicString("Something went wrong Try after Some time"),
                                status = MessageStatus.ERROR
                            )
                        }

                        _isLoading.value = false

                    }.onError { dataError ->
                        _message.value = Message(
                            key = MessageKey.MARKET_HOME_INFO,
                            uiText = dataError.toUiText(),
                            status = MessageStatus.ERROR
                        )
                    }
            }
        }
    }

    private fun CommodityState.matchesSearchQuery(query: String): Boolean {
        return commodity.lowercase().contains(query.lowercase()) || apmc.lowercase()
            .contains(query.lowercase())

    }


    private fun getStateList(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                if(cacheState.data.isNotEmpty()){
                    return@withContext
                }

                repository.getStateData()
                    .onSuccess { enamState ->
                        if(enamState.status == 200L){
                            cacheState = enamState
                            _userDataState.value = _userDataState.value.copy(
                                stateSelectionList = enamState.data.map{
                                    it.name
                                }
                            )
                        }
                        _isLoading.value = false
                    }
                    .onError {
                        _message.value = Message(
                            key = MessageKey.MARKET_HOME_INFO,
                            uiText = it.toUiText(),
                            status = MessageStatus.ERROR
                        )
                    }
            }
        }
    }

    private fun getApmcList(
        stateId: String
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                if(cacheApmc.containsKey(stateId)){
                    _userDataState.value = _userDataState.value.copy(
                        apmcSelectionList = cacheApmc[stateId]?.data?.mapNotNull {
                            it.apmcName
                        } ?: emptyList()
                    )
                    return@withContext
                }

                repository.getApmcData(stateId)
                    .onSuccess { enamApmc ->
                        if(enamApmc.status == 200L){
                            cacheApmc[stateId] = enamApmc
                            _userDataState.value = _userDataState.value.copy(
                                apmcSelectionList = enamApmc.data.mapNotNull {
                                    it.apmcName
                                }
                            )
                        }else{
                            _message.value = Message(
                                key = MessageKey.MARKET_HOME_INFO,
                                uiText = UiText.DynamicString("Failed to fetch Mandi"),
                                status = MessageStatus.ERROR
                            )
                        }
                    }
                    .onError {
                        _message.value = Message(
                            key = MessageKey.MARKET_HOME_INFO,
                            uiText = it.toUiText(),
                            status = MessageStatus.ERROR
                        )
                    }
            }
        }
    }

}