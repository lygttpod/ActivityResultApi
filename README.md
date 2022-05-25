# ActivityResultApi

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.lygttpod/activity-result-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.lygttpod/activity-result-api)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ActivityResultApi-green.svg?style=true)](https://github.com/lygttpod)
[![](https://img.shields.io/github/stars/lygttpod/ActivityResultApi.svg)](https://github.com/lygttpod/ActivityResultApi/stargazers)
[![](https://img.shields.io/github/forks/lygttpod/ActivityResultApi.svg)](https://github.com/lygttpod/ActivityResultApi/network)

[**Demo下载体验**](https://www.pgyer.com/9L7i)

一、 添加依赖

```
   implementation 'io.github.lygttpod:activity-result-api:0.0.2'
```

二、用法

1、使用ActivityResultApi统一暴露的方法，便于管理

```
    //在activity或fragment中，声明变量, 根据业务需求去添加不同的Type
    private var activityResultApi = ActivityResultApi.createResultApi(
            this,
            listOf(
                ResultApiType.PERMISSION,
                ResultApiType.PERMISSIONS,
                ResultApiType.TAKE_CAMERA_FILE,
                ResultApiType.TAKE_CAMERA_BITMAP,
                ResultApiType.TAKE_PHOTO,
                ResultApiType.TAKE_PICTURE_CROP,
                ResultApiType.START_ACTIVITY_FOR_RESULT
            )
        )
    
     //对外暴露的方法：
     activityResultApi.permission(permission: String, result: ((Boolean) -> Unit)?)
     activityResultApi.permissions(permissions: Array<String>, result: ((Boolean) -> Unit)?)
     activityResultApi.takeCameraFile(file: File, uri: Uri? = null, result: ((File) -> Unit)?)
     activityResultApi.takeCameraBitmap(result: ((Bitmap) -> Unit)?)
     activityResultApi.takePhoto(result: ((Intent) -> Unit)?)
     activityResultApi.takePictureCrop(cropConfig: CropConfig, result: ((Uri?) -> Unit)?)
     activityResultApi.startActivityForResult(intent: Intent, result: ((Intent) -> Unit)?)
     activityResultApi.takeCameraWithPermission(file: File,permissionResult: ((Boolean) -> Unit)? = null,result: ((File) -> Unit)?)
     activityResultApi.takeCameraWithPermission(uri: Uri,permissionResult: ((Boolean) -> Unit)? = null,result: ((Uri) -> Unit)?)
     activityResultApi.takeCameraBitmapWithPermission(permissionResult: ((Boolean) -> Unit)? = null,result: ((Bitmap?) -> Unit)?)
     activityResultApi.takePhotoWithPermission(permissionResult: ((Boolean) -> Unit)? = null, result: ((Intent?) -> Unit)?)
```

2、使用内置api

```
    //在activity或fragment中定义变量
    private var permissionApi: PermissionApi = PermissionApi(this)
    private var permissionsApi: PermissionsApi = PermissionsApi(this)
    private var takeCameraApi: TakeCameraApi = TakeCameraApi(this)
    private var takeCameraBitmapApi: TakeCameraBitmapApi = TakeCameraBitmapApi(this)
    private var takePictureCropApi: TakePictureCropApi = TakePictureCropApi(this)
    private var takePhotoApi: TakePhotoApi = TakePhotoApi(this)
    private var startActivityForResultApi: StartActivityForResultApi = StartActivityForResultApi(this)
    
   
    //示例
    permissionsApi.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showToast("$it")
            }
    
```

三、混淆(默认已加混淆,如遇问题添加如下混淆)

```
    # activity-result-api
    -keep class com.lygttpod.android.activity.result.api.** { *; }
```