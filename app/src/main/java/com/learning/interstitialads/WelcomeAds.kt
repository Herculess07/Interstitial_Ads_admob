package com.learning.interstitialads

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.learning.interstitialads.databinding.ActivityMainBinding

class WelcomeAds : AppCompatActivity() {
    lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_ads)

        btnBack = findViewById(R.id.btnBack)
    }

    override fun onStart() {
        super.onStart()
        btnBack.setOnClickListener{
            finish()
        }
    }
}