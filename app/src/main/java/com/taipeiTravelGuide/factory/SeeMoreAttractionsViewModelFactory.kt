package com.taipeiTravelGuide.factory

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.viewModel.SeeMoreTravelSpotViewModel

class SeeMoreAttractionsViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: TravelRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(SeeMoreTravelSpotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SeeMoreTravelSpotViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}