package com.lygttpod.android.activity.result.api.ktx

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.lygttpod.android.activity.result.api.config.CropConfig

/**
 * 打开相册
 */
fun createChooserIntent(): Intent {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
        .setType("image/*")
        .addCategory(Intent.CATEGORY_OPENABLE)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    }
    return intent
}

/**
 * 裁剪图片
 */
fun createCropIntent(config: CropConfig?): Intent {
    val uri = config?.sourceUri
    val intent = Intent("com.android.camera.action.CROP")
    intent.setDataAndType(uri, "image/*")
    // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
    intent.putExtra("crop", "true")
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", config?.aspectX ?: 1)
    intent.putExtra("aspectY", config?.aspectY ?: 1)
    // outputX outputY 是裁剪图片宽高
    intent.putExtra("outputX", config?.outputX ?: 500)
    intent.putExtra("outputY", config?.outputY ?: 500)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, config?.outputUri)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    return intent
}