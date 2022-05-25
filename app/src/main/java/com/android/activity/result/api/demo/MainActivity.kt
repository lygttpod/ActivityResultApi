package com.android.activity.result.api.demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.lygttpod.android.activity.result.api.ActivityResultApi
import com.lygttpod.android.activity.result.api.config.CropConfig
import com.android.activity.result.api.demo.databinding.ActivityMainBinding
import com.lygttpod.android.activity.result.api.e.ResultApiType
import com.lygttpod.android.activity.result.api.ktx.showToast
import com.lygttpod.android.activity.result.api.ktx.transToUri
import com.lygttpod.android.activity.result.api.observer.PermissionsApi
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var activityResultApi = ActivityResultApi.createResultApi(
        this,
        listOf(
            ResultApiType.PERMISSION,
            ResultApiType.PERMISSIONS,
            ResultApiType.TAKE_CAMERA,
            ResultApiType.TAKE_CAMERA_BITMAP,
            ResultApiType.TAKE_PHOTO,
            ResultApiType.TAKE_PICTURE_CROP,
            ResultApiType.START_ACTIVITY_FOR_RESULT
        )
    )

    //也可可以单个定义
    private var permissionsApi: PermissionsApi = PermissionsApi(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.permission.setOnClickListener {
            activityResultApi.permission(Manifest.permission.CAMERA) {
                showToast("$it")
            }
        }
        binding.permissions.setOnClickListener {
//            activityResultApi.permissions2(
//                arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//                showToast("$it")
//            }

            permissionsApi.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showToast("$it")
            }
        }
        binding.takePicture.setOnClickListener {
            activityResultApi.takeCameraWithPermission(createTempImageFile()) {
                binding.tvText.text = it.path
                binding.image.setImageURI(it.transToUri(this))
            }
        }
        binding.takePicturePreview.setOnClickListener {
            activityResultApi.takeCameraBitmapWithPermission {
                binding.image.setImageBitmap(it)
            }
        }

        binding.takeChooser.setOnClickListener {
            activityResultApi.takePhotoWithPermission {
                if (it == null) return@takePhotoWithPermission
                val photoUri = it.data
                binding.tvText.text = photoUri?.toString() ?: ""
                photoUri?.let { uri -> binding.image.setImageURI(uri) }
                val outputUri = createCropTempImageFile().transToUri(this)
                activityResultApi.takePictureCrop(CropConfig(photoUri!!, outputUri)) {
                    it?.let { corpUri ->
                        binding.tvText.text = corpUri.toString()
                        binding.image.setImageURI(corpUri)
                    }
                }
            }
        }
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("data", "我是主页面传递的参数")
            }
            activityResultApi.startActivityForResult(intent) {
                val resultData = it?.getStringExtra("result") ?: ""
                showToast(resultData)
                binding.tvText.text = resultData
            }
        }
        binding.btnFragment.setOnClickListener {
            val intent = Intent(this, FragmentActivity::class.java).apply {
                putExtra("data", "我是activity传递的参数")
            }
            activityResultApi.startActivityForResult(intent) {
                val resultData = it?.getStringExtra("result") ?: ""
                showToast(resultData)
                binding.tvText.text = resultData
            }
        }
    }

    private fun createTempImageFile(): File {
        return File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "ara_temp.jpg"
        )
    }


    private fun createCropTempImageFile(): File {
        return File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ara_cropTemp.jpg")
    }
}