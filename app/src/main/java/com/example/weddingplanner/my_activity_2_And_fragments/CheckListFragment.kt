package com.example.weddingplanner.my_activity_2_And_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.FragmentCheckListBinding
import com.example.weddingplanner.my_activity_2adapters.Wedding_ChecklistAdapter
import com.example.weddingplanner.my_activity_2data_models.Checklist
import com.example.weddingplanner.mychecklist_room_andmvvm.AppDatabase
import com.example.weddingplanner.mychecklist_room_andmvvm.UserDatabase
import com.example.weddingplanner.mychecklist_room_andmvvm.UserRepository
import com.example.weddingplanner.mychecklist_room_andmvvm.UserViewModel
import com.example.weddingplanner.mychecklist_room_andmvvm.UserViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CheckListFragment : Fragment() {

    private lateinit var adapter: Wedding_ChecklistAdapter
    private lateinit var binding: FragmentCheckListBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        adapter = Wedding_ChecklistAdapter()
        binding.checklistRecy.layoutManager = LinearLayoutManager(requireContext())
        binding.checklistRecy.adapter = adapter

        // Setup Repository + ViewModel
        val dao = AppDatabase.Companion.getDatabase_check(requireContext()).user_dao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        viewModel.insertDefaultTasks()

        // ✅ Observe data from ViewModel and use actual checked value
        viewModel.existData.observe(viewLifecycleOwner, Observer { checklist ->
            val converted = checklist.map { Checklist(it.checked, it.taskName, it.isDefault) }
            adapter.submitList(converted)
        })

        // Setup BottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheet.visibility = View.INVISIBLE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        // Initial load
        viewModel.getCheckData()

        // 🔹 Handle FAB click → show add task bottom sheet
        binding.fabAddTask.setOnClickListener {
            binding.bottomSheetTitle.text = "Add Task"
            binding.checkEditName.text?.clear()
            showBottomSheet()
        }

        // 🔹 Handle Save button in bottom sheet
        binding.editButt.setOnClickListener {
            val newTask = binding.checkEditName.text.toString().trim()
            if (newTask.isNotEmpty()) {
                val user = UserDatabase(checked = false, taskName = newTask, isDefault = false)
                viewModel.check_exist(user)
                hideBottomSheet()
            }
        }

        // 🔹 Handle edit and delete from adapter
        adapter.setOnEditClickListener { item, newName ->
            viewModel.check_exist(
                UserDatabase(
                    checked = false,
                    taskName = item.taskName,
                    isDefault = false
                )
            )
            viewModel.check_exist(
                UserDatabase(
                    checked = false,
                    taskName = newName,
                    isDefault = false
                )
            )
            viewModel.getCheckData()
        }

        adapter.setOnDeleteClickListener { item ->
            if (!item.isDefault) {
                val user = UserDatabase(
                    id = 0, // id is not required because we'll delete by taskName
                    taskName = item.taskName,
                    checked = item.checked,
                    isDefault = item.isDefault
                )
                viewModel.deleteCheck(user)
            } else {
                Toast.makeText(requireContext(), "Default task cannot be deleted or edited", Toast.LENGTH_SHORT).show()
            }
        }





        // 🔹 Handle checkbox change
        adapter.setOnCheckChangeListener { item, isChecked ->
            viewModel.updateCheckStatus(item.taskName, isChecked)
        }
    }

    private fun showBottomSheet() {
        binding.bottomSheet.visibility = View.VISIBLE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}