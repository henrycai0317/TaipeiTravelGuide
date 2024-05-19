package com.taipeiTravelGuide.view.fragment.travelSpotPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.taipeiTravelGuide.di.Injection
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.utils.RecyclerViewLayoutManagerUtils.setLinearLayoutManager
import com.taipeiTravelGuide.utils.StringUtils.checkString
import com.taipeiTravelGuide.utils.ViewUtils.setViewVisibleOrGone
import com.taipeiTravelGuide.databinding.FragmentSeeMoreTravelSpotBinding
import com.taipeiTravelGuide.model.Attractions
import com.taipeiTravelGuide.view.dialog.ProcessDialog
import com.taipeiTravelGuide.view.fragment.common.SeeMoreLoadStateAdapter
import com.taipeiTravelGuide.view.fragment.travelSpotPage.adapter.SeeMoreTravelSpotAdapter
import com.taipeiTravelGuide.viewModel.SeeMoreTravelSpotViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 看更多 遊憩景點
 * */
class SeeMoreTravelSpotFragment : Fragment() {

    companion object {
        private const val EXTRA_SEE_MORE_TRAVEL_SPOT_LANGUAGE_TYPE =
            "EXTRA_SEE_MORE_TRAVEL_SPOT_LANGUAGE_TYPE"

        fun newBundle(pType: String) = Bundle().apply {
            putString(EXTRA_SEE_MORE_TRAVEL_SPOT_LANGUAGE_TYPE, pType)
        }
    }

    private var mProcessDialog: ProcessDialog? = null //Loading Dialog
    private var mLanguageType: String? = null
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
        mLanguageType = arguments?.getString(EXTRA_SEE_MORE_TRAVEL_SPOT_LANGUAGE_TYPE).checkString()
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
        restoreRecyclerViewState()
        initViews()
        initListener()
    }

    override fun onPause() {
        super.onPause()
        val state = mBinding?.rvList?.layoutManager?.onSaveInstanceState()
        if (state != null) {
            mViewModel.saveRecyclerViewState(state)
        }
    }

    private fun restoreRecyclerViewState() {
        val state = mViewModel.getRecyclerViewState()
        if (state != null) {
            mBinding?.rvList?.layoutManager?.onRestoreInstanceState(state)
        }
    }


    private fun initListener() {
        mBinding?.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val iItfTravelSpotItemClick = object : SeeMoreTravelSpotAdapter.ItfTravelSpotItemClick {
                override fun onTravelSpotItemClick(pItemData: Attractions) {
                    TravelSpotFragment.newBundle(pItemData)
                    findNavController().navigate(
                        R.id.action_seeMoreTravelSpotFragment_to_TravelSpotFragment,
                    )
                }
            }
            mAdapter.setOnClickItf(iItfTravelSpotItemClick)

            btRetry.setOnClickListener {
                mAdapter.retry()
            }
        }
    }

    private fun initViews() {
        mBinding?.apply {
            activity?.let { iAct ->
                rvList.setLinearLayoutManager(iAct)
                rvList.adapter = mAdapter.withLoadStateHeaderAndFooter(
                    header = SeeMoreLoadStateAdapter { mAdapter.retry() },
                    footer = SeeMoreLoadStateAdapter { mAdapter.retry() }
                )

                lifecycleScope.launch {
                    mViewModel.getAttractions(mLanguageType ?: "zh-tw")
                        .collectLatest { pagingData ->
                            mAdapter.submitData(pagingData)
                        }
                }
            }

            /** Pager 頁面初始狀態判斷*/
            lifecycleScope.launch {
                mAdapter.loadStateFlow.collect { loadState ->
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && mAdapter.itemCount == 0

                    isTimeToShowProgressDialog(loadState.source.refresh is LoadState.Loading)
                    rvList.setViewVisibleOrGone(!isListEmpty)
                    btRetry.setViewVisibleOrGone(loadState.source.refresh is LoadState.Error)

                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error

                    errorState?.let {
                        activity?.let { iAct ->
                            Toast.makeText(
                                iAct,
                                "\uD83D\uDE28 Wooops ${it.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

        }
    }

    private fun isTimeToShowProgressDialog(pShow: Boolean) {
        val state = mViewModel.getRecyclerViewState()
        if (state == null) {
            mBinding?.icSeeMoreShimmer?.root?.setViewVisibleOrGone(pShow)
            if (pShow) {
                showProgressDialog()
            } else {
                cancelProgressDialog()
            }

        }
    }

    private fun showProgressDialog() {
        val iProcessDialog = mProcessDialog
        val iActivity = activity
        if (iProcessDialog == null && iActivity != null) {
            val iiProcessDialog = ProcessDialog(iActivity)
            iiProcessDialog.setCancelable(false)
            iiProcessDialog.show()
            mProcessDialog = iiProcessDialog
        }
    }

    private fun cancelProgressDialog() {
        mProcessDialog?.cancel()
        mProcessDialog = null
    }

}