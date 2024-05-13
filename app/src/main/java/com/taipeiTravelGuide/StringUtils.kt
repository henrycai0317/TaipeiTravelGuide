package com.taipeiTravelGuide

object StringUtils {

    fun String?.checkString(pShowData:String = ""): String {
        return this?.ifEmpty {
            pShowData
        } ?: pShowData
    }
}