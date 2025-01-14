@file:OptIn(FlowPreview::class, FlowPreview::class)

package com.ar.farmguard.marketprice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.app.presentation.util.UiText
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.marketprice.domain.model.CommodityState
import com.ar.farmguard.marketprice.domain.model.MarketState
import com.ar.farmguard.marketprice.domain.model.dto.toTradeDataRequest
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketPriceViewModel (
    private val repository: EnamMandiRepository
): ViewModel() {

    private val l = Logger.withTag("MarketPriceViewModel")

    private val _message = MutableStateFlow<Message?>(null)
    val message = _message.asStateFlow()

    private var _data  =  MutableStateFlow<List<CommodityState>>(emptyList())
    private val data = _data.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow(MarketState())
    val state = combine(_state, _searchQuery, data) { state, query, value ->
        state.copy(
            commodityWithHistory = if (query.isEmpty()) {
                value
            } else {
                value.filter { it.matchesSearchQuery(query) }
            }
        )
    }.debounce(200L).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MarketState()
    )

    init {
        viewModelScope.launch {
            getTradeList()
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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
        return  commodity.lowercase().contains(query.lowercase()) || apmc.lowercase().contains(query.lowercase())
    }

}