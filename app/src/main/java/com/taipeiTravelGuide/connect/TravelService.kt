package com.taipeiTravelGuide.connect

import android.util.Log
import com.taipeiTravelGuide.connect.request.TravelApi
import com.taipeiTravelGuide.connect.response.AttractionsResponse
import com.taipeiTravelGuide.connect.response.EventsResponse
import retrofit2.Call

class TravelService {
    private val travelApi: TravelApi

    init {
        val retrofit = RetrofitClient.getRetrofitInstance()
        travelApi = retrofit.create(TravelApi::class.java)
    }

    /**
     * 請求 Attractions
     * 遊憩景點
     * */
    fun callAttractionsApi(
        lang: String,
        categoryIds: String? = null,
        page: Int? = null
    ): Call<AttractionsResponse> {
        return travelApi.getAttractionsREQ(lang, categoryIds, page)
    }

    /**
     * 請求 Events
     * 活動資訊
     * */
    fun callEventsApi(
        lang: String,
        begin: String? = null,
        end: String? = null,
        page: Int? = null
    ): Call<EventsResponse> {
        return travelApi.getEventsREQ(lang, begin, end, page)
    }

    /**
     * Pager Events 專用
     * 活動資訊
     * */
    suspend fun callEventsPager(
        lang: String,
        begin: String? = null,
        end: String? = null,
        page: Int? = null
    ): EventsResponse {
        Log.d("EventPagingSource", "callEventsPager: page $page")
        return travelApi.getEventsPager(lang, begin, end, page)
    }


    /**
     * Pager Events 專用
     * 活動資訊
     * */
    suspend fun callAttractionsPager(
        lang: String,
        categoryIds: String? = null,
        page: Int? = null
    ): AttractionsResponse {
        Log.d("AttractionsPagingSource", "callAttractionsPager: page $page")
        return travelApi.getAttractionsPager(lang, categoryIds, page)
    }

}