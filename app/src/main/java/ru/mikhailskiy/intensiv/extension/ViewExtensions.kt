package ru.mikhailskiy.intensiv.extension

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = INVISIBLE
}

fun List<View>.hide() {
    forEach {
        it.hide()
    }
}

fun List<View>.show() {
    forEach {
        it.show()
    }
}