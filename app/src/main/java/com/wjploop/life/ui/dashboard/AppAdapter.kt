package com.wjploop.life.ui.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.AppItem

class AppAdapter(
    val itemClick: (AppItem) -> Unit
) :
    ListAdapter<AppItem, AppAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<AppItem?>() {
        override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.text.text = item.name
        Glide.with(holder.itemView.context).load(
            item.icon
        ).circleCrop().into(holder.icon)

        holder.itemView.setOnClickListener {
            itemClick(item)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.iv_icon)
        val text: TextView = itemView.findViewById(R.id.tv_app_name)
    }

}
