package com.example.weddingplanner.my_activity_2_And_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.BottomsheetAddGuestBinding
import com.example.weddingplanner.databinding.FragmentGuestBinding
import com.example.weddingplanner.my_activity_2adapters.GuestListAdapter
import com.example.weddingplanner.my_guest_list_room_and_mvvm.GuestDatabase
import com.example.weddingplanner.my_guest_list_room_and_mvvm.GuestViewModel
import com.example.weddingplanner.my_guest_list_room_and_mvvm.GuestViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GuestFragment : Fragment() {
    private lateinit var binding: FragmentGuestBinding
    private lateinit var viewModel: GuestViewModel
    private lateinit var adapter: GuestListAdapter
    private var editingGuest: GuestDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupViewModel() {
        val factory = GuestViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[GuestViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = GuestListAdapter()
        binding.guestListRecy.layoutManager = LinearLayoutManager(requireContext())
        binding.guestListRecy.adapter = adapter

        adapter.setOnEditClickListener { guest ->
            showAddEditDialog(guest)
        }

        adapter.setOnDeleteClickListener { guest ->
            showDeleteConfirmation(guest)
        }

        // Add RSVP click listener
        adapter.setOnRsvpClickListener { guest ->
            showRsvpDialog(guest)
        }
    }

    private fun setupObservers() {
        viewModel.allGuests.observe(viewLifecycleOwner) { guests ->
            adapter.submitList(guests)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddGuest.setOnClickListener {
            showAddEditDialog(null)
        }
    }

    private fun showAddEditDialog(guest: GuestDatabase?) {
        val dialogBinding: BottomsheetAddGuestBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.bottomsheet_add_guest,
            null,
            false
        )

        editingGuest = guest

        // Setup gender spinner
        val genderOptions = arrayOf("Male", "Female", "Other")
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            genderOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.spinnerGender.adapter = spinnerAdapter

        // Pre-fill data if editing
        guest?.let {
            dialogBinding.editGuestName.setText(it.name)
            dialogBinding.editGuestAge.setText(it.age.toString())
            dialogBinding.spinnerGender.setSelection(
                genderOptions.indexOf(it.gender)
            )
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (guest == null) "Add Guest" else "Edit Guest")
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSaveGuest.setOnClickListener {
            val name = dialogBinding.editGuestName.text.toString().trim()
            val ageStr = dialogBinding.editGuestAge.text.toString().trim()
            val gender = dialogBinding.spinnerGender.selectedItem.toString()

            if (validateInput(name, ageStr)) {
                val age = ageStr.toInt()

                if (guest == null) {
                    // Add new guest
                    val newGuest = GuestDatabase(
                        name = name,
                        age = age,
                        gender = gender,
                        rsvpStatus = "Pending"
                    )
                    viewModel.insertGuest(newGuest)
                    Toast.makeText(requireContext(), "Guest added", Toast.LENGTH_SHORT).show()
                } else {
                    // Update existing guest
                    val updatedGuest = guest.copy(
                        name = name,
                        age = age,
                        gender = gender
                    )
                    viewModel.updateGuest(updatedGuest)
                    Toast.makeText(requireContext(), "Guest updated", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun validateInput(name: String, age: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter guest name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (age.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter age", Toast.LENGTH_SHORT).show()
            return false
        }
        if (age.toIntOrNull() == null || age.toInt() <= 0) {
            Toast.makeText(requireContext(), "Please enter valid age", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showDeleteConfirmation(guest: GuestDatabase) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Guest")
            .setMessage("Are you sure you want to delete ${guest.name}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteGuest(guest)
                Toast.makeText(requireContext(), "Guest deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showRsvpDialog(guest: GuestDatabase) {
        val rsvpOptions = arrayOf("Pending", "Confirmed", "Declined")
        val currentIndex = rsvpOptions.indexOf(guest.rsvpStatus)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update RSVP Status for ${guest.name}")
            .setSingleChoiceItems(rsvpOptions, currentIndex) { dialog, which ->
                val updatedGuest = guest.copy(rsvpStatus = rsvpOptions[which])
                viewModel.updateGuest(updatedGuest)
                Toast.makeText(
                    requireContext(),
                    "RSVP updated to ${rsvpOptions[which]}",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}