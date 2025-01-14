package com.ar.farmguard.services.insurance.calculator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.core.presentation.toUiText
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.CalculatedState
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.PcData
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.toCalculatedState
import com.ar.farmguard.services.insurance.calculator.domain.models.crop.PcCropData
import com.ar.farmguard.services.insurance.calculator.domain.models.district.LevelDistrict
import com.ar.farmguard.services.insurance.calculator.domain.models.sssy.SssyData
import com.ar.farmguard.services.insurance.calculator.domain.models.state.LastCalculated
import com.ar.farmguard.services.insurance.calculator.domain.models.state.MappedSssyData
import com.ar.farmguard.services.insurance.calculator.domain.models.state.PremiumCalculatorState
import com.ar.farmguard.services.insurance.calculator.domain.repository.PremiumCalculatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PremiumCalculatorViewModel(
    private val repository: PremiumCalculatorRepository
): ViewModel() {

    private val l = Logger.withTag("PremiumCalculatorViewModel")

    private var sssyId: String = ""
    private var districtID: String = ""
    private var cropID: String = ""
    private var lastCalculatedData =  LastCalculated()
    private var pcDataCache: List<PcData> = emptyList()


    private val cacheOfDistrict = mutableMapOf<String, List<LevelDistrict>>()

    private var dataState = emptyList<SssyData>()

    private var yearCache = emptyList<SssyData>()

    private var schemeCache = emptyList<SssyData>()

    private var stateCache = emptyList<SssyData>()

    private var districtsCache = emptyList<SssyData>()

    private var districts = emptyList<LevelDistrict>()

    private var crops = emptyList<PcCropData>()

    private val _message = MutableStateFlow<Message?>(null)
    val message = _message.asStateFlow()

    private val  _state = MutableStateFlow(PremiumCalculatorState())
    val state = _state.asStateFlow()

    private val _sssyData = MutableStateFlow(MappedSssyData())
    val sssyData = _sssyData.asStateFlow()
        .onStart { getSssyList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MappedSssyData())

    private val _calculatedPremium = MutableStateFlow<CalculatedState?>(null)
    val calculatedPremium = _calculatedPremium.asStateFlow()


    fun saveField(key: String, value: String){
        when(key){
            "season" -> {
                _state.value = _state.value.copy(season = value)
                _state.value = _state.value.copy(
                    year = "",
                    scheme = "",
                    district = "",
                    crop = ""
                )
                filterYears()
            }
            "year" -> {
                _state.value = _state.value.copy(year = value)
                filterScheme()
            }
            "scheme" -> {
                _state.value = _state.value.copy(scheme = value)
                filterState()
            }
            "state" -> {
                _state.value = _state.value.copy(state = value)
                fetchDistrict()
            }
            "district" -> {
                _state.value = _state.value.copy(district = value)
                fetchCropList()
            }
            "crop" -> _state.value = _state.value.copy(crop = value)
            "area" -> _state.value = _state.value.copy(area = value)
        }
    }

    fun resetValues(){
        _state.value = PremiumCalculatorState()
        _sssyData.value = MappedSssyData()
        _calculatedPremium.value = null
    }

    fun calculate() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                cropID = crops.first {
                    it.cropName == _state.value.crop
                }.cropID

                if (
                    lastCalculatedData.sssyID == sssyId
                    && lastCalculatedData.cropID == cropID
                    && lastCalculatedData.districtID == districtID
                ) {
                    calculateWithData(pcDataCache)
                    return@withContext
                }


                if(districtID.isNotEmpty() && sssyId.isNotEmpty() && cropID.isNotEmpty()){

                    repository.calculatePremium(
                        sssyID = sssyId,
                        districtID = districtID,
                        cropID = cropID
                    ).onSuccess { response->
                        pcDataCache = response.data
                        lastCalculatedData = LastCalculated(sssyId, districtID, cropID)
                        calculateWithData(response.data)

                    }.onError {
                        _message.value = Message(
                            key = MessageKey.SSSY_LIST_INFO,
                            uiText = it.toUiText(),
                            status = MessageStatus.ERROR
                        )
                    }
                }
            }
        }

    }

    private fun calculateWithData(data: List<PcData>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = data.first()
                _calculatedPremium.value = result.toCalculatedState(
                    area = _state.value.area.toFloatOrNull() ?: 1f,
                    crop = _state.value.crop
                )
            }
        }
    }

    private fun getSssyList(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.getSssyList()
                    .onSuccess { sssyResponse ->
                        dataState = sssyResponse.data
                    }
                    .onError { error ->
                        _message.value = Message(
                            key = MessageKey.SSSY_LIST_INFO,
                            uiText = error.toUiText(),
                            status = MessageStatus.ERROR
                        )
                    }
            }
        }
    }

    private fun filterYears() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                yearCache = dataState.filter {
                    it.sssyName?.seasonName == _state.value.season
                }

                val filteredYears =
                    yearCache.map {
                        if(_state.value.season == "Rabi")
                            "${it.sssyName?.year} - ${(it.sssyName?.year?.toInt()?.plus(1))} "
                        else
                            "${it.sssyName?.year}"
                    }.distinct()
                        .sortedDescending()


                _sssyData.value = _sssyData.value.copy(
                    years = filteredYears,
                    schemes = emptyList(),
                    states = emptyList(),
                    districts = emptyList()
                )
                _state.value = _state.value.copy(
                    scheme = "",
                    state = "",
                    district = "",
                    crop = ""
                )
            }
        }
    }

    private fun filterScheme() {
        viewModelScope.launch {

            _state.value = _state.value.copy(
                scheme = "",
                state = "",
                district = "",
                crop = ""
            )


            schemeCache = yearCache.filter {
                it.sssyName?.year == _state.value.year.substring(0,4)
            }

            val filteredSchemes =
                schemeCache.map {
                    it.sssyName?.schemeName.toString()
                }.distinct()


            _sssyData.value = _sssyData.value.copy(
                schemes = filteredSchemes,
                states = emptyList(),
                districts = emptyList(),
                crops = emptyList()
            )
        }
    }

    private fun filterState(){
        viewModelScope.launch {

            _state.value = _state.value.copy(
                state = "",
                district = "",
                crop = ""
            )

            stateCache = schemeCache.filter {
                it.sssyName?.schemeName == _state.value.scheme
            }

            val filteredState =
                stateCache.map {
                    it.sssyName?.stateName.toString()
                }.distinct()


            _sssyData.value = _sssyData.value.copy(
                states = filteredState,
                districts = emptyList(),
                crops = emptyList()
            )

        }
    }

    private fun setDistrictInState(){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                district = "",
                crop = ""
            )
            val districtNameList = districts.map {
                it.level3Name.toString()
            }

            _sssyData.value = _sssyData.value.copy(
                districts = districtNameList,
                crops = emptyList()
            )
        }
    }

    private fun setCropsInState(){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                crop = "",
                area = ""
            )
            val crops = crops.map {
                it.cropName
            }

            _sssyData.value = _sssyData.value.copy(
                crops = crops,
            )
        }

    }

    private fun fetchCropList(){
        viewModelScope.launch {
           withContext(Dispatchers.IO){

               val sssyData = districtsCache.first()

               districtID = districts.first {
                   it.level3Name == _state.value.district
               }.level3ID.toString()


               val sssyId = sssyData.sssyID

               if(districtID.isNotEmpty() && sssyId != null){

                   repository.getCrop(
                       sssyID = sssyId,
                       districtID = districtID
                   ).onSuccess { cropResponse->

                       crops = cropResponse.data

                       setCropsInState()

                   }.onError {
                       _message.value = Message(
                           key = MessageKey.SSSY_LIST_INFO,
                           uiText = it.toUiText(),
                           status = MessageStatus.ERROR
                       )
                   }
               }
           }
        }
    }

    private fun fetchDistrict(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                districtsCache = stateCache.filter {
                    it.sssyName?.stateName == _state.value.state
                }
                val sssyData = districtsCache.first()
                val stateId = sssyData.stateID

                sssyId = sssyData.sssyID.toString()

                if(stateId != null && sssyId.isNotEmpty()){
                    if(cacheOfDistrict.containsKey(sssyId)){
                        districts = cacheOfDistrict[sssyId] ?: emptyList()
                        setDistrictInState()
                        return@withContext
                    }
                    repository.getDistrict(
                        stateId = stateId,
                        sssyId = sssyId
                    ).onSuccess { districtResponse ->

                        districts  = districtResponse.data?.hierarchy?.level3 ?: emptyList()

                        cacheOfDistrict[sssyId] = districts

                        setDistrictInState()

                    }.onError {
                        _message.value = Message(
                            key = MessageKey.SSSY_LIST_INFO,
                            uiText = it.toUiText(),
                            status = MessageStatus.ERROR
                        )
                    }
                }
            }
        }
    }


}
