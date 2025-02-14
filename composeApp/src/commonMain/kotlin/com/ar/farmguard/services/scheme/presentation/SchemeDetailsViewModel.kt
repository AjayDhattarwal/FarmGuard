package com.ar.farmguard.services.scheme.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.farmguard.app.utils.MY_SCHEME_BUILD_ID
import com.ar.farmguard.app.utils.SCHEME_KEY
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.services.scheme.domain.model.SchemeDetailsState
import com.ar.farmguard.services.scheme.domain.repository.SchemeDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchemeDetailsViewModel(
    private val schemeDetailsRepository: SchemeDetailsRepository,
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    private val dataStoreKey = stringPreferencesKey(SCHEME_KEY)

    private val _state = MutableStateFlow(SchemeDetailsState())
    val state = _state.asStateFlow()

    private var buildID by mutableStateOf(MY_SCHEME_BUILD_ID)

    init {
        viewModelScope.launch {
            buildID = getBuildID()
        }
    }


    private suspend fun getBuildID(): String {
        return dataStore.data.first()[dataStoreKey] ?: MY_SCHEME_BUILD_ID
    }


    private fun fetchBuildID(){
        viewModelScope.launch {
            schemeDetailsRepository.fetchBuildID().onSuccess { value ->
                value?.let{
                    buildID = it
                    setNewBuildID(it)
                }
            }
        }
    }

    private suspend fun setNewBuildID(id: String){
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = id
        }
    }


    fun fetchSchemeDetails(id: String, tryCount: Int = 0) {

        if (_state.value.schemeDetails.pageProps?.schemeData?.id == id) return

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true
            )

            withContext(Dispatchers.IO){
                schemeDetailsRepository.getSchemeDetails(id, buildID)
                    .onSuccess { 
                        _state.value = _state.value.copy(
                            schemeDetails = it,
                            isLoading = false
                        )
                    }.onError {
                        if(tryCount < 2){
                            fetchBuildID()
                            fetchSchemeDetails(id, tryCount + 1)
                        }
                    }
            }
        }
    }


}


