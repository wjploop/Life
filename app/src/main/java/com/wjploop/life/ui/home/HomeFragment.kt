package com.wjploop.life.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.wjploop.life.App
import com.wjploop.life.R
import com.wjploop.life.databinding.FragmentHomeBinding

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
        binding.rvTask.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = taskAdapter
        }

        homeViewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.setDiffNewData(it.toMutableList())
        }
        return binding.root
    }
}