package com.dmitrykozhukhov.kotlinweather.ui

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBar(
    @StringRes text: Int,
    @StringRes actionText: Int,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBarNotAction(
    text: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).show()
}