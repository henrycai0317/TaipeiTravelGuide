package com.taipeiTravelGuide.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.connect.response.AttractionsResponse
import com.taipeiTravelGuide.connect.response.EventsResponse
import com.taipeiTravelGuide.view.dialog.ProcessDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TaipeiTravelViewModel"

class TaipeiTravelViewModel : ViewModel() {

    //Connect相關
    private val mTravelService: TravelService by lazy {
        TravelService()
    }

    //API 相關
    private val _attractionsApi: MutableLiveData<Response<AttractionsResponse>> = MutableLiveData()
    val attractionsApi: LiveData<Response<AttractionsResponse>> get() = _attractionsApi

    private val _eventsApi: MutableLiveData<Response<EventsResponse>> = MutableLiveData()
    val eventsApi: LiveData<Response<EventsResponse>> get() = _eventsApi

    //UI相關
    private var mProcessDialog: ProcessDialog? = null //Loading Dialog
    var mHasInitHomeFragment = false //防止螢幕轉向重複call API
    var tampSelectedId = -1 //防止選擇同一個語言，重複call API
    private val _isChangeDarkMode = MutableLiveData<Boolean>() //Dark Mode
    val isChangeDarkMode: LiveData<Boolean> get() = _isChangeDarkMode //Dark Mode

    private val _multipleLanguageSelectedId = MutableLiveData<Int>() //多國語言RadioGroup
    val multipleLanguageSelectedId: LiveData<Int>  //多國語言RadioGroup
        get() = _multipleLanguageSelectedId

    fun setMultipleLanguageSelectedId(itemId: Int) {  //多國語言RadioGroup
        if (tampSelectedId != itemId) {
            _multipleLanguageSelectedId.value = itemId
            tampSelectedId = itemId
        }
    }

    //Dark Mode 設定
    fun setIsChangeDarkMode(isChange: Boolean) {
        _isChangeDarkMode.value = isChange
    }

    /**
     *  call Attractions 遊憩景點
     * */
    fun callAttractionsApi(pContext: Context?, pLanguageType: String, pIsChangeLanguage: Boolean) {
        val iCallAttractions = mTravelService.callAttractionsApi(pLanguageType)
        showProgressDialog(pContext, pIsChangeLanguage)
        iCallAttractions.enqueue(object : Callback<AttractionsResponse> {
            override fun onResponse(
                call: Call<AttractionsResponse>,
                response: Response<AttractionsResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d(TAG, "callAttractionsApi onResponse:  $apiResponse")
                } else {
                    Log.d(TAG, "onResponse: not success ${response.code()}")
                }
                _attractionsApi.value = response
                cancelProgressDialog(pIsChangeLanguage)
            }

            override fun onFailure(call: Call<AttractionsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                cancelProgressDialog(pIsChangeLanguage)
            }

        })
    }

    /**
     *  call Events 活動資訊
     * */
    fun callEventsApi(pContext: Context?, pLanguageType: String, pIsChangeLanguage: Boolean) {
        val iCallEvents = mTravelService.callEventsApi(pLanguageType)
        showProgressDialog(pContext, pIsChangeLanguage)
        iCallEvents.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d(TAG, "callEventsApi onResponse:  $apiResponse")
                } else {
                    Log.d(TAG, "onResponse: not success ${response.code()}")
                }
                _eventsApi.value = response
                cancelProgressDialog(pIsChangeLanguage)
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                cancelProgressDialog(pIsChangeLanguage)
            }

        })
    }

    private fun showProgressDialog(pContext: Context?, pIsChangeLanguage: Boolean) {
        if (!pIsChangeLanguage) {
            val iProcessDialog = mProcessDialog
            if (iProcessDialog == null && pContext != null) {
                val iiProcessDialog = ProcessDialog(pContext)
                iiProcessDialog.show()
                mProcessDialog = iiProcessDialog
            }
        }
    }

    private fun cancelProgressDialog(pIsChangeLanguage: Boolean) {
        if (!pIsChangeLanguage) {
            mProcessDialog?.cancel()
            mProcessDialog = null
        }
    }

}