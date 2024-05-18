package com.taipeiTravelGuide.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.model.EventsData
import kotlinx.coroutines.flow.Flow

class SeeMoreEventViewModel(private val repository: TravelRepository, handle: SavedStateHandle) : ViewModel() {
    fun getEvents(
        lang: String,
        begin: String? = null,
        end: String? = null
    ): Flow<PagingData<EventsData>> {
        return repository.getEventsStream(lang, begin, end).cachedIn(viewModelScope)
    }
}