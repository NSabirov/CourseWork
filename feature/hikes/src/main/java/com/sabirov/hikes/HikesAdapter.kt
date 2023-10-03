package com.sabirov.hikes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sabirov.core_api.entities.hike.Hike
import com.sabirov.hikes.databinding.LiHikeBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.StringJoiner


class HikesAdapter(private val onHikeClick: (hikeId: Number) -> Unit) :
    RecyclerView.Adapter<HikesAdapter.ViewHolder>() {
    private val list = mutableListOf<Hike>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HikesAdapter.ViewHolder {
        val itemBinding =
            LiHikeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding, onHikeClick)
    }

    override fun onBindViewHolder(holder: HikesAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setData(items: List<Hike>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val itemBinding: LiHikeBinding,
        onHikeClick: (campId: Number) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(hike: Hike) {
            itemBinding.apply {
                tvTitle.text = hike.name
                tvLocation.text = hike.place

                if (hike.date.isNullOrEmpty()) {
                    tvDate.text = ContextCompat.getString(
                        root.context,
                        com.sabirov.resources.R.string.title_processing
                    )
                } else {
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale("ru", "RU"))
                    val date = sdf.parse(hike.date!!)
                    sdf.applyPattern("dd.MMMM.yyyy")
                    tvDate.text = if (date != null) {
                        sdf.format(date)
                    } else {
                        ContextCompat.getString(
                            root.context,
                            com.sabirov.resources.R.string.title_processing
                        )
                    }
                }
                tvParticipants.text = if (hike.users.isNotEmpty()) {
                    val sj = StringJoiner(",")
                    hike.users.forEach { sj.add(it.firstName) }
                    sj.toString()
                } else {
                    ContextCompat.getString(
                        root.context,
                        com.sabirov.resources.R.string.title_processing
                    )
                }
                if (hike.creators.isNotEmpty()) {
                    val creator = hike.creators[0]
                    tvBoss.text = "${creator.firstName} ${creator.lastName}"
                    Glide.with(root.context)
                        .load(creator.photoUrl)
                        .placeholder(com.sabirov.resources.R.drawable.ic_avatar)
                        .into(icAvatar)
                } else {
                    tvBoss.text = ContextCompat.getString(
                        root.context,
                        com.sabirov.resources.R.string.title_processing
                    )
                }
                root.setOnClickListener {
                    hike.id?.let { it1 -> onHikeClick.invoke(it1) }
                }
            }
        }
    }
}