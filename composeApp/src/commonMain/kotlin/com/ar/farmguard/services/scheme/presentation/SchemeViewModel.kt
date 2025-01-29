package com.ar.farmguard.services.scheme.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ar.farmguard.core.domain.PaginationManager
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.services.scheme.domain.model.SchemeItem
import com.ar.farmguard.services.scheme.domain.model.SchemeState
import com.ar.farmguard.services.scheme.domain.repository.SchemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SchemeViewModel(
    private val schemeRepository: SchemeRepository,
    private val paginationManager: PaginationManager
): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var schemeList = paginationManager.getPagingData(
        fetchItems = { page, pageSize ->
            fetchNextPageData(page, pageSize)
        }
    ).cachedIn(viewModelScope)

    private val _schemeState = MutableStateFlow(SchemeState())
    val schemeState = _schemeState.asStateFlow()



    private suspend fun fetchNextPageData(page: Int, pageSize: Int): List<SchemeItem> =
        withContext(Dispatchers.IO){
            var output = emptyList<SchemeItem>()
            schemeRepository.fetchScheme(
                from =(page),
                state = "Haryana",
                size = pageSize,
                lang = "en"
            ).onSuccess {
                output = it.data.hits.items
            }.onError {
               output = emptyList()
            }

            return@withContext output

    }


    fun sortBy() {
        TODO("Not yet implemented")
    }

    fun filter() {
        TODO("Not yet implemented")
    }

}