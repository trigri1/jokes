package com.test.jokes.utils

import android.content.Context
import android.view.View
import android.view.View.*
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