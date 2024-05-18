package com.taipeiTravelGuide.factory

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.viewModel.SeeMoreEventViewModel

class SeeMoreEventsViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: TravelRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(SeeMoreEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SeeMoreEventViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}