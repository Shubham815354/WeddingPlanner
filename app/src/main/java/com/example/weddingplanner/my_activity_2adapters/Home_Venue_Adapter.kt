package com.example.weddingplanner.my_activity_2adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.WeddingHomeRecyBinding
import com.example.weddingplanner.my_activity_2data_models.DiffGetCallback
import com.example.weddingplanner.my_activity_2data_models.Venue_Details

class Home_Venue_Adapter: ListAdapter<Venue_Details, Home_Venue_Adapter.MyHolder>(DiffGetCallback()) {
     private var onClick :((Venue_Details) -> Unit)?=null

    fun onSetclick(listner : (Venue_Details) -> Unit){
        onClick = listner
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val binding : WeddingHomeRecyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.wedding_home_recy,parent,false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        holder.onbind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick?.invoke(getItem(position))
        }
    }

    inner class MyHolder(val binding : WeddingHomeRecyBinding): RecyclerView.ViewHolder(binding.root){
        fun onbind(detail: Venue_Details){
            binding.resort.text= detail.venue_name.toString()
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