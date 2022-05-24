package com.lygttpod.android.activity.result.api

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import com.lygttpod.android.activity.result.api.config.CropConfig
import com.lygttpod.android.activity.result.api.e.ResultApiType
import com.lygttpod.android.activity.result.api.ktx.toast
import com.lygttpod.android.activity.result.api.observer.*
import java.io.File

class ActivityResultApi {

    private var act: ComponentActivity? = null

    private var permissionObs: PermissionObserver? = null
    private var permissionsObs: PermissionsObserver? = null
    private var takeCameraFileObs: TakeCameraFileObserver? = null
    private var takeCameraBitmapObs: TakeCameraBitmapObserver? = null
    private var takeTakePictureCropObs: TakePictureCropObserver? = null
    private var takePhotoObs: TakePhotoObserver? = null
    private var startActivityForResultObs: StartActivityForResultObserver? = null

    companion object {
        fun createResultApi(activity: ComponentActivity, resultApiType: List<ResultApiType>): ActivityResultApi {
            val api = ActivityResultApi()
            api.act = activity
            resultApiType.forEach {
                when (it) {
                    ResultApiType.PERMISSION -> api.permissionObs = PermissionObserver(activity)
                    ResultApiType.PERMISSIONS -> api.permissionsObs = PermissionsObserver(activity)
                    ResultApiType.TAKE_CAMERA_FILE -> api.takeCameraFileObs = TakeCameraFileObserver(activity)
                    ResultApiType.TAKE_CAMERA_BITMAP -> api.takeCameraBitmapObs = TakeCameraBitmapObserver(activity)
                    ResultApiType.TAKE_PHOTO -> api.takePhotoObs = TakePhotoObserver(activity)
                    ResultApiType.TAKE_PICTURE_CROP -> api.takeTakePictureCropObs = TakePictureCropObserver(activity)
                    ResultApiType.START_ACTIVITY_FOR_RESULT -> api.startActivityForResultObs = StartActivityForResultObserver(activity)
                }
            }
            return api
        }
    }

    private fun checkInitialized(observer: BaseObserver<*, *>?) {
        if (observer == null) {
            Log.e("ActivityResultApi", "请先调用createResultApi添加${getApiName(observer)}类型")
            if (BuildConfig.DEBUG) {
                act?.toast("请先调用createResultApi添加${getApiName(observer)}类型")
            }
        }
    }

    private fun getApiName(observer: BaseObserver<*, *>?): String {
        return when (observer) {
            is PermissionObserver -> "ResultApiType.PERMISSION"
            is PermissionsObserver -> "ResultApiType.PERMISSIONS"
            is TakeCameraFileObserver -> "ResultApiType.TAKE_CAMERA_FILE"
            is TakeCameraBitmapObserver -> "ResultApiType.TAKE_CAMERA_BITMAP"
            is TakePhotoObserver -> "ResultApiType.TAKE_PHOTO"
            is StartActivityForResultObserver -> "ResultApiType.START_ACTIVITY_FOR_RESULT"
            else -> "所需功能"
        }
    }

    fun permission(permission: String, result: ((Boolean) -> Unit)?) {
        checkInitialized(permissionObs)
        permissionObs?.launch(permission, result)
    }

    fun permissions(permissions: Array<String>, result: ((Boolean) -> Unit)?) {
        checkInitialized(permissionsObs)
        permissionsObs?.launch2(permissions, result)
    }

    fun takeCameraFile(file: File, uri: Uri? = null, result: ((File) -> Unit)?) {
        checkInitialized(takeCameraFileObs)
        takeCameraFileObs?.launch2(file, uri, result)
    }

    fun takeCameraBitmap(result: ((Bitmap) -> Unit)?) {
        checkInitialized(takeCameraBitmapObs)
        takeCameraBitmapObs?.launch2(result)
    }

    fun takePhoto(result: ((Intent) -> Unit)?) {
        checkInitialized(takeCameraBitmapObs)
        takePhotoObs?.launch2(result)
    }

    fun takePictureCrop(cropConfig: CropConfig, result: ((Uri?) -> Unit)?) {
        checkInitialized(takeTakePictureCropObs)
        takeTakePictureCropObs?.launch2(cropConfig, result)
    }

    fun startActivityForResult(intent: Intent, result: ((Intent) -> Unit)?) {
        checkInitialized(startActivityForResultObs)
        startActivityForResultObs?.launch2(intent, result)
    }

    fun takeCameraFileWithPermission(file: File, uri: Uri? = null, permissionResult: ((Boolean) -> Unit)? = null, result: ((File) -> Unit)?) {
        permissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            permissionResult?.invoke(it)
            if (it) {
                takeCameraFile(file, uri, result)
            } else {
                if (permissionResult == null) {
                    act?.let { activity -> activity.toast(activity.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }

    fun takeCameraBitmapWithPermission(permissionResult: ((Boolean) -> Unit)? = null, result: ((Bitmap) -> Unit)?) {
        permission(Manifest.permission.CAMERA) {
            permissionResult?.invoke(it)
            if (it) {
                takeCameraBitmap(result)
            } else {
                if (permissionResult == null) {
                    act?.let { activity -> activity.toast(activity.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }

    fun takePhotoWithPermission(permissionResult: ((Boolean) -> Unit)? = null, result: ((Intent) -> Unit)?) {
        permission(Manifest.permission.READ_EXTERNAL_STORAGE) {
            permissionResult?.invoke(it)
            if (it) {
                takePhoto(result)
            } else {
                if (permissionResult == null) {
                    act?.let { activity -> activity.toast(activity.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }
}