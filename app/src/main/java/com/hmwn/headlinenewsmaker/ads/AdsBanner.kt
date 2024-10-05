package com.hmwn.headlinenewsmaker.ads

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class AdsBanner {

    private var adRequest : AdRequest? = null

    fun initAdsBanner(context: Context) {

        MobileAds.initialize(context) {}
        adRequest = AdRequest.Builder().build()

    }

    fun getAds() : AdRequest? {
        return adRequest
    }

}