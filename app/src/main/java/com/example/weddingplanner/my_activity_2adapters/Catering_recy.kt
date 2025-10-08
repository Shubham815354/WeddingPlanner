package com.example.weddingplanner.my_activity_2adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weddingplanner.R
import com.example.weddingplanner.databinding.HomeCatRecyBinding
import com.example.weddingplanner.databinding.RecyChecklistBinding
import com.example.weddingplanner.databinding.WeddingHomeRecyBinding
import com.example.weddingplanner.my_activity_2data_models.Catering
import com.example.weddingplanner.my_activity_2data_models.Venue_Details

class Catering_recy:ListAdapter<Catering, Catering_recy.CatHolder>(DiffgetCalback()) {

     private var onClick :((Catering) -> Unit)?=null

    fun onSetclick(listner : (Catering) -> Unit){
        onClick = listner
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatHolder {
        val binding : HomeCatRecyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.home_cat_recy,parent,false)
        return CatHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CatHolder,
        position: Int
    ) {
        holder.onbind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick?.invoke(getItem(position))
        }
    }

    inner class CatHolder(val binding: HomeCatRecyBinding): RecyclerView.ViewHolder(binding.root){
        fun onbind(detail: Catering){

            binding.resort.text= detail.name.toString()
            Glide.with(itemView)
                .load(detail.Image)
                .into(binding.imageDestination)
        }
    }
}
class DiffgetCalback: DiffUtil.ItemCallback<Catering>(){
    override fun areItemsTheSame(
        oldItem: Catering,
        newItem: Catering
    )= oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Catering,
        newItem: Catering
    ) = oldItem == newItem

}