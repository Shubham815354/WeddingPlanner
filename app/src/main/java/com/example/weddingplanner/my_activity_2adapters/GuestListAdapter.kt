package com.example.weddingplanner.my_activity_2adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.GuestRecyBinding
import com.example.weddingplanner.my_activity_2data_models.Guest
import com.example.weddingplanner.my_guest_list_room_and_mvvm.GuestDatabase

class GuestListAdapter :
    ListAdapter<GuestDatabase, GuestListAdapter.GuestViewHolder>(GuestDiffCallback()) {

    private var onEditClick: ((GuestDatabase) -> Unit)? = null
    private var onDeleteClick: ((GuestDatabase) -> Unit)? = null
    private var onRsvpClick: ((GuestDatabase) -> Unit)? = null

    fun setOnEditClickListener(listener: (GuestDatabase) -> Unit) {
        onEditClick = listener
    }

    fun setOnDeleteClickListener(listener: (GuestDatabase) -> Unit) {
        onDeleteClick = listener
    }

    fun setOnRsvpClickListener(listener: (GuestDatabase) -> Unit) {
        onRsvpClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val binding: GuestRecyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.guest_recy,
            parent,
            false
        )
        return GuestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = getItem(position)
        holder.bind(guest)

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(guest)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick?.invoke(guest)
        }

        holder.binding.rsvpStatus.setOnClickListener {
            onRsvpClick?.invoke(guest)
        }
    }

    inner class GuestViewHolder(val binding: GuestRecyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(guest: GuestDatabase) {
            binding.name.text = guest.name
            binding.age.text = "Age: ${guest.age}"
            binding.gender.text = "Gender: ${guest.gender}"
            binding.rsvpStatus.text = guest.rsvpStatus

            // Update RSVP status background color based on status
            val context = binding.root.context
            binding.rsvpStatus.setBackgroundResource(R.drawable.rsvp_status_bg)

            val color = when (guest.rsvpStatus) {
                "Confirmed" -> ContextCompat.getColor(context, android.R.color.holo_green_dark)
                "Declined" -> ContextCompat.getColor(context, android.R.color.holo_red_dark)
                else -> ContextCompat.getColor(context, android.R.color.holo_orange_dark)
            }
            binding.rsvpStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(color)
        }
    }
}

class GuestDiffCallback : DiffUtil.ItemCallback<GuestDatabase>() {
    override fun areItemsTheSame(oldItem: GuestDatabase, newItem: GuestDatabase): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GuestDatabase, newItem: GuestDatabase): Boolean {
        return oldItem == newItem
    }
}

