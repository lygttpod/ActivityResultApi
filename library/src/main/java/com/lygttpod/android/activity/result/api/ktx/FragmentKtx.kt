package com.lygttpod.android.activity.result.api.ktx

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg: String?) {
    this.context?.showToast(msg)
}

fun Fragment.showToast(@StringRes msg: Int) {
    this.context?.showToast(msg)
}

fun Fragment.showLongToast(msg: String?) {
    this.context?.showLongToast(msg)
}

fun Fragment.showLongToast(@StringRes msg: Int) {
    this.context?.showLongToast(msg)
}