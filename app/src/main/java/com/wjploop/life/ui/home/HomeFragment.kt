package com.wjploop.life.ui.home

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.wjploop.life.App
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.Task
import com.wjploop.life.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("wolf", "HomeFragment created")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        Log.d("wolf", "is App is init ${App.isAppInit()}")
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        val taskAdapter = TaskAdapter()

        homeViewModel.run {
            showInput.observe(viewLifecycleOwner) {
                binding.fabInput.isVisible = !it
                binding.layoutInput.isVisible = it
            }
            tasks.observe(viewLifecycleOwner) {
                taskAdapter.submitList(it)
            }
        }


        binding.run {
            rvTask.apply {

                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = taskAdapter
                val header = LayoutInflater.from(context).inflate(R.layout.task_complete_header, null)
                header.measure(View.MeasureSpec.getSize(header.measuredWidth), View.MeasureSpec.getSize(header.measuredHeight))
                header.layout(0, 0, header.measuredWidth, header.measuredHeight)
                Log.d("wolf", "item decoration $itemDecorationCount")

//                addItemDecoration(object : RecyclerView.ItemDecoration() {
//
//                    // 是否是列表总第一个完成的任务
//                    fun isFirstCompleteTask(recyclerView: RecyclerView, pos: Int): Boolean {
//                        if (taskAdapter.currentList.size == 0) {
//                            return false
//                        }
//                        if (pos !in 0 until taskAdapter.currentList.size) {
//                            return false
//                        }
//
//                        val task = taskAdapter.currentList[pos]
//                        if (task.isComplete) {
//                            if (pos == 0) {
//                                return true
//                            } else {
//                                val prevTask = taskAdapter.currentList[pos - 1]
//                                if (!prevTask.isComplete) {
//                                    return true
//                                }
//                            }
//                        }
//                        return false
//                    }
//
//                    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//                        super.onDraw(c, parent, state)
////                        c.drawColor(Color.RED)
//                        header.draw(c)
//                        header.findViewById<TextView>(R.id.tv_count).text = taskAdapter.currentList.count { it.isComplete }.toString()
////                        c.drawLine(0f, 0f, c.width.toFloat(), c.height.toFloat(), Paint().apply { color = Color.WHITE })
//
//                        for (index in 0 until parent.childCount) {
//                            if (isFirstCompleteTask(parent, index)) {
//                                val child = parent.getChildAt(index)
//                                c.save()
//                                c.translate(child.left.toFloat(), child.top.toFloat() - header.height)
//                                header.draw(c)
//                                c.restore()
//                                break
//                            }
//                        }
//
//                    }
//
//
//                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//                        super.getItemOffsets(outRect, view, parent, state)
//                        val pos = getChildAdapterPosition(view)
//                        if (isFirstCompleteTask(parent, pos)) {
//                            outRect.top = header.measuredHeight
//                        }
//                    }
//                })


                // 右滑 完成任务， 左滑 删除任务
                val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.START or ItemTouchHelper.END
                ) {
                    val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24)!!
                    val doneIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_done_24)!!

                    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
                    private val background = ColorDrawable()
                    private val colorRed = Color.parseColor("#f44336")
                    private val colorBlue = Color.parseColor("#FF3D5AFE")


                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val pos = getChildAdapterPosition(viewHolder.itemView)
                        val task = taskAdapter.currentList[pos]
                        if (direction == ItemTouchHelper.START) {
                            lifecycleScope.launch {
                                homeViewModel.repo.removeTask(task)
                            }
                        } else {
                            lifecycleScope.launch {
                                if (!task.isComplete) { //
                                    homeViewModel.repo.updateTask(task.copy(isComplete = true))
                                } else {
                                    // 这样更新不太好
                                    taskAdapter.notifyItemChanged(pos)
                                }
                            }.invokeOnCompletion {
                            }
                        }
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        val itemView = viewHolder.itemView
                        val isDoneOrDelete = dX > 0
                        val iconMargin = ((itemView.bottom - itemView.top) - deleteIcon.intrinsicHeight) / 2

                        if (!isCurrentlyActive) {
//                            c.drawRect(Rect(itemView.left, itemView.top, itemView.right, itemView.bottom), clearPaint)
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            return
                        }
                        if (isDoneOrDelete) {
                            background.run {
                                color = colorBlue
                                setBounds(
                                    itemView.left, itemView.top, dX.toInt(), itemView.bottom
                                )
                                draw(c)
                            }
                            doneIcon.run {
                                setBounds(iconMargin, itemView.top + iconMargin, iconMargin + doneIcon.intrinsicWidth, itemView.bottom - iconMargin)
                                draw(c)
                            }
                        } else {
                            background.run {
                                color = colorRed
                                setBounds(
                                    itemView.right + dX.toInt(),
                                    itemView.top,
                                    itemView.right,
                                    itemView.bottom
                                )
                                draw(c)
                            }
                            deleteIcon.run {
                                setBounds(
                                    itemView.right - deleteIcon.intrinsicWidth - iconMargin,
                                    itemView.top + iconMargin,
                                    itemView.right - iconMargin,
                                    itemView.bottom - iconMargin
                                )
                                draw(c)
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    }


                }
                val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
                itemTouchHelper.attachToRecyclerView(this)

            }
            fabInput.setOnClickListener {
                homeViewModel.toggleInput()
            }
            etInput.addTextChangedListener(onTextChanged = { text, start, before, count ->
                binding.ibAdd.isEnabled = count > 0
                Log.d("wolf", "length $count")
            })
            ibAdd.setOnClickListener {
                lifecycleScope.launch {
                    homeViewModel.repo.addTask(
                        task = Task(
                            title = etInput.text!!.trim().toString()
                        )
                    )
                }
            }
        }

        return binding.root
    }
}