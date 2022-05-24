package com.lygttpod.android.activity.result.api.observer

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class PermissionObserver(activity: ComponentActivity) :
    BaseObserver<String, Boolean>(activity) {
    override fun createActivityResultContract(): ActivityResultContract<String, Boolean> {
        return ActivityResultContracts.RequestPermission()
    }
}