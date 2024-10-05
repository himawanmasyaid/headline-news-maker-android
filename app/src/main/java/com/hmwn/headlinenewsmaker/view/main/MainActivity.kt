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
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.hmwn.headlinenewsmaker.BuildConfig
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.common.setupRecyclerView
import com.hmwn.headlinenewsmaker.common.setupRecyclerViewVertical
import com.hmwn.headlinenewsmaker.common.startActivityLeftTransition
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.databinding.ActivityMainBinding
import com.hmwn.headlinenewsmaker.ui.theme.PrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.view.headline.CreateHeadlineActivity
import com.hmwn.headlinenewsmaker.view.template.TemplateActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        HeadlineAdapter()
    }

    // GDPR
    private lateinit var consentInformation: ConsentInformation

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)
    private val isTestingConsentGdpr = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        startObserveData()
        initRequestConsentFormGdpr()
        viewModel.getHeadlineNews()

    }

    private fun initView() {

        with(binding) {

            rvContent.adapter = adapter
            rvContent.setupRecyclerViewVertical(this@MainActivity)


        }

    }

    private fun initListener() {

        with(binding) {

             btnCreateHeadline.setOnClickListener {
                 startActivityLeftTransition<TemplateActivity>()
            }

        }

        adapter.setListener(object : HeadlineAdapter.AdapterListener {
            override fun onClickItem(it: HeadlineNewsEntity) {
                startActivityLeftTransition<CreateHeadlineActivity>(
                    CreateHeadlineActivity.TEMPLATE_ID_ARG to it.templateId,
                    CreateHeadlineActivity.HEADLINE_ID_ARG to it.id,
                )            }
        })

    }

    private fun startObserveData() {

        viewModel.headlinesState.observe(this) {
            adapter.clear()
            adapter.insertAll(it)
        }

    }


    fun initRequestConsentFormGdpr() {

        consentInformation = UserMessagingPlatform.getConsentInformation(this)
        if (!BuildConfig.DEBUG && isTestingConsentGdpr) {
            // reset for production
            consentInformation.reset()
        }
        consentInformation.requestConsentInfoUpdate(
            this@MainActivity,
            consentRequestParameters(isTestingConsentGdpr),
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    this@MainActivity,
                    ConsentForm.OnConsentFormDismissedListener { loadAndShowError ->
                        // Consent gathering failed.
                        // Consent has been gathered.
                        if (consentInformation.canRequestAds()) {
                            initializeMobileAdsSdk()
                        }
                    }
                )
            },
            { requestConsentError ->
                // Consent gathering failed.
            })

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk()
        }

    }

    private fun initializeMobileAdsSdk() {

        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this)
    }

    private fun consentRequestParameters(isTesting: Boolean): ConsentRequestParameters {

        // Create a ConsentRequestParameters object.
        return if (BuildConfig.DEBUG && isTesting) {
            // debug
            ConsentRequestParameters
                .Builder()
                .setConsentDebugSettings(consentDebugSettings())
                .build()
        } else {
            // release
            ConsentRequestParameters
                .Builder()
                .build()
        }

    }


    private fun consentDebugSettings(): ConsentDebugSettings {

        // the below code is for testing purpose only, remove it in production
        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId("CB29D20CFD8CEED45405D8BDDFCFFAED")
            .build()
        return debugSettings
    }


}