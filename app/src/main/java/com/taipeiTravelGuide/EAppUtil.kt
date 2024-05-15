package com.taipeiTravelGuide

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import java.io.Serializable

object EAppUtil {
    @SuppressLint("ObsoleteSdkInt", "UseCompatLoadingForDrawables")
    fun getDrawable(ctx: Context?, id: Int): Drawable? {
        return if (ctx != null && ctx.resources != null) {
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ctx.resources.getDrawable(id, ctx.theme)
            } else ctx.resources.getDrawable(id)
        } else null
    }

    /**
     * Android 33 以上版本 'getSerializable(String?): Serializable?' is deprecated.
     *
     * Android 0 ~ 32 用 getSerializable(String)
     * Android 33 以後 getSerializableExtra(String, Class<T>)
     *
     */
    inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

    /**
     * 打電話
     * */
    fun callTel(act: Activity, tel: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$tel")
        act.startActivity(intent)
    }
}