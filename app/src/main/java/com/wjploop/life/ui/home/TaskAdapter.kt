package com.wjploop.life.ui.home

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.diff.BrvahAsyncDifferConfig
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.chad.library.adapter.base.module.BaseDraggableModule
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.Task
import com.wjploop.life.databinding.ItemTaskBinding

class TaskAdapter : BaseQuickAdapter<Task, BaseViewHolder>(R.layout.item_task), DraggableModule {
    init {
        setDiffCallback(diffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        })
        draggableModule.isSwipeEnabled = true
        draggableModule.itemTouchHelperCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        draggableModule.setOnItemSwipeListener(object : OnItemSwipeListener {

            val deleteDrawable by lazy {
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24)
            }
            val doneDrawable by
            lazy { ContextCompat.getDrawable(context, R.drawable.ic_baseline_done_24) }

            val deleteText = "delete"
            val doneText = "done"

            val textPaint = Paint().apply {
                color = Color.WHITE
                isAntiAlias = true
                isElegantTextHeight = true
            }


            override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

            }

            override fun clearView(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

            }

            override fun onItemSwipeMoving(
                canvas: Canvas,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                isCurrentlyActive: Boolean
            ) {
                if (dX > 0) {
                    canvas.drawColor(Color.BLUE)
                    canvas.drawBitmap(
                        doneDrawable!!.toBitmap(60, 60),
                        60f,
                        (viewHolder.itemView.height - 60f) / 2f,
//                        0f,
                        textPaint
                    )
                    val textBounds = Rect()
                    textPaint.getTextBounds(doneText, 0, doneText.length, textBounds)
                    canvas.drawText(
                        doneText,
                        120 + 30f,
                        (viewHolder.itemView.height) / 2f - textBounds.exactCenterY(),
                        textPaint
                    )
//                    canvas.drawText(doneText, 60f, 40f, textPaint)
                } else {
                    canvas.drawColor(Color.RED)
                    canvas.drawText("删除", 0f, 0f, Paint())
                }

            }
        })
    }


    override fun convert(holder: BaseViewHolder, item: Task) {
        holder.setText(R.id.tv_category, item.category)
        holder.setText(R.id.tv_title, item.title)
    }
}