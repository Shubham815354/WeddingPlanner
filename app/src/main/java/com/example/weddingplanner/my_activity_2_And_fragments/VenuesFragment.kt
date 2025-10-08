package com.example.weddingplanner.my_activity_2_And_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weddingplanner.databinding.BottomSheetFilterBinding
import com.example.weddingplanner.databinding.FragmentVenuesBinding
import com.example.weddingplanner.my_activity_2data_models.VenueAdapter
import com.example.weddingplanner.my_activity_2data_models.Venue_Details
import com.example.weddingplanner.my_activity_2_And_fragments.VenueViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class VenuesFragment : Fragment() {

    private lateinit var binding: FragmentVenuesBinding
    private lateinit var venueAdapter: VenueAdapter

    // Shared ViewModel (same data as HomeFragment)
    private val venueViewModel: VenueViewModel by activityViewModels()

    // Local copy for filtering
    private var allVenues = listOf<Venue_Details>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVenuesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupListeners()
        observeVenues()
        return binding.root
    }

    /** -----------------------------
     *  Setup RecyclerView + Adapter
     *  ----------------------------- */
    private fun setupRecyclerView() {
        venueAdapter = VenueAdapter()
        binding.venueRecy.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = venueAdapter
        }

        venueAdapter.onSetclick { clicklist ->
            val intent = Intent(requireActivity(), VenueDetails::class.java)
            intent.putExtra("name",clicklist.venue_name)
            intent.putExtra("capacity",clicklist.venue_capacity)
            intent.putExtra("location",clicklist.venue_location)
            intent.putExtra("budget",clicklist.budget)
            intent.putExtra("image",clicklist.venue_Image)
            startActivity(intent)
        }
    }

    /** -----------------------------
     *  Observe shared data from ViewModel
     *  ----------------------------- */
    private fun observeVenues() {
        venueViewModel.venueList.observe(viewLifecycleOwner) { list ->
            if (!list.isNullOrEmpty()) {
                allVenues = list
                venueAdapter.submitList(allVenues)
                binding.tvEmpty.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    /** -----------------------------
     *  Setup UI listeners
     *  ----------------------------- */
    private fun setupListeners() {
        binding.btnFilter.setOnClickListener {
            showFilterBottomSheet()
        }

        binding.btnResetFilters.setOnClickListener {
            venueAdapter.submitList(allVenues)
            binding.tvActiveFilters.visibility = View.GONE
        }
    }

    /** -----------------------------
     *  Filter Bottom Sheet Logic
     *  ----------------------------- */
    private fun showFilterBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetFilterBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)

        // Cancel button
        bottomSheetBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Apply filters
        bottomSheetBinding.btnApply.setOnClickListener {
            val minBudget = bottomSheetBinding.etMinBudget.text.toString()
            val maxBudget = bottomSheetBinding.etMaxBudget.text.toString()
            val minCapacity = bottomSheetBinding.etMinCapacity.text.toString()
            val maxCapacity = bottomSheetBinding.etMaxCapacity.text.toString()

            val filteredList = allVenues.filter { venue ->
                val budget = venue.budget.toIntOrNull() ?: 0
                val capacity = venue.venue_capacity

                (minBudget.isEmpty() || budget >= (minBudget.toIntOrNull() ?: 0)) &&
                        (maxBudget.isEmpty() || budget <= (maxBudget.toIntOrNull() ?: Int.MAX_VALUE)) &&
                        (minCapacity.isEmpty() || capacity >= (minCapacity.toIntOrNull() ?: 0)) &&
                        (maxCapacity.isEmpty() || capacity <= (maxCapacity.toIntOrNull() ?: Int.MAX_VALUE))
            }

            venueAdapter.submitList(filteredList)
            updateActiveFiltersText(minBudget, maxBudget, minCapacity, maxCapacity)
            dialog.dismiss()
        }

        dialog.show()
    }

    /** -----------------------------
     *  Update Filter Summary Text
     *  ----------------------------- */
    private fun updateActiveFiltersText(
        minBudget: String,
        maxBudget: String,
        minCapacity: String,
        maxCapacity: String
    ) {
        val filters = mutableListOf<String>()

        if (minBudget.isNotEmpty() || maxBudget.isNotEmpty()) {
            filters.add("Budget: ₹${minBudget.ifEmpty { "0" }} - ₹${maxBudget.ifEmpty { "∞" }}")
        }
        if (minCapacity.isNotEmpty() || maxCapacity.isNotEmpty()) {
            filters.add("Capacity: ${minCapacity.ifEmpty { "0" }} - ${maxCapacity.ifEmpty { "∞" }}")
        }

        if (filters.isNotEmpty()) {
            binding.tvActiveFilters.visibility = View.VISIBLE
            binding.tvActiveFilters.text = filters.joinToString("\n")
        } else {
            binding.tvActiveFilters.visibility = View.GONE
        }
    }
}