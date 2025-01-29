package com.ar.farmguard.core.domain

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow

class PaginationManager {

    private val _errorState = MutableSharedFlow<String>()
    val errorState: SharedFlow<String> = _errorState.asSharedFlow()


    fun<T : Any> getPagingData(
        fetchItems: suspend (page: Int, pageSize: Int) -> List<T>,
    ): Flow<PagingData<T>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingDataSource(fetchItems) }
        ).flow
    }
}
