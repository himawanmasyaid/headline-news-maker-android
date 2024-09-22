package com.hmwn.headlinenewsmaker.view.headline

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.common.afterTextChanged
import com.hmwn.headlinenewsmaker.databinding.ActivityCreateHeadlineBinding
import com.hmwn.headlinenewsmaker.databinding.ViewTemplateLandscapeCnnBinding
import com.hmwn.headlinenewsmaker.view.preview.PreviewActivity

class CreateHeadlineActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCreateHeadlineBinding.inflate(layoutInflater)
    }

    private lateinit var template : ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()

    }

    private fun initView() {

        with(binding) {

            val template = ViewTemplateLandscapeCnnBinding.inflate(layoutInflater)

            includedLayout.root.removeAllViews()
            includedLayout.root.addView(template.root)

            etHeadline.afterTextChanged {
                template.tvHeadline.text = it
            }

            etDescription.afterTextChanged {
                template.tvDescription.text = it
            }

        }

    }

    private fun initListener() {

        with(binding) {

            btnBack.setOnClickListener {
                finish()
            }

            tvPreview.setOnClickListener {
                navigateToPreview()
            }

        }

    }

    private fun navigateToPreview() {

        val headline : String = binding.etHeadline.text.toString()
        val desc : String = binding.etDescription.text.toString()

        val intent = Intent(this@CreateHeadlineActivity, PreviewActivity::class.java)
        intent.putExtra(PreviewActivity.HEADLINE_ARG, headline ?: "")
        intent.putExtra(PreviewActivity.DESCRIPTION_ARG, desc ?: "")
        startActivity(intent)

    }

}