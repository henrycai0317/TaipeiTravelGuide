package com.taipeiTravelGuide.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaipeoTravelViewModel : ViewModel() {
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
}