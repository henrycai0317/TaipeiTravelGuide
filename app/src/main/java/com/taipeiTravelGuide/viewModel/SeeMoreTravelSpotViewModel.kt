package com.taipeiTravelGuide.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.model.Attractions
import kotlinx.coroutines.flow.Flow

class SeeMoreTravelSpotViewModel(private val repository: TravelRepository, handle: SavedStateHandle) : ViewModel() {
    fun getAttractions(
        lang: String,
        category: String? = null
    ): Flow<PagingData<Attractions>> {
        return repository.getAttractionsStream(lang, category).cachedIn(viewModelScope)
    }
}