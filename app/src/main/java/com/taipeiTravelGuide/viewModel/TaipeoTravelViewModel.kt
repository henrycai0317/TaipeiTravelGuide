package com.taipeiTravelGuide.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.connect.response.AttractionsResponse
import com.taipeiTravelGuide.connect.response.EventsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TaipeiTravelViewModel"

class TaipeiTravelViewModel : ViewModel() {

    //Connect相關
    private val mTravelService: TravelService by lazy {
        TravelService()
    }

    //UI相關
    private val _isChangeDarkMode = MutableLiveData<Boolean>() //Dark Mode
    val isChangeDarkMode: LiveData<Boolean> get() = _isChangeDarkMode //Dark Mode

    private val _multipleLanguageSelectedId = MutableLiveData<Int>() //多國語言RadioGroup
    val multipleLanguageSelectedId: LiveData<Int>  //多國語言RadioGroup
        get() = _multipleLanguageSelectedId

    fun setMultipleLanguageSelectedId(itemId: Int) {  //多國語言RadioGroup
        _multipleLanguageSelectedId.value = itemId
    }

    //Dark Mode 設定
    fun setIsChangeDarkMode(isChange: Boolean) {
        _isChangeDarkMode.value = isChange
    }

    /**
     *  call Attractions 遊憩景點
     * */
    fun callAttractionsApi(pLanguageType: String) {
        val iCallAttractions = mTravelService.callAttractionsApi(pLanguageType)
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
            }

            override fun onFailure(call: Call<AttractionsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

    /**
     *  call Events 活動資訊
     * */
    fun callEventsApi(pLanguageType: String) {
        val iCallEvents = mTravelService.callEventsApi(pLanguageType)
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
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

}