package com.lygttpod.android.activity.result.api.observer

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.lygttpod.android.activity.result.api.config.CropConfig
import com.lygttpod.android.activity.result.api.ktx.createCropIntent

class TakePictureCropObserver(activity: ComponentActivity) :
    BaseObserver<Intent, ActivityResult>(activity) {

    override fun createActivityResultContract(): ActivityResultContract<Intent, ActivityResult> {
        return ActivityResultContracts.StartActivityForResult()
    }

    fun launch2(cropConfig: CropConfig, result: ((Uri?) -> Unit)?) {
        launch(createCropIntent(cropConfig)) {
            result?.invoke(cropConfig.outputUri)
        }
    }
}