package com.lygttpod.android.activity.result.api.observer

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseObserver<I, O>(private var activity: ComponentActivity) :
    DefaultLifecycleObserver {
    init {
        activity.lifecycle.addObserver(this)
    }

    private lateinit var registry: ActivityResultLauncher<I>

    protected var result: ((O) -> Unit)? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
//        registry = activity.activityResultRegistry.register("permission", owner, ActivityResultContracts.RequestPermission()) {
//            result?.invoke(it)
//        }

        registry = activity.registerForActivityResult(createActivityResultContract()) {
            onResult(it)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        registry.unregister()
    }

    abstract fun createActivityResultContract(): ActivityResultContract<I, O>

    open fun onResult(o: O?) {
        o?.let { result?.invoke(it) }
    }

    fun launch(i: I, result: ((O) -> Unit)?) {
        this.result = result
        registry.launch(i)
    }
}