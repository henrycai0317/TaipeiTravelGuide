package com.taipeiTravelGuide.viewModel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.model.EventsData
import kotlinx.coroutines.flow.Flow

class SeeMoreEventViewModel(
    private val repository: TravelRepository,
    private val mHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val RECYCLER_VIEW_SEE_MORE_HOT_NEWS_STATE_KEY =
            "recycler_view_see_more_hot_news_state"
    }

    private var currentPagingData: Flow<PagingData<EventsData>>? = null

    fun getEvents(
        lang: String,
        begin: String? = null,
        end: String? = null
    ): Flow<PagingData<EventsData>> {
        return currentPagingData ?: createPagingDataFlow(
            lang,
            begin,
            end
        ).also { iCurrentPagingData ->
            currentPagingData = iCurrentPagingData
        }
    }

    private fun createPagingDataFlow(
        lang: String,
        begin: String?,
        end: String?
    ): Flow<PagingData<EventsData>> {
        return repository.getEventsStream(lang, begin, end).cachedIn(viewModelScope)
    }

    fun saveRecyclerViewState(state: Parcelable) {
        mHandle[RECYCLER_VIEW_SEE_MORE_HOT_NEWS_STATE_KEY] = state
    }

    fun getRecyclerViewState(): Parcelable? {
        return mHandle[RECYCLER_VIEW_SEE_MORE_HOT_NEWS_STATE_KEY]
    }
}