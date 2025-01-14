package com.ar.farmguard.services.insurance.calculator.domain.models.state

data class MappedSssyData(
    val seasons: List<String>  = listOf("Rabi", "Kharif"),
    val years: List<String> = emptyList(),
    val schemes: List<String> = emptyList(),
    val states: List<String> = emptyList(),
    val districts: List<String> = emptyList(),
    val crops : List<String> = emptyList()
)