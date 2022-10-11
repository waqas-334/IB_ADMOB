package com.androidbull.incognito.browser

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var tvLog: TextView
    private lateinit var frame: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLog = findViewById(R.id.tv)
        frame = findViewById(R.id.frame)


        RequestConfiguration.Builder().setTestDeviceIds(listOf("C2F7BDFDD12AF195758C529363F68B9E"))
        MobileAds.initialize(
            this
        ) {
            appendLog("Initialised")
            loadHomeBannerAd()
        }
    }


    private fun loadHomeBannerAd() {

        appendLog("loadHomeBannerAd")
        val adView = AdView(this)

        adView.setAdSize(AdSize.BANNER)

        adView.adUnitId = "ca-app-pub-2900479938479651/1132448499"

        val request = AdRequest.Builder().build()
        adView.loadAd(request)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                appendLog("ad loaded")
                frame.removeAllViews()
                frame.addView(adView)
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                appendLog("On Ad Failed to load: ${p0.message}")
                Log.e(TAG, "onAdFailedToLoad: ${p0.message}\n${p0.code}")
                super.onAdFailedToLoad(p0)
                Handler(Looper.getMainLooper()).postDelayed({
                    loadHomeBannerAd()
                }, 3000)
            }
        }


    }

    private fun appendLog(message: String) {
        tvLog.append("$message\n")
    }
}