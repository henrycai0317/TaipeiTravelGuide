package com.taipeiTravelGuide


/**
 * Bundle 需要使用的大量資料 將都以此做傳輸處理
 *
 */
object ClassTrans {

    private val mHashMap = HashMap<String, Any>()

    private class ClassTrans<T> {
        var mData: T? = null
    }

    private var mData: Any? = null

    fun <T> setData(pKeyStr: String = "Key", pData: T) {
        val iData: ClassTrans<T> = ClassTrans()
        iData.mData = pData
        mData = iData
        mHashMap[pKeyStr] = iData
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getData(pKeyStr: String = "Key"): T? {
        val iData = mHashMap[pKeyStr] ?: return null
        val iClass = iData as ClassTrans<*>
        val iDetail = iClass.mData as T
        mData = null
        mHashMap.remove(pKeyStr)
        return iDetail
    }

}