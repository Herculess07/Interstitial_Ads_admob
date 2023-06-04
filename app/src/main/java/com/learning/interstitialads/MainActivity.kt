package com.learning.interstitialads

import android.content.Intent
import com.google.android.gms.ads.FullScreenContentCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.learning.interstitialads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var interstitialAd: InterstitialAd? = null
    private var TAG = "Ads Listener"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAd()

    }

    override fun onStart() {
        super.onStart()
        binding.btnNextPage.apply {
            setOnClickListener(View.OnClickListener {
                if (interstitialAd != null) {
                    interstitialAd?.show(this@MainActivity)
                    Log.d(TAG, "Interstitial ads shown")
                    interstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                Log.d(TAG, "Ad was clicked")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.e(TAG, "Ad failed to show fullscreen content.")
                            }

                            override fun onAdDismissedFullScreenContent() {
                                Log.d(TAG, "Ad dismissed fullscreen content.")
                                //  mInterstitialAd = null
                                startActivity(Intent(this@MainActivity, WelcomeAds::class.java))
                                initAd()
                            }

                            override fun onAdImpression() {
                                Log.d(TAG, "Ad recorded an impression.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                Log.d(TAG, "Ad showed fullscreen content.")
                            }
                        }
                } else {
                    Log.d(TAG, "The ad is not ready yet")
                    startActivity(Intent(this@MainActivity, WelcomeAds::class.java))
                }
            })
        }
    }

    private fun initAd() {
        MobileAds.initialize(this)
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this@MainActivity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    interstitialAd = null
                }

                override fun onAdLoaded(mInterstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    interstitialAd = mInterstitialAd
                }
            })
    }
}