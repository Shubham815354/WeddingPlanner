package com.example.weddingplanner.my_activity_2_And_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weddingplanner.R
import com.example.weddingplanner.my_activity_2_And_fragments.VenueDetails
import com.example.weddingplanner.databinding.FragmentHomeBinding
import com.example.weddingplanner.my_activity_2adapters.Catering_recy
import com.example.weddingplanner.my_activity_2adapters.Home_Venue_Adapter
import com.example.weddingplanner.my_activity_2data_models.Catering
import com.example.weddingplanner.my_activity_2data_models.Venue_Details
import com.example.weddingplanner.mysharedpref.SharedPrefHelper
import com.example.weddingplanner.my_activity_2_And_fragments.VenueViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val venueViewModel: VenueViewModel by activityViewModels()
    lateinit var venueadapter : Home_Venue_Adapter
    lateinit var cateringAdapter : Catering_recy
    private var venueList = mutableListOf<Venue_Details>()
    private var cateringList = mutableListOf<Catering>()
    private lateinit var sharedPrefHelper: SharedPrefHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefHelper = SharedPrefHelper(requireActivity())
        val userName = sharedPrefHelper.getUserDetails()
        binding.greet.text = "Hi , ${userName}"
        venueadapter = Home_Venue_Adapter()
        binding.venueRecy.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.venueRecy.adapter = venueadapter

        cateringAdapter = Catering_recy()
        binding.catRecy.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.catRecy.adapter = cateringAdapter
        venueList.addAll(
            listOf(
                Venue_Details(
                    "The Royal Destination",
                    "Rajasthan",
                    "500000",
                    5000,
                    R.drawable.resort_image
                ),
                Venue_Details("Sunset Palace", "Goa", "600000", 3000, R.drawable.goa_hotel),
                Venue_Details("Grand Horizon", "Mumbai", "750000", 4000, R.drawable.mumbai_hotel),
                Venue_Details("Paradise Garden", "Manali", "500000", 2000, R.drawable.manali_hotel),
                Venue_Details(
                    "Ocean View",
                    "Darjeeling",
                    "650000",
                    3500,
                    R.drawable.darjelling_hotel
                ),
                Venue_Details(
                    "Skyline Hall",
                    "Bangalore",
                    "550000",
                    1000,
                    R.drawable.kolkata_hotel
                ),
                Venue_Details("Lotus Palace", "Udaipur", "800000", 4500, R.drawable.lonavla_hotel)
            )
        )
        venueViewModel.setVenueList(venueList)


        cateringList.addAll(
            listOf(
                Catering("Sunshine Photography", "50000", R.drawable.photographer1, "Photographer"),
                Catering("Golden Lens", "60000", R.drawable.photographer2, "Photographer"),
                Catering("Memory Makers", "55000", R.drawable.photographer3, "Photographer"),

                // Catering
                Catering("Royal Caterers", "120000", R.drawable.cat1, "Caterer"),
                Catering("Delightful Bites", "100000", R.drawable.cat2, "Caterer"),
                Catering("Grand Feast", "150000", R.drawable.cat3, "Caterer")
            )
        )

        cateringAdapter.submitList(cateringList)
        venueadapter.submitList(venueList)

        venueadapter.onSetclick { clicklist ->
            val intent = Intent(requireActivity(), VenueDetails::class.java)
            intent.putExtra("name",clicklist.venue_name)
            intent.putExtra("capacity",clicklist.venue_capacity)
            intent.putExtra("location",clicklist.venue_location)
            intent.putExtra("budget",clicklist.budget)
            intent.putExtra("image",clicklist.venue_Image)
            startActivity(intent)

        }
        cateringAdapter.onSetclick { clicklist ->
            val intent = Intent(requireActivity(), CateringAndPhotography::class.java)
            intent.putExtra("name",clicklist.name)
            intent.putExtra("type",clicklist.type)
            intent.putExtra("budget",clicklist.budget)
            intent.putExtra("image",clicklist.Image)
            startActivity(intent)
        }
    }


}