package com.task.drapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.task.drapp.R
import com.task.drapp.navigator.Navigator

open class BaseActivity : AppCompatActivity(), Navigator {

    private fun fragmentTransaction(fragment: Fragment, isReplace: Boolean, isBackStack: Boolean, bundle: Bundle? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isReplace) {
            fragmentTransaction.replace(R.id.placeHolder, fragment, fragment::class.java.simpleName)
        } else {
            fragmentTransaction.add(R.id.placeHolder, fragment, fragment::class.java.simpleName)
        }

        if (isBackStack) {
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        }

        bundle?.let {
            fragment.arguments = bundle
        }

        fragmentTransaction.commit()
    }

    override fun openFragment(fragment: Fragment, isReplace: Boolean, isBackStack: Boolean, bundle: Bundle?) {
        fragmentTransaction(fragment, isReplace, isBackStack, bundle)
    }
}