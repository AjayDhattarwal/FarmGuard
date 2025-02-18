package com.ar.farmguard.services.insurance.applications.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ar.farmguard.app.presentation.util.UiText
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.services.insurance.applications.domain.model.PolicyListState
import com.ar.farmguard.services.insurance.applications.domain.repostory.UserApplicationsRepository
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationsViewModel(
    private val userApplicationsRepository: UserApplicationsRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(PolicyListState())
    val state = _state.asStateFlow()

    var selectedYear by mutableStateOf("2024")
        private set
    var selectedSeason by mutableStateOf("Kharif")
        private set

    private var farmerID : String? = null

    fun saveField(key: String, value: String){
        when(key){
            "year" -> selectedYear = value
            "season" -> selectedSeason = value
        }
    }

    fun reset(){
        selectedYear = "2024"
        selectedSeason = "Kharif"
        _state.value = _state.value.copy(
            message = null
        )
    }

    fun checkPolicy(){

        val sssyID = when(selectedSeason){
            "Kharif" -> "0001$selectedYear"
            "Rabi" -> "0002$selectedYear"
            else -> return
        }
        viewModelScope.launch {

            _state.value = _state.value.copy(
                message = null,
                isLoading = true
            )

            val response = authRepository.checkLoginState()

            if(response.first){
                farmerID = response.second?.data?.login?.userData?.farmerID
            }

            if(farmerID != null){
                withContext(Dispatchers.IO){
                    userApplicationsRepository.getApplications(sssyID, farmerID!!)
                        .onSuccess {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                list = it,
                                message = null
                            )
                        }
                        .onError {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                message = Message(
                                    key = MessageKey.POLICY_LIST_STATE,
                                    uiText = it.toUiText(),
                                    status = MessageStatus.INFO
                                )
                            )
                        }
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    message = Message(
                        key = MessageKey.POLICY_LIST_STATE,
                        uiText = UiText.DynamicString("Please Login to fetch details"),
                        status = MessageStatus.ERROR
                    )
                )
            }
        }
    }




}