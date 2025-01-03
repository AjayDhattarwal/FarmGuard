package com.ar.farmguard.domain.model



data class CropData(
    val id: String,
    val name: String,
    val price: Double,
    val image: String,
    val marketName: String,
    val priceColor: Int,   // green Int
    val priceThread: String,  //+₹20 (+13.33%)
)