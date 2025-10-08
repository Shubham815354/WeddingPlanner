package com.example.weddingplanner.my_Splash_And_View_Pager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.ActivityOnboardingBinding
import com.example.weddingplanner.my_login_activity_and_fragments.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf(
            OnboardingItem(
                "Plan Your Dream Wedding",
                "Explore the best venues, caterers, and photographers.",
                R.drawable.wedding_plan_
            ),
            OnboardingItem(
                "Track Your Checklist",
                "Stay organized with a simple wedding checklist.",
                R.drawable.checklist_image
            ),
            OnboardingItem(
                "Manage Your Guests",
                "Easily add guests, track RSVPs, and stay organized.",
                R.drawable.guests_image
            )
        )

        adapter = OnboardingAdapter(items)
        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()


        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < items.size - 1) {
                binding.viewPager.currentItem++
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
