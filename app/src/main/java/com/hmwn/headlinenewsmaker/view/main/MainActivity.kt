package com.hmwn.headlinenewsmaker.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.databinding.ActivityMainBinding
import com.hmwn.headlinenewsmaker.ui.theme.PrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.view.headline.CreateHeadlineActivity
import com.hmwn.headlinenewsmaker.view.main.home.HomeView
import com.hmwn.headlinenewsmaker.view.template.TemplateActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        HeadlineAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        startObserveData()
        viewModel.getHeadlineNews()

    }

    private fun initView() {

        with(binding) {

            rvContent.adapter = adapter
            rvContent.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }

        }

    }

    private fun initListener() {

        with(binding) {

             btnCreateHeadline.setOnClickListener {
                startActivity(Intent(this@MainActivity, TemplateActivity::class.java))
            }

        }

    }

    private fun startObserveData() {


    }

}