package com.wjploop.life.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.diff.BrvahAsyncDifferConfig
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.Task
import com.wjploop.life.databinding.ItemTaskBinding

class TaskAdapter : BaseQuickAdapter<Task, BaseViewHolder>(R.layout.item_task) {
    init {
        setDiffCallback(diffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        })
    }

    override fun convert(holder: BaseViewHolder, item: Task) {
        holder.setText(R.id.tv_category, item.category)
        holder.setText(R.id.tv_title, item.title)
    }
}