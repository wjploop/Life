package com.wjploop.life.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
                taskAdapter.setDiffNewData(it.toMutableList())
            }
        }


        binding.run {
            rvTask.apply {
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = taskAdapter
                addOnItemTouchListener(object : ItemTouchHelper.Callback(),
                    RecyclerView.OnItemTouchListener {
                    override fun getMovementFlags(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder
                    ): Int {
                        return makeFlag(
                            ItemTouchHelper.ACTION_STATE_IDLE,
                            ItemTouchHelper.LEFT
                        )
                    }

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    }

                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        return true
                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    }

                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                    }

                })
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