package com.task.drapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.task.drapp.navigator.Navigator

open class BaseFragment: Fragment() {

    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = activity as Navigator
    }

}