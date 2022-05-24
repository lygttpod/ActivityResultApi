1. 添加依赖
```
   implementation 'io.github.lygttpod:activity-result-api:0.0.1'
```

2. 混淆(默认已加混淆,如遇问题添加如下混淆)
```
    # activity-result-api
    -keep class com.lygttpod.android.activity.result.api.** { *; }
```

3. 用法
```
//在activity中，声明变量, 根据业务需求去添加不同的Type
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

//在fragment中，声明变量
private lateinit var activityResultApi: ActivityResultApi
//在onViewCreated中初始化
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultApi = ActivityResultApi.createResultApi(
            requireActivity(),
            listOf(
                ResultApiType.PERMISSION,
                ResultApiType.PERMISSIONS,
                ResultApiType.TAKE_CAMERA_FILE,
                ResultApiType.TAKE_CAMERA_BITMAP,
                ResultApiType.TAKE_PHOTO,
                ResultApiType.START_ACTIVITY_FOR_RESULT
            )
        )
    }

//对外暴露的方法：
 activityResultApi.permission(permission: String, result: ((Boolean) -> Unit)?)
 activityResultApi.permissions(permissions: Array<String>, result: ((Boolean) -> Unit)?)
 activityResultApi.takeCameraFile(file: File, uri: Uri? = null, result: ((File) -> Unit)?)
 activityResultApi.takeCameraBitmap(result: ((Bitmap) -> Unit)?)
 activityResultApi.takePhoto(result: ((Intent) -> Unit)?)
 activityResultApi.takePictureCrop(cropConfig: CropConfig, result: ((Uri?) -> Unit)?)
 activityResultApi.startActivityForResult(intent: Intent, result: ((Intent) -> Unit)?)
 activityResultApi.takeCameraFileWithPermission(file: File, uri: Uri? = null, permissionResult: ((Boolean) -> Unit)? = null, result: ((File) -> Unit)?)
 activityResultApi.takeCameraBitmapWithPermission(permissionResult: ((Boolean) -> Unit)? = null, result: ((Bitmap) -> Unit)?)
 activityResultApi.takePhotoWithPermission(permissionResult: ((Boolean) -> Unit)? = null, result: ((Intent) -> Unit)?)
```