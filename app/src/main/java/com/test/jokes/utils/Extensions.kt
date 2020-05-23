package com.test.jokes.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


fun <T> MutableList<T>.clearAndAddAll(list: List<T>) {
    clear()
    addAll(list)
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.show(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}