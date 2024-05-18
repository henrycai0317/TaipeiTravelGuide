package com.taipeiTravelGuide.view.fragment.hotNewsPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taipeiTravelGuide.databinding.ItemViewHotNewsBinding
import com.taipeiTravelGuide.model.EventsData

class SeeMoreHotNewsAdapter :
    PagingDataAdapter<EventsData, SeeMoreEventViewHolder>(EVENT_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeMoreEventViewHolder {
        val binding =
            ItemViewHotNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeeMoreEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeeMoreEventViewHolder, position: Int) {
        val event = getItem(position)
        if (event != null) {
            holder.bind(event)
        }
    }

    companion object {
        private val EVENT_COMPARATOR = object : DiffUtil.ItemCallback<EventsData>() {
            override fun areItemsTheSame(oldItem: EventsData, newItem: EventsData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: EventsData, newItem: EventsData): Boolean =
                oldItem == newItem
        }
    }
}

class SeeMoreEventViewHolder(private val binding: ItemViewHotNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pEventData: EventsData) {
        binding.apply {
            tvHotNewsTitle.text = pEventData.title
            tvHotNewsContent.text = pEventData.description
        }
    }
}