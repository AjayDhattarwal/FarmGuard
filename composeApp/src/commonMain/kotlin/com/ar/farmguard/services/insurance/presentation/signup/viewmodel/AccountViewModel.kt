package com.ar.farmguard.services.insurance.presentation.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccountViewModel: ViewModel() {

    private val  _states = MutableStateFlow<List<String>>(emptyList())
    val states = _states.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _states.value
        )
    private val _districts = MutableStateFlow<List<String>>(emptyList())
    val districts = _districts.asStateFlow()

    private val _banks = MutableStateFlow<List<String>>(emptyList())
    val banks = _banks.asStateFlow()

    private val _branches = MutableStateFlow<List<String>>(emptyList())
    val branches = _branches.asStateFlow()

    fun fetchStates() {
        viewModelScope.launch {
        }
    }

    fun fetchDistricts(state: String) {
        viewModelScope.launch {
        }
    }

    fun fetchBanks(district: String) {
        viewModelScope.launch {
        }
    }

    fun fetchBranches(bank: String) {
        viewModelScope.launch {

        }
    }

    fun verifyIFSC(ifsc: String) {
        viewModelScope.launch {

        }
    }

    fun saveFarmerAccount() {

    }


}


//{"status":true,"data":{"branchID":"B35EFD07-D68F-4EF8-9E2D-459C92C9B269","branchCode":"004331","bankCode":"001","branchName":"BHATTU KALAN","bankType":"COMMERCIAL","bankID":"15DAF2E0-0010-4E61-8960-0E76D9CA0A5C","bankName":"State Bank Of India","districts":[{"districtID":"D14C2906-E134-422F-941C-AC25B0ECF3BD","districtName":"Fatehabad","stateName":"HARYANA","stateID":"5C77DA4F-BC9B-4099-BED7-15E06A45F376","stateCode":"06"}]},"error":""}