package com.ar.farmguard.core.domain

import androidx.paging.PagingState
import app.cash.paging.PagingSource

class PagingDataSource<T : Any>(
    private val fetchItems: suspend (page: Int, pageSize: Int) -> List<T>
): PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 0
            val data = fetchItems(page, params.loadSize)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}