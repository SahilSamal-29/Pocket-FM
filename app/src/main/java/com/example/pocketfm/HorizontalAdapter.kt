package com.example.pocketfm

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketfm.data.model.AudioItem
import com.example.pocketfm.databinding.ItemStoryBinding

class HorizontalAdapter(
    private val onClick: (AudioItem) -> Unit
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    private val list = mutableListOf<AudioItem>()

    fun submitList(newList: List<AudioItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvTitle.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.binding.imgCover)

        // 🔥 PUT IT HERE
        holder.itemView.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("image", item.image)
            intent.putExtra("audio", item.audioUrl)
            intent.putExtra("title", item.title)
            intent.putExtra("desc", item.description)

            context.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size
}