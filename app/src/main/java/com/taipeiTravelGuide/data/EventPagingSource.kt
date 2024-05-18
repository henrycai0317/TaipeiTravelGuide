package com.taipeiTravelGuide.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.data.TravelRepository.Companion.NETWORK_PAGE_SIZE
import com.taipeiTravelGuide.model.EventsData


/**
 *  建立 PagingSource
 *  處理分頁載邏輯
 * */

private const val EVENTS_STARTING_PAGE_INDEX = 1

class EventPagingSource(
    private val travelService: TravelService,
    private val lang: String,
    private val begin: String? = null,
    private val end: String? = null
) : PagingSource<Int, EventsData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventsData> {
        val position = params.key ?: EVENTS_STARTING_PAGE_INDEX
        return try {
            val response = travelService.callEventsPager(lang, begin, end, position)
            val events = response.data
            val prevKey = if (position == EVENTS_STARTING_PAGE_INDEX) null else position - 1
            val nextKey = if (events.isNotEmpty()) position + 1 else null
            Log.d(
                "EventPagingSource",
                "page: $position, params.loadSize: ${params.loadSize} ,NETWORK_PAGE_SIZE: ${NETWORK_PAGE_SIZE}, nextKey: $nextKey"
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