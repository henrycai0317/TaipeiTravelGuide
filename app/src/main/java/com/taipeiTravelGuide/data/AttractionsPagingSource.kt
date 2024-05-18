package com.taipeiTravelGuide.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.model.Attractions

private const val EVENTS_STARTING_PAGE_INDEX = 1

/**
 *   TravelSpot 建立 PagingSource
 *  處理分頁載邏輯
 * */

class AttractionsPagingSource(
    private val travelService: TravelService,
    private val lang: String,
    private val categoryIds: String? = null,
) : PagingSource<Int, Attractions>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attractions> {
        val page = params.key ?: 1
        return try {
            val response = travelService.callAttractionsPager(lang, categoryIds, page)
            val events = response.data
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (events.isNotEmpty()) page + 1 else null
            Log.d(
                "AttractionsPagingSource",
                "page: $page, params.loadSize: ${params.loadSize} ,NETWORK_PAGE_SIZE: ${TravelRepository.NETWORK_PAGE_SIZE}, nextKey: $nextKey"
            )
            LoadResult.Page(
                data = events,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Log.d("AttractionsPagingSource", "load: Error$exception")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Attractions>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}