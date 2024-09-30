package com.hmwn.headlinenewsmaker.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.hmwn.headlinenewsmaker.common.gone
import com.hmwn.headlinenewsmaker.common.visible

class AdsManager(private val context: Context) {

    private var adsInterstitial: AdsInterstitial? = null
    private var adsBanner: AdsBanner? = null

    /**
     *   BANNER
     */

    fun setupBanner() {
        adsBanner = AdsBanner()
        adsBanner!!.initAdsBanner(context)
    }

    fun getBanner(): AdsBanner? {
        return adsBanner
    }

    fun bannerListener(adView: AdView) {
        adView.adListener = object : AdListener() {
            override fun onAdOpened() {
                super.onAdOpened()
                adView.visible()
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                adView.gone()
            }

        }
    }

    /**
     * INTERSTITIAL
     */

    fun setupInterstitial() {
        adsInterstitial = AdsInterstitial(context)
        adsInterstitial!!.initAds()
    }

    fun getInterstitial(): AdsInterstitial? {
        return adsInterstitial
    }

    fun showInterstitialAds(activity: Activity) {

        try {
            adsInterstitial?.getAds()?.show(activity)
            // listener
            adsInterstitial?.getAds()!!.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                    }
                }
        } catch (error : Exception) {
        }

    }

}