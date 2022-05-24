package com.lygttpod.android.activity.result.api.ktx

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String?) {
    if (msg.isNullOrBlank()) return
    val act = this.activity ?: return
    Toast.makeText(act, msg, Toast.LENGTH_SHORT).show()
}