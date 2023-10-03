package com.sabirov.info

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sabirov.campings.databinding.LiImageBinding

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val itemBinding =
            LiImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(items: List<String>){
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val itemBinding: LiImageBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(url: String) {
            itemBinding.apply {
                Glide.with(itemBinding.root.context)
                    .load(Uri.parse(url))
                    .placeholder(com.sabirov.resources.R.drawable.bg_container)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView)
            }
        }
    }
}