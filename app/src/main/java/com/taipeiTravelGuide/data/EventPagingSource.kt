package com.taipeiTravelGuide.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.connect.request.TravelApi
import com.taipeiTravelGuide.data.TravelRepository.Companion.NETWORK_PAGE_SIZE
import com.taipeiTravelGuide.model.EventsData

private const val EVENTS_STARTING_PAGE_INDEX = 1

/**
 *  建立 PagingSource
 *  處理分頁載邏輯
 * */

class EventPagingSource(
    private val travelService: TravelService,
    private val lang: String,
    private val begin: String? = null,
    private val end: String? = null
) : PagingSource<Int, EventsData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventsData> {
        val page = params.key ?: 1
        return try {
            val response = travelService.callEventsPager(lang, begin, end, page)
            val events = response.data
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (events.isNotEmpty()) page + 1 else null
            Log.d(
                "EventPagingSource",
                "page: $page, params.loadSize: ${params.loadSize} ,NETWORK_PAGE_SIZE: ${NETWORK_PAGE_SIZE}, nextKey: $nextKey"
            )
            LoadResult.Page(
                data = events,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EventsData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}