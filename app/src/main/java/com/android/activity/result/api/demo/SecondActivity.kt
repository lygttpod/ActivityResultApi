package com.android.activity.result.api.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.activity.result.api.demo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvText.text = intent.getStringExtra("data")

        binding.btnBack.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply { putExtra("result", "我是SecondActivity页面返回的数据哦") })
            finish()
        }
    }
}