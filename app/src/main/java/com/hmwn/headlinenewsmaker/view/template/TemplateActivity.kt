package com.hmwn.headlinenewsmaker.view.template

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.databinding.ActivityTemplateBinding

class TemplateActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTemplateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}