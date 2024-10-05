package com.hmwn.headlinenewsmaker.view.template

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.ads.AdsManager
import com.hmwn.headlinenewsmaker.common.startActivityLeftTransition
import com.hmwn.headlinenewsmaker.common.visible
import com.hmwn.headlinenewsmaker.data.model.TemplateDesignModel
import com.hmwn.headlinenewsmaker.data.model.getTemplateDesign
import com.hmwn.headlinenewsmaker.databinding.ActivityTemplateBinding
import com.hmwn.headlinenewsmaker.view.headline.CreateHeadlineActivity
import org.koin.android.ext.android.inject

class TemplateActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTemplateBinding.inflate(layoutInflater)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        TemplateAdapter()
    }

    private val adsManager by inject<AdsManager>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        initBannerAds()
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

        with(binding) {
            toolbar.setNavigationOnClickListener {
                finish()
            }
        }

        adapter.setListener(object : TemplateAdapter.AdapterListener {
            override fun onClickSetting(item: TemplateDesignModel) {
                startActivityLeftTransition<CreateHeadlineActivity>(
                    CreateHeadlineActivity.TEMPLATE_ID_ARG to item.id
                )
            }
        })
    }

    private fun initBannerAds() {
        adsManager.setupBanner()
        adsManager.getBanner()?.getAds().let {
            if (it != null) {
                binding.adView.visible()
                binding.adView.loadAd(it)
                adsManager.bannerListener(binding.adView)
            }
        }
    }

}