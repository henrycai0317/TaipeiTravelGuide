package com.taipeiTravelGuide

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewLayoutManagerUtils {
    fun RecyclerView.setLinearLayoutManager(pContext: Context? = null) {
        this.layoutManager = LinearLayoutManager(context)
    }

}