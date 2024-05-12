package com.taipeiTravelGuide.connect.response

import com.google.gson.annotations.SerializedName
import com.taipeiTravelGuide.model.Attractions

data class AttractionsResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("data") val data: List<Attractions>
)
