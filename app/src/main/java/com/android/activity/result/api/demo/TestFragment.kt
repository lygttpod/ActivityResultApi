package com.android.activity.result.api.demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lygttpod.android.activity.result.api.ActivityResultApi
import com.android.activity.result.api.demo.databinding.FragmentMainBinding
import com.lygttpod.android.activity.result.api.e.ResultApiType
import com.lygttpod.android.activity.result.api.ktx.showToast
import com.lygttpod.android.activity.result.api.ktx.transToUri
import java.io.File

class TestFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private var activityResultApi = ActivityResultApi.createResultApi(
        this,
        listOf(
            ResultApiType.PERMISSION,
            ResultApiType.PERMISSIONS,
            ResultApiType.TAKE_CAMERA,
            ResultApiType.TAKE_CAMERA_BITMAP,
            ResultApiType.TAKE_PHOTO,
            ResultApiType.START_ACTIVITY_FOR_RESULT
        )
    )

    companion object {
        fun buildFragment(data: String?): TestFragment {
            return TestFragment().apply {
                arguments = Bundle().apply {
                    putString("data", data)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvText.text = arguments?.getString("data")
        initListener()

    }

    private fun initListener() {
        binding.permission.setOnClickListener {
            activityResultApi.permission(Manifest.permission.CAMERA) {
                showToast("$it")
            }
        }
        binding.permissions.setOnClickListener {
            activityResultApi.permissions(
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
            activityResultApi.permission(Manifest.permission.CAMERA) {
                if (it) {
                    activityResultApi.takeCameraFile(createTempImageFile()) {
                        binding.tvText.text = it.path
                        binding.image.setImageURI(it.transToUri(requireActivity()))
                    }
                }
            }
        }
        binding.takePicturePreview.setOnClickListener {
            activityResultApi.permissions2(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                if (it) {
                    activityResultApi.takeCameraBitmap {
                        binding.image.setImageBitmap(it)
                    }
                }
            }
        }

        binding.takeChooser.setOnClickListener {
            activityResultApi.permission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                if (it) {
                    activityResultApi.takePhoto {
                        if (it == null) return@takePhoto
                        binding.tvText.text = it.data?.toString() ?: ""
                        binding.image.setImageURI(it.data ?: return@takePhoto)
                    }
                }
            }
        }
        binding.btnNext.setOnClickListener {
            val intent = Intent(requireActivity(), SecondActivity::class.java).apply {
                putExtra("data", "我是fragment传递的参数")
            }
            activityResultApi.startActivityForResult(intent) {
                val resultData = it?.getStringExtra("result") ?: ""
                showToast(resultData)
                binding.tvText.text = resultData
            }
        }
        binding.btnBack.setOnClickListener {
            requireActivity().setResult(
                Activity.RESULT_OK,
                Intent().apply { putExtra("result", "我是fragment页面返回的数据哦") })
            requireActivity().finish()
        }
    }

    private fun createTempImageFile(): File {
        return File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"${System.currentTimeMillis()}_temp.jpg")
    }
}