package com.taipeiTravelGuide.model


data class EventsData(
    val begin: Any,
    val description: String,
    val end: Any,
    val files: List<Any>,
    val id: Int,
    val links: List<EventsLink>,
    val modified: String,
    val posted: String,
    val title: String,
    val url: String
) {
    data class EventsLink(
        val src: String,
        val subject: String
    )

}

