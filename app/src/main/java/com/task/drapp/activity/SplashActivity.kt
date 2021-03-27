package com.task.drapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.task.drapp.R
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_splash)

        Thread {
            try {
                Thread.sleep(2000)
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

    }
}