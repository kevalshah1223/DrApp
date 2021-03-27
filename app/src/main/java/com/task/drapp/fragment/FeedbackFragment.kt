package com.task.drapp.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.database.*
import com.task.drapp.R
import com.task.drapp.dataclass.FeedbackDataClass
import com.task.drapp.dataclass.UserDetailsDataClass


class FeedbackFragment : BaseFragment(), View.OnClickListener {

    companion object {
        private const val TABLE_NAME = "userFeedback"
        private var lastId = 0
    }

    private lateinit var editTextSubject: AppCompatEditText
    private lateinit var editTextFeedback: AppCompatEditText
    private lateinit var buttonFeedback: AppCompatButton

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextSubject = view.findViewById(R.id.editTextSubject)
        editTextFeedback = view.findViewById(R.id.editTextFeedback)
        buttonFeedback = view.findViewById(R.id.buttonFeedback)
        databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    lastId = snapshot.childrenCount.toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        buttonFeedback.setOnClickListener(this)


        val textViewMakeAppointment =
            view.findViewById<AppCompatTextView>(R.id.textViewMakeAppointment)
        val spannableTextViewMakeAppointment =
            SpannableString(textViewMakeAppointment.text.toString())

        spannableTextViewMakeAppointment.setSpan(
            UnderlineSpan(),
            0,
            textViewMakeAppointment.text.length,
            0
        )
        textViewMakeAppointment.text = spannableTextViewMakeAppointment
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonFeedback -> {
                if (editTextSubject.text.isNullOrEmpty()) {
                    Toast.makeText(
                        activity!!,
                        "Enter Subject",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (editTextFeedback.text.isNullOrEmpty()) {
                    Toast.makeText(
                        activity!!,
                        "Enter Feedback",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                databaseReference.child((lastId + 1).toString()).setValue(
                    FeedbackDataClass(
                        lastId + 1,
                        editTextSubject.text.toString(),
                        editTextFeedback.text.toString()
                    )
                )

                editTextSubject.text!!.clear()
                editTextFeedback.text!!.clear()
                Toast.makeText(
                    activity!!,
                    getString(R.string.message_success),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}