package com.example.weddingplanner.my_activity_2_And_fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weddingplanner.my_activity_2data_models.Venue_Details

class VenueViewModel : ViewModel() {

    // LiveData to store your venue list
    val venueList = MutableLiveData<List<Venue_Details>>()

    // Optional: function to set or update list
    fun setVenueList(list: List<Venue_Details>) {
        venueList.value = list
    }

    // Optional: function to get list easily
    fun getVenueList(): List<Venue_Details>? {
        return venueList.value
    }
}