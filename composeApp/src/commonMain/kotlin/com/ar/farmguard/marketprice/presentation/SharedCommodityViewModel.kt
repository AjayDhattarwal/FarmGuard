package com.ar.farmguard.marketprice.presentation

import androidx.lifecycle.ViewModel
import com.ar.farmguard.app.utils.IMG_BASE_URL_CROPS
import com.ar.farmguard.marketprice.domain.model.state.CommodityState
import com.ar.farmguard.marketprice.domain.model.state.TradeReport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class SharedCommodityViewModel() : ViewModel() {

    private val _selectedCommodity = MutableStateFlow<CommodityState?>(null)
    val selectedCommodity = _selectedCommodity.asStateFlow()


//    init{
//        val commodity = CommodityState(
//            commodity = "Onion",
//            image = "$IMG_BASE_URL_CROPS/ONION.jpg",
//            tradeData = getSampleTradeReports(),
//            currentPriceThread = "+ 05.00",
//            apmc = "Sirsa"
//        )
//
//        _selectedCommodity.value = commodity
//    }

    fun getSampleTradeReports(): List<TradeReport> {

        return  listOf(
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 1000.0, modalPrice = 2100.0, maxPrice = 1540.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-16",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            ),
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 1100.0, modalPrice = 2250.0, maxPrice = 1100.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-15",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            ),
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 1400.0, modalPrice = 1800.0, maxPrice = 1900.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-14",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            ),
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 1090.0, modalPrice = 1300.0, maxPrice = 1320.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-13",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            ),
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 2100.0, modalPrice = 1200.0, maxPrice = 2200.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-12",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            ),
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 1800.0, modalPrice = 1900.0, maxPrice = 1600.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-11",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            ),
            TradeReport(
                id = "38", state = "Haryana", apmc = "Sirsa", commodity = "Onion",
                minPrice = 1400.0, modalPrice = 1400.0, maxPrice = 2000.0,
                commodityArrivals = "100", commodityTraded = "80", createdAt = "2025-01-10",
                status = "Active", commodityUom = "Quintal", priceColor = 0xFF1E8805.toInt(),
                priceThread = 0.0f
            )
        )
    }

    fun setCommodity(commodity: CommodityState) {
        _selectedCommodity.value = commodity
    }



}


























