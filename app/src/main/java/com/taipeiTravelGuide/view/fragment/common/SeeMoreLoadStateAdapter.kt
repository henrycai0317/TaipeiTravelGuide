package com.taipeiTravelGuide.view.fragment.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taipeiTravelGuide.ViewUtils.setViewVisibleOrGone
import com.taipeiTravelGuide.databinding.ItemViewLoadStateFooterViewItemBinding

class SeeMoreLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<SeeMoreLoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SeeMoreLoadStateViewHolder {
        val binding =
            ItemViewLoadStateFooterViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SeeMoreLoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: SeeMoreLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}


class SeeMoreLoadStateViewHolder(
    private val binding: ItemViewLoadStateFooterViewItemBinding,
    retry: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.setViewVisibleOrGone(loadState is LoadState.Loading)
            retryButton.setViewVisibleOrGone(loadState is LoadState.Error)
            errorMsg.setViewVisibleOrGone(loadState is LoadState.Error)
        }
    }

}