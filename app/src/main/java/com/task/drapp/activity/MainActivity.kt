package com.task.drapp.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.task.drapp.R
import com.task.drapp.ServiceChecker
import com.task.drapp.fragment.AboutUsFragment
import com.task.drapp.fragment.AppointmentFragment
import com.task.drapp.fragment.ContactUsFragment
import com.task.drapp.fragment.HomeFragment

class MainActivity : BaseActivity() {

    lateinit var bottomMenuMain: BottomNavigationView
    private lateinit var groupMain: Group
    private lateinit var groupInternet: Group

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        replaceFragment(HomeFragment())
        bottomMenuMain = findViewById(R.id.bottomMenuMain)
        groupMain = findViewById(R.id.groupMain)
        groupInternet = findViewById(R.id.groupInternet)


        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (ServiceChecker().isConnected(this@MainActivity)!!) {
                    groupMain.visibility = View.VISIBLE
                    groupInternet.visibility = View.GONE
                } else {
                    groupMain.visibility = View.GONE
                    groupInternet.visibility = View.VISIBLE
                }

                handler.postDelayed(this, 1000)

            }
        }, 1000)


        bottomMenuMain.setOnNavigationItemSelectedListener { item ->
            replaceFragment(
                when (item.itemId) {
                    R.id.menuHome -> {
                        HomeFragment()
                    }

                    R.id.menuAppointment -> {
                        AppointmentFragment()
                    }

                    R.id.menuAboutUs -> {
                        AboutUsFragment()
                    }

                    R.id.menuContact -> {
                        ContactUsFragment()
                    }

                    else -> {
                        HomeFragment()
                    }
                }
            )

            true
        }
    }

    override fun onResume() {
        super.onResume()
        replaceFragment(
            when (bottomMenuMain.selectedItemId) {
                R.id.menuHome -> {
                    HomeFragment()
                }

                R.id.menuAppointment -> {
                    AppointmentFragment()
                }

                R.id.menuAboutUs -> {
                    AboutUsFragment()
                }

                R.id.menuContact -> {
                    ContactUsFragment()
                }

                else -> {
                    HomeFragment()
                }
            }
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        openFragment(
            fragment = fragment,
            isReplace = true,
            isBackStack = false
        )
    }
}