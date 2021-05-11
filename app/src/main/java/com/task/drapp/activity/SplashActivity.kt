package com.task.drapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.task.drapp.R
import com.task.drapp.authentication.AuthMainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_splash)

        Thread {
            try {
                val pref = getSharedPreferences("Login", Context.MODE_PRIVATE)
                Thread.sleep(2000)
                if (pref.getBoolean("isLogin", false)) {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                } else {
                    startActivity(
                        Intent(this, AuthMainActivity::class.java)
                    )
                }
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

    }
}