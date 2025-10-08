package com.example.weddingplanner.my_Splash_And_View_Pager

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.ActivitySplashBinding
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Optional fade-in animation
        binding.logo.alpha = 0f
        binding.logo.animate().alpha(1f).setDuration(1500).start()

        Handler(Looper.getMainLooper()).postDelayed({
            // After splash, open ViewPager onboarding screen
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500) // 2.5 seconds delay
    }
}