package com.lygttpod.android.activity.result.api.observer

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class StartActivityForResultObserver(activity: ComponentActivity) :
    BaseObserver<Intent, ActivityResult>(activity) {
    override fun createActivityResultContract(): ActivityResultContract<Intent, ActivityResult> {
        return ActivityResultContracts.StartActivityForResult()
    }

    fun launch2(intent: Intent, result: ((Intent) -> Unit)?) {
        launch(intent) { activityResult ->
            activityResult.data?.let { result?.invoke(it) }
        }
    }
}