package com.task.drapp.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.task.drapp.R
import com.task.drapp.fragment.AboutUsFragment
import com.task.drapp.fragment.AppointmentFragment
import com.task.drapp.fragment.ContactUsFragment
import com.task.drapp.fragment.HomeFragment

class MainActivity : BaseActivity() {

    lateinit var bottomMenuMain: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        replaceFragment(HomeFragment())
        bottomMenuMain = findViewById(R.id.bottomMenuMain)

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