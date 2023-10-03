package com.sabirov.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.button.MaterialButton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


fun View.showKeyBoard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, 0)
}

fun View.hideKeyBoard() {
    val imm = this.context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun EditText.isValidEmptyWithAnother(vararg editTexts: EditText): Boolean {
    var isValid = true
    editTexts.forEach {
        isValid = isValid && it.text.toString().isNotEmpty()
    }
    return isValid && this.text.toString().isNotEmpty()
}

@BindingAdapter("showProgress")
fun MaterialButton.setShowProgress(showProgress: Boolean?) {
    icon = if (showProgress == true) {
        CircularProgressDrawable(context!!).apply {
            setStyle(CircularProgressDrawable.DEFAULT)
            val nightModeFlags: Int =
                context.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK
            val color = if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                com.sabirov.resources.R.color.md_theme_dark_onPrimary
            } else com.sabirov.resources.R.color.md_theme_light_onPrimary
            setColorSchemeColors(ContextCompat.getColor(context!!, color))
            start()
        }
    } else null
    if (icon != null) { // callback to redraw button icon
        icon.callback = object : Drawable.Callback {
            override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            }

            override fun invalidateDrawable(who: Drawable) {
                this@setShowProgress.invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            }
        }
    }
}


fun Fragment.registerOnBackPressedCallback(
    onBackPressed: OnBackPressedCallback.() -> Unit
) {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        onBackPressed()
    }
}


fun Int.dpToPx(res: Resources): Int =
    (this.toFloat() * (res.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

fun Float.dpToPx(res: Resources): Int =
    (this.toFloat() * (res.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

fun Int.pxToDp(res: Resources): Int =
    (this.toFloat() / (res.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()


inline fun <reified T> argument(
    key: String,
    defaultValue: T? = null
): ReadWriteProperty<Fragment, T> =
    BundleExtractorDelegate { thisRef ->
        extractFromBundle(
            bundle = thisRef.arguments,
            key = key,
            defaultValue = defaultValue
        )
    }

inline fun <reified T> extractFromBundle(
    bundle: Bundle?,
    key: String,
    defaultValue: T? = null
): T {
    val result = bundle?.get(key) ?: defaultValue
    if (result != null && result !is T) {
        throw ClassCastException("Property $key has different class type")
    }
    return result as T
}

class BundleExtractorDelegate<R, T>(private val initializer: (R) -> T) : ReadWriteProperty<R, T> {

    private object EMPTY

    private var value: Any? = EMPTY

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == EMPTY) {
            value = initializer(thisRef)
        }
        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}