package com.taipeiTravelGuide

import android.view.View

object ViewUtils {

    fun View.setViewVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.setViewGone() {
        this.visibility = View.GONE
    }

    fun View.setViewVisibleOrGone(pIsVisible: Boolean) {
        if (pIsVisible) {
            this.setViewVisible()
        } else {
            this.setViewGone()
        }
    }

    fun View.setViewInvisible() {
        this.visibility = View.INVISIBLE
    }
}