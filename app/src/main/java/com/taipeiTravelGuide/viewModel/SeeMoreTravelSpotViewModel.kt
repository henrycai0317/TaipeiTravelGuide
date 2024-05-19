package com.taipeiTravelGuide.viewModel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.model.Attractions
import kotlinx.coroutines.flow.Flow

class SeeMoreTravelSpotViewModel(
    private val repository: TravelRepository,
    private val mHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val RECYCLER_VIEW_SEE_MORE_TRAVEL_SPOT_STATE_KEY =
            "recycler_view_see_more_travel_spot_state"
    }

    private var currentPagingData: Flow<PagingData<Attractions>>? = null
    fun getAttractions(
        lang: String,
        category: String? = null
    ): Flow<PagingData<Attractions>> {
        return currentPagingData ?: createPagingDataFlow(
            lang,
            category
        ).also { iCurrentPagingData ->
            currentPagingData = iCurrentPagingData
        }
    }

    private fun createPagingDataFlow(
        lang: String,
        category: String?
    ): Flow<PagingData<Attractions>> {
        return repository.getAttractionsStream(lang, category).cachedIn(viewModelScope)
    }


    fun saveRecyclerViewState(state: Parcelable) {
        mHandle[RECYCLER_VIEW_SEE_MORE_TRAVEL_SPOT_STATE_KEY] = state
    }

    fun getRecyclerViewState(): Parcelable? {
        return mHandle[RECYCLER_VIEW_SEE_MORE_TRAVEL_SPOT_STATE_KEY]
    }
}