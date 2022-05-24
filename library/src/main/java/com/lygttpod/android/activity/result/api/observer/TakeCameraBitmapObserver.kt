package com.lygttpod.android.activity.result.api.observer

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class TakeCameraBitmapObserver(activity: ComponentActivity) :
    BaseObserver<Void?, Bitmap>(activity) {

    override fun createActivityResultContract(): ActivityResultContract<Void?, Bitmap> {
        return ActivityResultContracts.TakePicturePreview()
    }

    fun launch2(result: ((Bitmap) -> Unit)?) {
        launch(null, result)
    }
}