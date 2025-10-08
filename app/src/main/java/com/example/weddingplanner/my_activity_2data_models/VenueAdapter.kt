package com.example.weddingplanner.my_activity_2data_models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.RecyVenueBinding
import com.example.weddingplanner.my_activity_2adapters.DiffGetCallback

class VenueAdapter: ListAdapter<Venue_Details, VenueAdapter.MyVenueHolder>(DiffGetCallback()) {
    private var onClick :((Venue_Details) -> Unit)?=null

    fun onSetclick(listner : (Venue_Details) -> Unit){
        onClick = listner
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyVenueHolder {
        val binding : RecyVenueBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.recy_venue,parent,false)
        return MyVenueHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyVenueHolder,
        position: Int
    ) {
        holder.onbind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick?.invoke(getItem(position))
        }
    }

    inner class MyVenueHolder(val binding : RecyVenueBinding): RecyclerView.ViewHolder(binding.root){
        fun onbind(detail: Venue_Details){
            binding.venueName.text= detail.venue_name.toString()
            binding.venueLoc.text = detail.venue_location.toString()
            Glide.with(itemView)
                .load(detail.venue_Image)
                .into(binding.imageDestination)
        }
    }
}

class DiffGetCallback: DiffUtil.ItemCallback<Venue_Details>(){
    override fun areItemsTheSame(
        oldItem: Venue_Details,
        newItem: Venue_Details
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Venue_Details,
        newItem: Venue_Details
    ) = oldItem == newItem

}
