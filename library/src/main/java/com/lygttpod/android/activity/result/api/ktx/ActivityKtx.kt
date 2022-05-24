package com.lygttpod.android.activity.result.api.ktx

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun Activity.toast(msg: String?) {
    if (msg.isNullOrBlank()) return
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.toUri(file: File): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
    } else {
        return Uri.fromFile(file)
    }
}
