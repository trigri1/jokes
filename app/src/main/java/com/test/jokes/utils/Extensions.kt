package com.test.jokes.utils


fun <T> MutableList<T>.clearAndAddAll(list: List<T>) {
    clear()
    addAll(list)
}