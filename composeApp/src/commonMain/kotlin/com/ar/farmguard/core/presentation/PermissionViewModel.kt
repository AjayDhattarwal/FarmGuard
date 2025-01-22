package com.ar.farmguard.core.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionViewModel(
    private val permissionsController: PermissionsController
) : ViewModel(){

    private val _permissionState = MutableStateFlow(PermissionState.NotDetermined)
    val permissionState = _permissionState.asStateFlow()

    init {
        viewModelScope.launch {
            _permissionState.value = permissionsController.getPermissionState(Permission.COARSE_LOCATION)
        }
    }

    fun requestPermission() {
        viewModelScope.launch {
            try {
               permissionsController.providePermission(Permission.COARSE_LOCATION)

            } catch(deniedAlways: DeniedAlwaysException) {
                _permissionState.value = PermissionState.DeniedAlways
            } catch(denied: DeniedException) {
                _permissionState.value = PermissionState.Denied
            }
        }
    }

}