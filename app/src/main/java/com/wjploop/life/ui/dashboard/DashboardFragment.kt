package com.wjploop.life.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wjploop.life.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        val appAdapter = AppAdapter(itemClick = { appItem ->
            context?.packageManager?.getLaunchIntentForPackage(appItem.packageName)?.let {
                startActivity(it)
            }
        })

        binding.rvApp.run {
            adapter = appAdapter
        }

        lifecycleScope.launchWhenCreated {
            dashboardViewModel.startLoad()
        }

        dashboardViewModel.apps.observe(viewLifecycleOwner, {
            appAdapter.submitList(it)
        })
        return binding.root
    }

}