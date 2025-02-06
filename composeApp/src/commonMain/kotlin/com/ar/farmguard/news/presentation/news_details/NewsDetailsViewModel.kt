package com.ar.farmguard.news.presentation.news_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.news.domian.model.NewsDetailsUiState
import com.ar.farmguard.news.domian.repository.NewsRepository
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsDetailsViewModel(
    private val newsRepository: NewsRepository
): ViewModel(){

    private val l = Logger.withTag("NewsDetailsViewModel")

    private val _newsState = MutableStateFlow(NewsDetailsUiState())
    val newsState = _newsState.asStateFlow()


    fun fetchNewsDetails(
        shortTag: String,
    ){
        l.e(shortTag)
        _newsState.value = _newsState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                newsRepository.getNewsDetails(shortTag)
                    .onSuccess {
                        _newsState.value = _newsState.value.copy(
                            newsDetails = it,
                            isLoading = true,
                            message = null
                        )
                    }
                    .onError {
                        _newsState.value = _newsState.value.copy(
                            isLoading = false,
                            message = Message(
                                key = MessageKey.NEWS_INFO,
                                status = MessageStatus.ERROR,
                                uiText = it.toUiText()
                            )
                        )
                    }
            }
        }
    }

}