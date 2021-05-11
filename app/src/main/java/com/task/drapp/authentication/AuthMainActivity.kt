package com.task.drapp.authentication

import android.os.Bundle
import com.task.drapp.R
import com.task.drapp.activity.BaseActivity
import com.task.drapp.authentication.fragment.LoginFragment

class AuthMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_main)

        openFragment(fragment = LoginFragment(), isReplace = true, isBackStack = false)
    }
}