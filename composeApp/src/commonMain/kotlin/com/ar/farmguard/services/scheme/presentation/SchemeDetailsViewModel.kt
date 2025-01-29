package com.ar.farmguard.services.scheme.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.services.scheme.domain.model.SchemeDetailsState
import com.ar.farmguard.services.scheme.domain.repository.SchemeDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchemeDetailsViewModel(
    private val schemeDetailsRepository: SchemeDetailsRepository
): ViewModel() {


    private val _state = MutableStateFlow(SchemeDetailsState())
    val state = _state.asStateFlow()


    init {

    }

    fun fetchSchemeDetails(id: String) {
        
        println("fetching scheme details $id")

        if (_state.value.schemeDetails?.pageProps?.schemeData?.id == id) return

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true
            )

            withContext(Dispatchers.IO){
                schemeDetailsRepository.getSchemeDetails(id)
                    .onSuccess { 
                        _state.value = _state.value.copy(
                            schemeDetails = it,
                            isLoading = false
                        )
                    }
                
                
            }
        }
    }


}


