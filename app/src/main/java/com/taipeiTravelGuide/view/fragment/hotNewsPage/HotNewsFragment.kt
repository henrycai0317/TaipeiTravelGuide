package com.taipeiTravelGuide.view.fragment.hotNewsPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.StringUtils.checkString
import com.taipeiTravelGuide.StringUtils.secureUrl
import com.taipeiTravelGuide.databinding.FragmentHotNewsBinding
import com.taipeiTravelGuide.view.dialog.ProcessDialog

/**
 *  最新消息-內頁
 * */
class HotNewsFragment : Fragment() {

    private var mBinding: FragmentHotNewsBinding? = null
    private var mWebViewUrl: String? = null
    private var mProcessDialog: ProcessDialog? = null //Loading Dialog


    companion object {
        private const val EXTRA_HOT_NEWS_WEB_VIEW_URL = "EXTRA_HOT_NEWS_WEB_VIEW_URL"

        fun newBundle(pUrl: String) = Bundle().apply {
            putString(EXTRA_HOT_NEWS_WEB_VIEW_URL, pUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWebViewUrl = arguments?.getString(EXTRA_HOT_NEWS_WEB_VIEW_URL).checkString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHotNewsBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
    }

    private fun initViews() {
        loadingUnlimitedFeeWebView()
    }

    private fun initListener() {
        mBinding?.apply {
            ivBack.setOnClickListener {
                if (wvWebView.canGoBack()) {
                    wvWebView.goBack()
                } else {
                    findNavController().popBackStack()
                }
            }

            /** 使用者按硬件Back鍵處理 */
            requireActivity()
                .onBackPressedDispatcher
                .addCallback(
                    viewLifecycleOwner,
                    object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            ivBack.performClick()
                        }
                    })
        }
    }

    /**
     * 載入最新消息的網頁
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadingUnlimitedFeeWebView() {
        mBinding?.wvWebView?.apply {
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.loadsImagesAutomatically = true
            settings.setSupportZoom(true)
            //useWideViewPort 參數可設定true或是false,設定true時會將viewport的meta tag啟動。
            settings.useWideViewPort = true
            //loadWithOverviewMode 參數值可設定true或是false,默認值是false,此參數值設定為true是為了當內容大於viewport時，系統將會自動縮小內容以適屏幕寬度。
            settings.loadWithOverviewMode = true
            settings.builtInZoomControls = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    cancelProgressDialog()
                }
            }
            isClickable = true
            showProgressDialog()
            webChromeClient = WebChromeClient()
            mWebViewUrl?.let { iWebViewUrl ->
                loadUrl(iWebViewUrl.secureUrl())
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