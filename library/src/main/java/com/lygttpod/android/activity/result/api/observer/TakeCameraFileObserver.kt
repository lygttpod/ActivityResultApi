package com.lygttpod.android.activity.result.api.observer

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.lygttpod.android.activity.result.api.ktx.transToUri
import java.io.File

class TakeCameraFileObserver(val activity: ComponentActivity) : BaseObserver<Uri, Boolean>(activity) {

    override fun createActivityResultContract(): ActivityResultContract<Uri, Boolean> {
        return ActivityResultContracts.TakePicture()
    }

    fun launch2(file: File, uri: Uri? = null, result: ((File) -> Unit)?) {
        val finalUri = uri ?: file.transToUri(activity)
        launch(finalUri) {
            if (it) {
                result?.invoke(file)
            }
        }
    }

}