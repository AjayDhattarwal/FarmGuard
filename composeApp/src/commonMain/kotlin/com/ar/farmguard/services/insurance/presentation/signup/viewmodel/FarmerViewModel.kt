package com.ar.farmguard.services.insurance.presentation.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FarmerViewModel ():ViewModel() {

    private val _farmerIdList = MutableStateFlow<List<String>>(listOf("UID"))
    val farmerIdList = _farmerIdList.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _passbookName = MutableStateFlow("")
    val passbookName = _passbookName.asStateFlow()

    private val _relativeName = MutableStateFlow("")
    val relativeName = _relativeName.asStateFlow()

    private val _relationship = MutableStateFlow("")
    val relationship = _relationship.asStateFlow()

    private val _mobileNo = MutableStateFlow("")
    val mobileNo = _mobileNo.asStateFlow()

    private val _age = MutableStateFlow("")
    val age = _age.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _castCategory = MutableStateFlow("")
    val castCategory = _castCategory.asStateFlow()

    private val _farmerCategory = MutableStateFlow("")
    val farmerCategory = _farmerCategory.asStateFlow()

    private val _farmerType = MutableStateFlow("")
    val farmerType = _farmerType.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _pinCode = MutableStateFlow("")
    val pinCode = _pinCode.asStateFlow()

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    private val _district = MutableStateFlow("")
    val district = _district.asStateFlow()

    private val _subDistrict = MutableStateFlow("")
    val subDistrict = _subDistrict.asStateFlow()

    private val _village = MutableStateFlow("")
    val village = _village.asStateFlow()

    private val _ifsc = MutableStateFlow("")
    val ifsc = _ifsc.asStateFlow()




    fun saveFarmerDetails(key: String,string: String){
        when(key){
            "fullName" -> _fullName.value = string
            "passbookName" -> _passbookName.value = string
            "relativeName" -> _relativeName.value = string
            "relationship" -> _relationship.value = string
            "mobileNo" -> _mobileNo.value = string
            "age" -> _age.value = string
            "gender" -> _gender.value = string
            "castCategory" -> _castCategory.value = string
            "farmerCategory" -> _farmerCategory.value = string
            "farmerType" -> _farmerType.value = string
            "address" -> _address.value = string
            "pinCode" -> _pinCode.value = string
            "state" -> _state.value = string
            "district" -> _district.value = string
            "subDistrict" -> _subDistrict.value = string
            "village" -> _village.value = string
            "ifsc" -> _ifsc.value = string
        }
    }


    fun saveFarmerID(){

    }

    fun saveAccountDetails(){

    }

}

//getVerifyOtpInFarmer
//{"status":true,"data":{"verified":true,"mobile":"9518686493"},"error":""}

//getVerifyMobileFarmer
//{"status":true,"data":"SMS sent successfully to entered mobile number","error":""}

//data class Selector(
//    val options: List<Pair<String,String>>,
//    val label: String,
//)
//data class BasicField(
//    val label: String,
//    val key : String
//)
//
//val basicFields = listOf(
//    BasicField(
//        label = "Full Name*",
//        key = "full_name"
//    ),
//    BasicField(
//        label = "Passbook Name*",
//        key = "passbook_name"
//    ),
//    BasicField(
//        label = "Relative Name*",
//        key = "relative_name"
//    ),
//)
//
//val selectorFields = listOf(
//    Selector(
//        options = listOf(
//            "S/O" to "son_of",
//            "D/O" to "daughter_of",
//            "W/O" to "wife_of",
//            "C/O" to "care_of",
//        ),
//        label = "Relationship*"
//    ),
//    Selector(
//        options = listOf(
//            " General " to "GEN",
//            " OBC " to "OBC",
//            " SC " to "SC",
//            " ST " to "ST",
//        ),
//        label = "Cast Category"
//    ),
//    Selector(
//        options = listOf(
//            " Male " to "M",
//            " Female " to "F",
//            " Others " to "O",
//        ),
//        label = "Gender*"
//    ),
//    Selector(
//        options = listOf(
//            "Owner" to "O",
//            "Tenant" to "T",
//            "Share Cropper" to "S",
//        ),
//        label = "Farmer category"
//    ),
//    Selector(
//        options = listOf(
//            "  Small  " to "small",
//            " Marginal " to "marginal",
//            " Others " to "other",
//        ),
//        label = "Farmer Type"
//    )
//)