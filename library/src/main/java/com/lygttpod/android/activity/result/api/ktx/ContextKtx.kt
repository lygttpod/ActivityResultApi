package com.lygttpod.android.activity.result.api.ktx

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(msg: String?) {
    if (msg.isNullOrBlank()) return
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes msgResId: Int) {
    Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(msg: String?) {
    if (msg.isNullOrBlank()) return
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(@StringRes msgResId: Int) {
    Toast.makeText(this, msgResId, Toast.LENGTH_LONG).show()
}