package com.wjploop.life

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.wjploop.life.databinding.ActivityMainBinding
import com.wjploop.life.ui.dashboard.DashboardFragment
import com.wjploop.life.ui.home.HomeFragment
import com.wjploop.life.ui.notifications.NotificationsFragment
import com.wjploop.life.widget.setupWithNavController

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
//        if (savedInstanceState == null) {
//            setupBottomNavigationBar()
//        }
    }

    val homeFragment by lazy {
        HomeFragment()
    }
    val dashboardFragment by lazy {
        DashboardFragment()
    }
    val notificationFragment by lazy {
        NotificationsFragment()
    }


    private var currentNavController: LiveData<NavController>? = null


    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
//        findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_home ->
//            }
//        }
    }

    private fun showFragment(fragment: Fragment) {
        val home = supportFragmentManager.findFragmentByTag(HomeFragment::class.simpleName)
        val dash = supportFragmentManager.findFragmentByTag(DashboardFragment::class.simpleName)
        val notification =
            supportFragmentManager.findFragmentByTag(NotificationsFragment::class.simpleName)
        supportFragmentManager.apply {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

//    private var lastTimePressBack = 0L
//    override fun onBackPressed() {
//        val currentTime = System.currentTimeMillis()
//        if (lastTimePressBack + 1000 > currentTime) {
//            super.onBackPressed()
//        } else {
//            lastTimePressBack = currentTime
//            Toast.makeText(this, "Double press to exit", Toast.LENGTH_SHORT).show()
//        }
//
//    }
}