package com.hmwn.headlinenewsmaker.ads

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hmwn.headlinenewsmaker.R

class AdsInterstitial(private val context: Context) {

    private var mInterstitialAd: InterstitialAd? = null

    fun initAds() {

        var adRequest = AdRequest.Builder().build()

        // setup
        InterstitialAd.load(
            context,
            context.getString(R.string.ads_interstitial),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    fun getAds(): InterstitialAd? {
        return mInterstitialAd
    }

}