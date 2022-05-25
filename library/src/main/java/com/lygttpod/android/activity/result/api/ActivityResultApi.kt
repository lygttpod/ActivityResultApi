package com.lygttpod.android.activity.result.api

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import com.lygttpod.android.activity.result.api.config.CropConfig
import com.lygttpod.android.activity.result.api.e.ResultApiType
import com.lygttpod.android.activity.result.api.ktx.showToast
import com.lygttpod.android.activity.result.api.observer.*
import java.io.File

class ActivityResultApi {

    private var context: Context? = null

    private var permissionApi: PermissionApi? = null
    private var permissionsApi: PermissionsApi? = null
    private var takeCameraApi: TakeCameraApi? = null
    private var takeCameraBitmapApi: TakeCameraBitmapApi? = null
    private var takePictureCropApi: TakePictureCropApi? = null
    private var takePhotoApi: TakePhotoApi? = null
    private var startActivityForResultApi: StartActivityForResultApi? = null

    companion object {

        fun createResultApi(
            activityResultCaller: ActivityResultCaller,
            resultApiType: List<ResultApiType>
        ): ActivityResultApi {
            val api = ActivityResultApi()
            when (activityResultCaller) {
                is ComponentActivity -> api.context = activityResultCaller
                is Fragment -> api.context = activityResultCaller.context
            }
            create(api, activityResultCaller, resultApiType)
            return api
        }

        private fun create(
            api: ActivityResultApi,
            activityResultCaller: ActivityResultCaller,
            resultApiType: List<ResultApiType>
        ) {
            resultApiType.forEach {
                when (it) {
                    ResultApiType.PERMISSION -> api.permissionApi =
                        PermissionApi(activityResultCaller)
                    ResultApiType.PERMISSIONS -> api.permissionsApi =
                        PermissionsApi(activityResultCaller)
                    ResultApiType.TAKE_CAMERA -> api.takeCameraApi =
                        TakeCameraApi(activityResultCaller)
                    ResultApiType.TAKE_CAMERA_BITMAP -> api.takeCameraBitmapApi =
                        TakeCameraBitmapApi(activityResultCaller)
                    ResultApiType.TAKE_PHOTO -> api.takePhotoApi =
                        TakePhotoApi(activityResultCaller)
                    ResultApiType.TAKE_PICTURE_CROP -> api.takePictureCropApi =
                        TakePictureCropApi(activityResultCaller)
                    ResultApiType.START_ACTIVITY_FOR_RESULT -> api.startActivityForResultApi =
                        StartActivityForResultApi(activityResultCaller)
                }
            }
        }

    }

    private fun checkInitialized(api: BaseApi<*, *>?) {
        if (api == null) {
            Log.e("ActivityResultApi", "请先调用createResultApi添加${getApiName(api)}类型")
            if (BuildConfig.DEBUG) {
                context?.showToast("请先调用createResultApi添加${getApiName(api)}类型")
            }
        }
    }

    private fun getApiName(api: BaseApi<*, *>?): String {
        return when (api) {
            is PermissionApi -> "ResultApiType.PERMISSION"
            is PermissionsApi -> "ResultApiType.PERMISSIONS"
            is TakeCameraApi -> "ResultApiType.TAKE_CAMERA"
            is TakeCameraBitmapApi -> "ResultApiType.TAKE_CAMERA_BITMAP"
            is TakePhotoApi -> "ResultApiType.TAKE_PHOTO"
            is TakePictureCropApi -> "ResultApiType.TAKE_PICTURE_CROP"
            is StartActivityForResultApi -> "ResultApiType.START_ACTIVITY_FOR_RESULT"
            else -> "${api?.toString()}"
        }
    }

    fun permission(permission: String, result: ((Boolean) -> Unit)?) {
        checkInitialized(permissionApi)
        permissionApi?.launch(permission, result)
    }

    fun permissions(permissions: Array<String>, result: ((Map<String, Boolean>) -> Unit)?) {
        checkInitialized(permissionsApi)
        permissionsApi?.launch(permissions, result)
    }

    fun permissions2(permissions: Array<String>, result: ((Boolean) -> Unit)?) {
        checkInitialized(permissionsApi)
        permissionsApi?.launch2(permissions, result)
    }

    fun takeCameraFile(file: File, result: ((File) -> Unit)?) {
        checkInitialized(takeCameraApi)
        takeCameraApi?.launch2(file, result)
    }

    fun takeCameraUri(uri: Uri, result: ((Uri) -> Unit)?) {
        checkInitialized(takeCameraApi)
        takeCameraApi?.launch2(uri, result)
    }

    fun takeCameraBitmap(result: ((Bitmap?) -> Unit)?) {
        checkInitialized(takeCameraBitmapApi)
        takeCameraBitmapApi?.launch2(result)
    }

    fun takePhoto(result: ((Intent?) -> Unit)?) {
        checkInitialized(takeCameraBitmapApi)
        takePhotoApi?.launch2(result)
    }

    fun takePictureCrop(cropConfig: CropConfig, result: ((Uri?) -> Unit)?) {
        checkInitialized(takePictureCropApi)
        takePictureCropApi?.launch2(cropConfig, result)
    }

    fun startActivityForResult(intent: Intent, result: ((Intent?) -> Unit)?) {
        checkInitialized(startActivityForResultApi)
        startActivityForResultApi?.launch2(intent, result)
    }

    fun takeCameraWithPermission(
        file: File,
        permissionResult: ((Boolean) -> Unit)? = null,
        result: ((File) -> Unit)?
    ) {
        permissions2(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            permissionResult?.invoke(it)
            if (it) {
                takeCameraFile(file, result)
            } else {
                if (permissionResult == null) {
                    context?.let { context -> context.showToast(context.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }

    fun takeCameraWithPermission(
        uri: Uri,
        permissionResult: ((Boolean) -> Unit)? = null,
        result: ((Uri) -> Unit)?
    ) {
        permissions2(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            permissionResult?.invoke(it)
            if (it) {
                takeCameraUri(uri, result)
            } else {
                if (permissionResult == null) {
                    context?.let { context -> context.showToast(context.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }

    fun takeCameraBitmapWithPermission(
        permissionResult: ((Boolean) -> Unit)? = null,
        result: ((Bitmap?) -> Unit)?
    ) {
        permission(Manifest.permission.CAMERA) {
            permissionResult?.invoke(it)
            if (it) {
                takeCameraBitmap(result)
            } else {
                if (permissionResult == null) {
                    context?.let { context -> context.showToast(context.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }

    fun takePhotoWithPermission(
        permissionResult: ((Boolean) -> Unit)? = null,
        result: ((Intent?) -> Unit)?
    ) {
        permission(Manifest.permission.READ_EXTERNAL_STORAGE) {
            permissionResult?.invoke(it)
            if (it) {
                takePhoto(result)
            } else {
                if (permissionResult == null) {
                    context?.let { context -> context.showToast(context.getString(R.string.activity_result_api_permission_reject_toast)) }
                }
            }
        }
    }
}