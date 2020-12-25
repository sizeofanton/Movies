package ru.mikhailskiy.intensiv.extension

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable

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

fun CheckBox.getObservable(): Observable<Boolean> {
    return Observable.create { emitter ->
        setOnCheckedChangeListener { _, b ->
            emitter.onNext(b)
        }
    }
}

fun Fragment.showSnackbar(msg: String) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
}