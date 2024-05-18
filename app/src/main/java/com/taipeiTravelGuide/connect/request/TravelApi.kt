package com.taipeiTravelGuide.connect.request

import com.taipeiTravelGuide.connect.response.AttractionsResponse
import com.taipeiTravelGuide.connect.response.EventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApi {

    /** 遊憩景點
     * @param lang 語系代碼
     * zh-tw -正體中文
     * zh-cn -簡體中文
     * en -英文
     * ja -日文
     * ko -韓文
     * es -西班牙文
     * id -印尼文
     * th -泰文
     * vi -越南文
     *
     * @param categoryIds 查詢的分類編號(可輸入多個請以逗號,分隔)。例如 12,34,124
     * @param page 頁碼。(每次回應30筆資料)
     * */
    @Headers("Accept:application/json") // 添加 Accept 標頭
    @GET("/open-api/{lang}/Attractions/All")
    fun getAttractionsREQ(
        @Path("lang") lang: String,
        @Query("categoryIds") categoryIds: String? = null,
        @Query("page") page: Int? = null
    ): Call<AttractionsResponse>

    /** 最新活動
     * @param lang 語系代碼
     * zh-tw -正體中文
     * zh-cn -簡體中文
     * en -英文
     * ja -日文
     * ko -韓文
     * es -西班牙文
     * id -印尼文
     * th -泰文
     * vi -越南文
     *
     * @param begin 開始時間，格式 yyyy-MM-dd
     * @param end 結束時間，格式 yyyy-MM-dd
     * @param page 頁碼。(每次回應30筆資料)
     * */
    @Headers("Accept:application/json") // 添加 Accept 標頭
    @GET("/open-api/{lang}/Events/News")
    fun getEventsREQ(
        @Path("lang") lang: String,
        @Query("begin") begin: String? = null,
        @Query("end") end: String? = null,
        @Query("page") page: Int? = null
    ): Call<EventsResponse>


    @Headers("Accept:application/json") // 添加 Accept 標頭
    @GET("/open-api/{lang}/Events/News")
    suspend fun getEventsPager(
        @Path("lang") lang: String,
        @Query("begin") begin: String? = null,
        @Query("end") end: String? = null,
        @Query("page") page: Int? = null
    ): EventsResponse

    @Headers("Accept:application/json") // 添加 Accept 標頭
    @GET("/open-api/{lang}/Attractions/All")
    suspend fun getAttractionsPager(
        @Path("lang") lang: String,
        @Query("categoryIds") categoryIds: String? = null,
        @Query("page") page: Int? = null
    ): AttractionsResponse
}