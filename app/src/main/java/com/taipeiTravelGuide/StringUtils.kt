package com.taipeiTravelGuide

object StringUtils {

    fun String?.checkString(pShowData: String = ""): String {
        return this?.ifEmpty {
            pShowData
        } ?: pShowData
    }

    fun String.secureUrl(): String {
        // 如果網址的協定是 HTTP，則將其改為 HTTPS
        return if (this.startsWith("http://")) {
            this.replace("http://", "https://")
        } else {
            this // 如果已經是 HTTPS 或其他協定，則不做改變
        }
    }

}