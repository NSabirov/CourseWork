package com.example.core
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Int.dpToPx(res: Resources): Int =
    (this.toFloat() * (res.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

fun Float.dpToPx(res: Resources): Int =
    (this.toFloat() * (res.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

fun Int.pxToDp(res: Resources): Int =
    (this.toFloat() / (res.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()