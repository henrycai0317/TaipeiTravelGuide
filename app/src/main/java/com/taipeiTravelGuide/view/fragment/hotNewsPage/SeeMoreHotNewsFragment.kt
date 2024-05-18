package com.taipeiTravelGuide.view.fragment.hotNewsPage

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
import com.taipeiTravelGuide.StringUtils.checkString
import com.taipeiTravelGuide.databinding.FragmentSeeMoreHotNewsBinding
import com.taipeiTravelGuide.view.fragment.hotNewsPage.adapter.SeeMoreHotNewsAdapter
import com.taipeiTravelGuide.view.fragment.common.SeeMoreLoadStateAdapter
import com.taipeiTravelGuide.viewModel.SeeMoreEventViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  看更多最新消息
 * */
class SeeMoreHotNewsFragment : Fragment() {

    companion object {
        private const val EXTRA_SEE_MORE_HOT_NEWS_LANGUAGE_TYPE =
            "EXTRA_SEE_MORE_HOT_NEWS_LANGUAGE_TYPE"

        fun newBundle(pType: String) = Bundle().apply {
            putString(EXTRA_SEE_MORE_HOT_NEWS_LANGUAGE_TYPE, pType)
        }
    }

    private var mLanguageType: String? = null
    private var mBinding: FragmentSeeMoreHotNewsBinding? = null
    private val mViewModel: SeeMoreEventViewModel by lazy {
        ViewModelProvider(
            this@SeeMoreHotNewsFragment,
            Injection.provideSeeMoreEventsViewModelFactory(owner = this@SeeMoreHotNewsFragment)
        )[SeeMoreEventViewModel::class.java]
    }

    //SeeMoreHotNewsAdapter
    private val mAdapter: SeeMoreHotNewsAdapter by lazy {
        SeeMoreHotNewsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLanguageType = arguments?.getString(EXTRA_SEE_MORE_HOT_NEWS_LANGUAGE_TYPE).checkString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSeeMoreHotNewsBinding.inflate(inflater, container, false)
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
                    mViewModel.getEvents(mLanguageType ?: "zh-tw").collectLatest { pagingData ->
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

