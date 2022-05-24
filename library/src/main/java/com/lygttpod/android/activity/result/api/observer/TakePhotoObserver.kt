package com.lygttpod.android.activity.result.api.observer

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.lygttpod.android.activity.result.api.ktx.createChooserIntent

class TakePhotoObserver(activity: ComponentActivity) :
    BaseObserver<Intent, ActivityResult>(activity) {

    override fun createActivityResultContract(): ActivityResultContract<Intent, ActivityResult> {
        return ActivityResultContracts.StartActivityForResult()
    }

    fun launch2(result: ((Intent) -> Unit)?) {
        launch(createChooserIntent()) {
            result?.invoke(it.data ?: return@launch)
        }
    }
}