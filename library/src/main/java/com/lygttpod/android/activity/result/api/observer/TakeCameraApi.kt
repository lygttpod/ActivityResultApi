package com.lygttpod.android.activity.result.api.observer

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lygttpod.android.activity.result.api.ktx.transToUri
import java.io.File

class TakeCameraApi(private val activityResultCaller: ActivityResultCaller) :
    BaseApi<Uri, Boolean>(activityResultCaller) {

    override fun createActivityResultContract(): ActivityResultContract<Uri, Boolean> {
        return ActivityResultContracts.TakePicture()
    }

    fun launch2(uri: Uri, result: ((Uri) -> Unit)?) {
        launch(uri) {
            if (it) {
                result?.invoke(uri)
            }
        }
    }

    fun launch2(file: File, result: ((File) -> Unit)?) {
        val context = when (activityResultCaller) {
            is ComponentActivity -> activityResultCaller
            is Fragment -> activityResultCaller.context
            else -> null
        }
        if (context != null) {
            launch(file.transToUri(context)) {
                if (it) {
                    result?.invoke(file)
                }
            }
        } else {
            Log.e("ActivityResultApi", "未在fragment或者activity中使用，无法获取context，file无法转uri，请使用uri参数替代")
        }
    }

}