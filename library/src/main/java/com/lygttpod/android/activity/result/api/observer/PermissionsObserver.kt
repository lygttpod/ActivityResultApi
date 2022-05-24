package com.lygttpod.android.activity.result.api.observer

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class PermissionsObserver(activity: ComponentActivity) :
    BaseObserver<Array<String>, Map<String, Boolean>>(activity) {
    override fun createActivityResultContract(): ActivityResultContract<Array<String>, Map<String, Boolean>> {
        return ActivityResultContracts.RequestMultiplePermissions()
    }

    fun launch2(array: Array<String>, result: ((Boolean) -> Unit)?) {
        launch(array) {
            var agant = true
            it.forEach { permission ->
                if (!permission.value) {
                    agant = false
                    return@forEach
                }
            }
            result?.invoke(agant)
        }
    }
}