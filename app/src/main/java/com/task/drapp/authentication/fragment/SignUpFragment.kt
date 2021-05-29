package com.task.drapp.authentication.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.task.drapp.R
import com.task.drapp.activity.MainActivity
import com.task.drapp.authentication.dataclass.RegisterUserDataClass

class SignUpFragment : Fragment() {

    private lateinit var editTextUserName: AppCompatEditText
    private lateinit var editTextUserContact: AppCompatEditText
    private lateinit var editTextEmail: AppCompatEditText
    private lateinit var editTextPassword: AppCompatEditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioButtonMale: AppCompatRadioButton
    private lateinit var radioButtonFemale: AppCompatRadioButton
    private lateinit var textViewSignIn: AppCompatTextView
    private lateinit var buttonSignUp: AppCompatButton
    private var lastId = 0
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().reference.child("register_user")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    lastId = snapshot.childrenCount.toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        init()
        textViewSignIn.setOnClickListener {
            activity!!.onBackPressed()
        }

        buttonSignUp.setOnClickListener {
            if (editTextUserName.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity!!,
                    getString(R.string.error_enter_name),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (editTextUserContact.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity!!,
                    getString(R.string.error_enter_number),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (editTextEmail.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity!!,
                    "Please Enter Email Address",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var selectedGender = ""
            if (radioGroupGender.checkedRadioButtonId != -1) {
                selectedGender =
                    when {
                        radioButtonMale.isChecked -> activity!!.getString(R.string.label_male)
                        radioButtonFemale.isChecked -> activity!!.getString(R.string.label_female)
                        else -> activity!!.getString(R.string.label_other)
                    }
            } else {
                Toast.makeText(
                    activity!!,
                    getString(R.string.message_select_gender),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (editTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(
                    activity!!,
                    "Please Enter Password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            databaseReference.child((lastId + 1).toString()).setValue(
                RegisterUserDataClass(
                    id = lastId + 1,
                    name = editTextUserName.text.toString(),
                    gender = selectedGender,
                    email = editTextEmail.text.toString(),
                    number = editTextUserContact.text.toString(),
                    password = editTextPassword.text.toString()
                )
            ).addOnCompleteListener {
                Toast.makeText(activity!!, "Successfully registered", Toast.LENGTH_SHORT).show()
                activity!!.onBackPressed()
            }
        }
    }

    private fun init() {
        textViewSignIn = view!!.findViewById(R.id.textViewSignIn)
        editTextUserName = view!!.findViewById(R.id.editTextFullName)
        editTextUserContact = view!!.findViewById(R.id.editTextPhoneNumber)
        editTextEmail = view!!.findViewById(R.id.editTextEmail)
        editTextPassword = view!!.findViewById(R.id.editTextPassword)
        radioGroupGender = view!!.findViewById(R.id.radioGroupGender)
        radioButtonMale = view!!.findViewById(R.id.radioButtonMale)
        radioButtonFemale = view!!.findViewById(R.id.radioButtonFemale)
        buttonSignUp = view!!.findViewById(R.id.buttonSignUp)
    }
}