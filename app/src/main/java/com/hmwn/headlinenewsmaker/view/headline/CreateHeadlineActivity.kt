package com.hmwn.headlinenewsmaker.view.headline

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.databinding.ActivityCreateHeadlineBinding

class CreateHeadlineActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCreateHeadlineBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


    }

    private fun initView() {

    }

    private fun initListener() {

    }

}