package com.lygttpod.android.activity.result.api.ktx

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File


fun File.transToUri(context: Context): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //设置7.0中共享文件，分享路径定义在xml/provider_paths.xml
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", this)
    } else {
        Uri.fromFile(this)
    }
}