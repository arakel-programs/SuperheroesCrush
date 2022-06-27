package com.arakel.superherocrush.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.arakel.superherocrush.MainActivity
import com.arakel.superherocrush.R
import com.google.android.gms.ads.*

class PlayActivity : AppCompatActivity() {

    private lateinit var playBtn : Button
    /**AD*/
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val action = supportActionBar
        action?.hide()
        playBtn = findViewById(R.id.playBtn)
        playBtn.setOnClickListener{
            startActivity(Intent
                (this@PlayActivity,
                MainActivity::class.java))
        }

        /**AD*/
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

    }
}