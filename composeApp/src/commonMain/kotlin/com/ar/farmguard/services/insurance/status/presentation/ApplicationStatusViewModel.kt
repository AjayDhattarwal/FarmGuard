package com.ar.farmguard.services.insurance.status.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusState
import com.ar.farmguard.services.insurance.status.domain.repository.ApplicationStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationStatusViewModel(
    private val repository: ApplicationStatusRepository
): ViewModel() {


    private val _captchaData = MutableStateFlow(byteArrayOf())
    val captchaData = _captchaData.asStateFlow()

    private val _message = MutableStateFlow<Message?>(null)
    val message = _message.asStateFlow()

    private val _state = MutableStateFlow(ApplicationStatusState())
    val state = _state.asStateFlow()
        .onStart {
            getCaptcha()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ApplicationStatusState()
        )



    fun saveField(key: String, value: TextFieldValue){
        when(key){
            "policyId" -> _state.value = _state.value.copy(
                policyId = value
            )

            "captcha" -> _state.value = _state.value.copy(
                captcha = value
            )
        }
    }

    fun reset(){
        _state.value = ApplicationStatusState()
    }

    fun getCaptcha(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.getCaptcha().onSuccess {
                    _captchaData.value = it
                }.onError {
                    _message.value = Message(
                        key = MessageKey.CAPTCHA_INFO,
                        uiText = it.toUiText(),
                        status = MessageStatus.ERROR
                    )
                }
            }
        }
    }

    fun checkPolicyStatus() {
        _state.value = _state.value.copy(
            data = emptyList()
        )
        val policyID = _state.value.policyId.text
        val captcha = _state.value.captcha.text

        if(policyID.isEmpty()){
            _message.value = Message(
                key = MessageKey.POLICY_STATUS_INFO,
                string = "Policy ID cannot be empty",
                status = MessageStatus.ERROR
            )
            return
        }
        if(captcha.isEmpty()){
            _message.value = Message(
                key = MessageKey.POLICY_STATUS_INFO,
                string = "Captcha cannot be empty",
                status = MessageStatus.ERROR
            )
        }

        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                repository.checkPolicyStatus(policyID, captcha).onSuccess {
                    if(it.error.isNotEmpty()){
                        _message.value = Message(
                            key = if(it.error.contains("Captcha")) MessageKey.CAPTCHA_INFO else MessageKey.POLICY_STATUS_INFO,
                            string = it.error,
                            status = MessageStatus.ERROR
                        )
                    }else{
                        _state.value = _state.value.copy(data = it.data)
                    }
                }.onError {
                    _message.value = Message(
                        key = MessageKey.POLICY_STATUS_INFO,
                        uiText = it.toUiText(),
                        status = MessageStatus.ERROR
                    )
                }
            }
        }
    }


}