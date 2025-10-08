package com.example.weddingplanner.my_Splash_And_View_Pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingplanner.databinding.ItemOnboardingBinding

data class OnboardingItem(val title: String, val desc: String, val image: Int)

class OnboardingAdapter(private val items: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.imageView.setImageResource(item.image)
        holder.binding.title.text = item.title
        holder.binding.description.text = item.desc
    }

    override fun getItemCount() = items.size
}
