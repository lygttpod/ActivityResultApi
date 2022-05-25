package com.lygttpod.android.activity.result.api.observer

import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract

abstract class BaseApi<I, O>(activityResultCaller: ActivityResultCaller) {
    private var registry: ActivityResultLauncher<I>

    init {
        activityResultCaller.registerForActivityResult(this.createActivityResultContract()) {
            onResult(it)
        }.also { registry = it }
    }

    protected var result: ((O) -> Unit)? = null

    protected abstract fun createActivityResultContract(): ActivityResultContract<I, O>

    protected open fun onResult(o: O?) {
        o?.let { result?.invoke(it) }
    }

    fun launch(i: I, result: ((O) -> Unit)?) {
        this.result = result
        registry.launch(i)
    }

    fun unregister() {
        registry.unregister()
    }
}