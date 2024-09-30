package com.hmwn.headlinenewsmaker.view.template

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.model.TemplateDesignModel
import com.hmwn.headlinenewsmaker.data.model.getTemplateDesign
import com.hmwn.headlinenewsmaker.databinding.ActivityTemplateBinding
import com.hmwn.headlinenewsmaker.view.headline.CreateHeadlineActivity

class TemplateActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTemplateBinding.inflate(layoutInflater)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        TemplateAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        adapter.clear()
        adapter.insertAll(getTemplateDesign())

    }

    fun initView() {

        with(binding) {

            rvTemplate.adapter = adapter
            rvTemplate.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

        }

    }

    fun initListener() {

        adapter.setListener(object : TemplateAdapter.AdapterListener {
            override fun onClickSetting(item: TemplateDesignModel) {
                val intent = Intent(this@TemplateActivity, CreateHeadlineActivity::class.java)
                intent.putExtra(CreateHeadlineActivity.TEMPLATE_ID_ARG, item.id)
                startActivity(intent)
            }
        })
    }

}