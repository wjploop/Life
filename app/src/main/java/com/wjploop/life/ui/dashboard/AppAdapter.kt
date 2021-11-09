package com.wjploop.life.ui.dashboard

import android.content.pm.PackageInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wjploop.life.R
import com.wjploop.life.ui.home.TaskAdapter

class AppAdapter :
    ListAdapter<PackageInfo, AppAdapter.ViewHolder>(AsyncDifferConfig.Builder<PackageInfo>(object :
        DiffUtil.ItemCallback<PackageInfo?>() {
        override fun areItemsTheSame(oldItem: PackageInfo, newItem: PackageInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PackageInfo, newItem: PackageInfo): Boolean {
            return oldItem.applicationInfo.packageName == newItem.applicationInfo.packageName
                    &&
                    oldItem.applicationInfo.icon == newItem.applicationInfo.icon
        }
    }).build()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.text.text = item.applicationInfo.name
        holder.icon.setImageResource(item.applicationInfo.icon)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon:ImageView = itemView.findViewById<ImageView>(R.id.iv_icon)
        val text: TextView = itemView.findViewById(R.id.tv_app_name)
    }



}