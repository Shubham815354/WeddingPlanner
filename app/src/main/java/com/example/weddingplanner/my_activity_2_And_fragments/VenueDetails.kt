package com.example.weddingplanner.my_activity_2_And_fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.ActivityVenueDetailsBinding

class VenueDetails : AppCompatActivity() {
    lateinit var binding : ActivityVenueDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_venue_details)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Set status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.PrimaryBackground)

        val name =intent.getStringExtra("name")
        val capacity = intent.getIntExtra("capacity",0)
        val location = intent.getStringExtra("location")
        val budget =intent.getStringExtra("budget")
        val image = intent.getIntExtra("image",0)

        binding.venueName.text = "Venue Name - ${name}"
        binding.venueBudget.text="Budget - ${budget}"
        binding.venueCapacity.text = "Capacity - ${capacity}"
        binding.venueLocation.text = "Location - ${location}"

        Glide.with(this)
            .load(image)
            .into(binding.imageDestination)

        binding.bookButton.setOnClickListener {
            Toast.makeText(this, "🎉 Venue booked successfully!", Toast.LENGTH_SHORT).show()
        }


    }
}