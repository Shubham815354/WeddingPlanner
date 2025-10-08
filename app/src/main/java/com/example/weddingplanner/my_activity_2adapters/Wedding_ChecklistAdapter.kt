package com.example.weddingplanner.my_activity_2adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingplanner.databinding.RecyChecklistBinding
import com.example.weddingplanner.my_activity_2data_models.Checklist
import com.example.weddingplanner.my_activity_2data_models.Venue_Details
import com.example.weddingplanner.mychecklist_room_andmvvm.UserDatabase

class Wedding_ChecklistAdapter :
    ListAdapter<Checklist, Wedding_ChecklistAdapter.WeddingAdapter>(DiffGetCallbackk()) {

    private var onEditClick: ((Checklist, String) -> Unit)? = null
    private var onDeleteClick: ((Checklist) -> Unit)? = null
    private var onCheckChange: ((Checklist, Boolean) -> Unit)? = null

    fun setOnCheckChangeListener(listener: (Checklist, Boolean) -> Unit) {
        onCheckChange = listener
    }

    fun setOnEditClickListener(listener: (Checklist, String) -> Unit) {
        onEditClick = listener
    }

    fun setOnDeleteClickListener(listener: (Checklist) -> Unit) {
        onDeleteClick = listener
    }

    inner class WeddingAdapter(val binding: RecyChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Checklist) {
            binding.tvTaskName.text = item.taskName
            binding.checkbox.isChecked = item.checked

            // Hide edit/delete layout initially
            binding.goneLinear.visibility = View.GONE

            // Click on task name
            binding.tvTaskName.setOnClickListener {
                if (item.isDefault) {
                    // Default task → show toast
                    android.widget.Toast.makeText(
                        binding.root.context,
                        "Default Task Name  Cannot Be Edited And deleted",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Non-default → toggle edit/delete layout
                    if (binding.goneLinear.visibility == View.GONE) {
                        binding.goneLinear.visibility = View.VISIBLE
                    } else {
                        binding.goneLinear.visibility = View.GONE
                    }
                }
            }

            // Checkbox toggle
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckChange?.invoke(item, isChecked)
            }

            // Edit and delete click
            binding.editButt.setOnClickListener {
                val newName = binding.checkEditName.text.toString().trim()
                if (newName.isNotEmpty()) onEditClick?.invoke(item, newName)
            }

            binding.deleteButt.setOnClickListener {
                onDeleteClick?.invoke(item)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeddingAdapter {
        val binding = RecyChecklistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeddingAdapter(binding)
    }

    override fun onBindViewHolder(holder: WeddingAdapter, position: Int) {
        holder.bind(getItem(position))
    }
}


class DiffGetCallbackk : DiffUtil.ItemCallback<Checklist>() {
    override fun areItemsTheSame(oldItem: Checklist, newItem: Checklist) = oldItem.taskName == newItem.taskName
    override fun areContentsTheSame(oldItem: Checklist, newItem: Checklist) = oldItem == newItem
}
