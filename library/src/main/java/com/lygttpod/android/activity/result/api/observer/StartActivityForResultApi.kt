package com.lygttpod.android.activity.result.api.observer

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class StartActivityForResultApi(activityResultCaller: ActivityResultCaller) :
    BaseApi<Intent, ActivityResult>(activityResultCaller) {
    override fun createActivityResultContract(): ActivityResultContract<Intent, ActivityResult> {
        return ActivityResultContracts.StartActivityForResult()
    }

    fun launch2(intent: Intent, result: ((Intent?) -> Unit)?) {
        launch(intent) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                result?.invoke(activityResult.data)
            }
        }
    }
}