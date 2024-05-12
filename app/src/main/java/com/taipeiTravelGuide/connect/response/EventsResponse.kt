package com.taipeiTravelGuide.connect.response

import com.google.gson.annotations.SerializedName
import com.taipeiTravelGuide.model.EventsData

data class EventsResponse(
    @SerializedName("data") val data: List<EventsData>,
    @SerializedName("total") val total: Int
)
