package com.taipeiTravelGuide.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.connect.request.TravelApi
import com.taipeiTravelGuide.model.Attractions
import com.taipeiTravelGuide.model.EventsData
import kotlinx.coroutines.flow.Flow

/**
 * 創建一個 Repository 來提供Pager 數據流
 * */
class TravelRepository(private val travelService: TravelService) {
    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    fun getEventsStream(
        lang: String,
        begin: String? = null,
        end: String? = null
    ): Flow<PagingData<EventsData>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { EventPagingSource(travelService, lang, begin, end) }
        ).flow
    }

    fun getAttractionsStream(
        lang: String,
        category: String? = null
    ): Flow<PagingData<Attractions>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { AttractionsPagingSource(travelService, lang, category) }
        ).flow
    }
}