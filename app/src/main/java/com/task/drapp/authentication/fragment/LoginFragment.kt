package com.task.drapp.authentication.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.database.*
import com.task.drapp.R
import com.task.drapp.activity.MainActivity
import com.task.drapp.authentication.dataclass.RegisterUserDataClass
import com.task.drapp.fragment.BaseFragment

class LoginFragment : BaseFragment() {

    private lateinit var textViewSignUp: AppCompatTextView
    private lateinit var editTextPhoneNumber: AppCompatEditText
    private lateinit var editTextPassword: AppCompatEditText
    private lateinit var buttonLogin: AppCompatButton
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewSignUp = view.findViewById(R.id.textViewSignUp)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)

        val spanTextViewSignUp = SpannableString(textViewSignUp.text)
        spanTextViewSignUp.setSpan(
            android.text.style.StyleSpan(Typeface.BOLD),
            20,
            textViewSignUp.text.length,
            0
        )
        textViewSignUp.text = spanTextViewSignUp

        textViewSignUp.setOnClickListener {
            navigator.openFragment(
                fragment = SignUpFragment(),
                isReplace = true,
                isBackStack = true
            )
        }

        buttonLogin.setOnClickListener {

            if (editTextPhoneNumber.text.isNullOrEmpty()) {
                Toast.makeText(activity!!, "Please enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (editTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(activity!!, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            databaseReference = FirebaseDatabase.getInstance().reference.child("register_user")
            var flag = false
            var userId: Int
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val value = it.getValue(RegisterUserDataClass::class.java)
                        if (value!!.email == editTextPhoneNumber.text.toString() && value.password == editTextPassword.text.toString()) {
                            flag = false
                            userId = value.id
                            val pref =
                                activity!!.getSharedPreferences("Login", Context.MODE_PRIVATE)
                            val edit = pref.edit()
                            edit.putBoolean("isLogin", true)
                            edit.putInt("userId", userId)
                            edit.apply()
                            startActivity(Intent(activity!!, MainActivity::class.java))
                            activity!!.finish()
                            return@forEach
                        } else {
                            flag = true
                        }
                    }
                    if (flag) {
                        Toast.makeText(activity!!, "error", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity!!, "done", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}