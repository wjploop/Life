package com.wjploop.life.ui.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.Task

class TaskAdapter :
    ListAdapter<Task, TaskAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Task?>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            Log.d("wolf", "check item same ${oldItem == newItem}")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            val same = oldItem.isComplete == newItem.isComplete
                    && oldItem.title == oldItem.title
                    && oldItem.category == newItem.category
            Log.d("wolf", "check content same ${same}")
            return same;
        }
    }) {

    var hasHeader = currentList.firstOrNull { it.isComplete } != null


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory = itemView.findViewById<TextView>(R.id.tv_category)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val ivDone = itemView.findViewById<ImageView>(R.id.iv_done)

        fun bind(task: Task) {
            tvTitle.text = task.title
            tvCategory.text = task.category
            ivDone.isVisible = task.isComplete
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {

        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]

        holder.bind(currentList[position])

        holder.itemView.setOnClickListener {

            holder.itemView.findNavController().navigate(R.id.graph_edit_task, bundleOf("taskId" to item.id))
            Log.d("wolf", "onClick on $position")
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}