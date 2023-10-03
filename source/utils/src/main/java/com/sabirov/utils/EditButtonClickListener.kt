package com.sabirov.utils

import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditButtonClickListener(
    private val firstImage: Int,
    private val secondImage: Int,
    private val view: EditText?,
    private val doneListener: (() -> Unit)?
) : View.OnClickListener {
    private var state = 0

    override fun onClick(v: View?) {
        (v as? MaterialButton)?.let {
            when (state) {
                0 -> {
                    state = 1
                    it.setIconResource(secondImage)
                    view?.isFocusable = true
                    view?.isFocusableInTouchMode = true
                    view?.requestFocus()
                    view?.setSelection(view.text?.length ?: 0)
                    view?.showKeyBoard()
                }

                1 -> {
                    state = 0
                    it.setIconResource(firstImage)
                    view?.clearFocus()
                    view?.isFocusable = false
                    view?.isFocusableInTouchMode = false
                    view?.hideKeyBoard()
                    doneListener?.invoke()
                }

                else -> {}
            }
        }
    }
}