package com.sabirov.camps

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sabirov.campings.databinding.LiCampingBinding
import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.utils.visible

class CampingsAdapter(private val onCampClick: (campId: Number) -> Unit) :
    RecyclerView.Adapter<CampingsAdapter.ViewHolder>() {

    private val list = mutableListOf<Camping>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampingsAdapter.ViewHolder {
        val itemBinding =
            LiCampingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding, onCampClick)
    }

    override fun onBindViewHolder(holder: CampingsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setData(items: List<Camping>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val itemBinding: LiCampingBinding,
        onCampClick: (campId: Number) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(camping: Camping) {
            itemBinding.apply {
                tvTitle.text = camping.name
                tvLocation.text = camping.place
                tvCity.text = camping.nearestTown
                tvPhone.text = camping.phone
                tvSite.text = camping.phone
                if (camping.photos.isEmpty()) {
                    imgCount.visible(false)
                } else {
                    imgCount.text = "+ ${camping.photos.size}"
                    imgCount.visible(true)
                    Glide.with(itemBinding.root.context)
                        .load(camping.photos[0])
                        .placeholder(com.sabirov.resources.R.drawable.bg_container)
                        .into(imageView)
                }
                root.setOnClickListener {
                    camping.id?.let { it1 -> onCampClick.invoke(it1) }
                }
            }
        }
    }
}