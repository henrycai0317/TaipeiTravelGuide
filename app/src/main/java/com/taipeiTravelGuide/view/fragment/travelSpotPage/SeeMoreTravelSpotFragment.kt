package com.taipeiTravelGuide.view.fragment.travelSpotPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.taipeiTravelGuide.Injection
import com.taipeiTravelGuide.databinding.FragmentSeeMoreTravelSpotBinding
import com.taipeiTravelGuide.view.fragment.common.SeeMoreLoadStateAdapter
import com.taipeiTravelGuide.view.fragment.hotNewsPage.adapter.SeeMoreHotNewsAdapter
import com.taipeiTravelGuide.view.fragment.travelSpotPage.adapter.SeeMoreTravelSpotAdapter
import com.taipeiTravelGuide.viewModel.SeeMoreEventViewModel
import com.taipeiTravelGuide.viewModel.SeeMoreTravelSpotViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 看更多 遊憩景點
 * */
class SeeMoreTravelSpotFragment : Fragment() {
    private var mBinding: FragmentSeeMoreTravelSpotBinding? = null

    private val mViewModel: SeeMoreTravelSpotViewModel by lazy {
        ViewModelProvider(
            this@SeeMoreTravelSpotFragment,
            Injection.provideSeeMoreAttractionsViewModelFactory(owner = this@SeeMoreTravelSpotFragment)
        )[SeeMoreTravelSpotViewModel::class.java]
    }

    private val mAdapter: SeeMoreTravelSpotAdapter by lazy {
        SeeMoreTravelSpotAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSeeMoreTravelSpotBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
    }

    private fun initListener() {
        mBinding?.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initViews() {
        mBinding?.apply {
            activity?.let { iAct ->
                rvList.layoutManager = LinearLayoutManager(iAct)
                rvList.adapter = mAdapter.withLoadStateHeaderAndFooter(
                    header = SeeMoreLoadStateAdapter { mAdapter.retry() },
                    footer = SeeMoreLoadStateAdapter { mAdapter.retry() }
                )
                lifecycleScope.launch {
                    mViewModel.getAttractions("zh-tw").collectLatest { pagingData ->
                        mAdapter.submitData(pagingData)
                    }
                }
            }

//            mAdapter.addLoadStateListener { loadState ->
//                // Show initial loading state
//                SeeMoreLoadStateAdapter { mAdapter.retry() }
//                // Show or hide your initial loading view here
//                // initialLoadingView.isVisible = isLoading
//            }
        }
    }


}