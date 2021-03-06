package com.android.activity.result.api.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val data = intent.getStringExtra("data")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TestFragment.buildFragment(data)).commit()
    }
}