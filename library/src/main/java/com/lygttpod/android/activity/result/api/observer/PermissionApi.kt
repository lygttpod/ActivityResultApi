package com.lygttpod.android.activity.result.api.observer

import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class PermissionApi(activityResultCaller: ActivityResultCaller) :
    BaseApi<String, Boolean>(activityResultCaller) {
    override fun createActivityResultContract(): ActivityResultContract<String, Boolean> {
        return ActivityResultContracts.RequestPermission()
    }
}