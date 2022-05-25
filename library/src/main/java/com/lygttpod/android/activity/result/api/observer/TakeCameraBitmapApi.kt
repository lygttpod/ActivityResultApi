package com.lygttpod.android.activity.result.api.observer

import android.graphics.Bitmap
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class TakeCameraBitmapApi(activityResultCaller: ActivityResultCaller) :
    BaseApi<Void?, Bitmap>(activityResultCaller) {

    override fun createActivityResultContract(): ActivityResultContract<Void?, Bitmap> {
        return ActivityResultContracts.TakePicturePreview()
    }

    fun launch2(result: ((Bitmap?) -> Unit)?) {
        launch(null, result)
    }
}