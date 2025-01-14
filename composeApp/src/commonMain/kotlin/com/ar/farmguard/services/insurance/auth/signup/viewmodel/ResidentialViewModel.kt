package com.ar.farmguard.services.insurance.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResidentialViewModel:ViewModel() {

    private val  _states = MutableStateFlow<List<String>>(emptyList())
    val states = _states.asStateFlow()
        .onStart {
            fetchStates()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _states.value
        )

    private val _districts = MutableStateFlow<List<String>>(emptyList())
    val districts = _districts.asStateFlow()

    private val _subDistricts = MutableStateFlow<List<String>>(emptyList())
    val subDistricts = _subDistricts.asStateFlow()

    private val _villages = MutableStateFlow<List<String>>(emptyList())
    val villages = _villages.asStateFlow()

    init {
        fetchStates()
    }

    private fun fetchStates() {
        viewModelScope.launch {

        }
    }

    fun saveResidentialDetails() {

    }


}