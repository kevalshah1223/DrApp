package com.task.drapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.task.drapp.R
import com.task.drapp.dataclass.UserDetailsDataClass

class AppointmentFragment : BaseFragment(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    companion object {
        private const val TABLE_NAME = "appointmentDetails"
        private var lastId = 0
    }

    private lateinit var radioGroupMorningSession: RadioGroup
    private lateinit var radioGroupAfternoonSession: RadioGroup

    private lateinit var cardViewSelectTime: CardView
    private lateinit var textViewSelectTimeText: AppCompatTextView

    private lateinit var editTextUserName: AppCompatEditText
    private lateinit var editTextUserContact: AppCompatEditText
    private lateinit var buttonMakeAppointment: AppCompatButton

    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioButtonMale: AppCompatRadioButton
    private lateinit var radioButtonFemale: AppCompatRadioButton

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextUserName = view.findViewById(R.id.editTextUserName)
        editTextUserContact = view.findViewById(R.id.editTextUserContact)
        buttonMakeAppointment = view.findViewById(R.id.buttonMakeAppointment)

        cardViewSelectTime = view.findViewById(R.id.cardViewSelectTime)
        textViewSelectTimeText = view.findViewById(R.id.textViewSelectTimeText)

        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        radioButtonMale = view.findViewById(R.id.radioButtonMale)
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale)

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

        buttonMakeAppointment.setOnClickListener(this)
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

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (group!!.id) {
            R.id.radioGroupMorningSession -> {
                if (radioGroupAfternoonSession.checkedRadioButtonId != -1) {
                    radioGroupAfternoonSession.setOnCheckedChangeListener(null)
                    radioGroupAfternoonSession.clearCheck()
                    radioGroupAfternoonSession.setOnCheckedChangeListener(this)
                }
            }

            R.id.radioGroupAfternoonSession -> {
                if (radioGroupMorningSession.checkedRadioButtonId != -1) {
                    radioGroupMorningSession.setOnCheckedChangeListener(null)
                    radioGroupMorningSession.clearCheck()
                    radioGroupMorningSession.setOnCheckedChangeListener(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cardViewSelectTime.setOnClickListener {
            val alertDialog = AlertDialog.Builder(activity!!)
            val dialogView =
                LayoutInflater.from(activity!!).inflate(R.layout.view_appointment_timing, null)

            radioGroupMorningSession = dialogView.findViewById(R.id.radioGroupMorningSession)
            radioGroupAfternoonSession = dialogView.findViewById(R.id.radioGroupAfternoonSession)

            radioGroupMorningSession.clearCheck()
            radioGroupAfternoonSession.clearCheck()

            radioGroupMorningSession.setOnCheckedChangeListener(this)
            radioGroupAfternoonSession.setOnCheckedChangeListener(this)

            alertDialog.setPositiveButton(getString(R.string.label_set_timing)) { dialog, _ ->
                if (radioGroupMorningSession.checkedRadioButtonId != -1) {
                    textViewSelectTimeText.text =
                        dialogView.findViewById<RadioButton>(radioGroupMorningSession.checkedRadioButtonId).text
                }
                if (radioGroupAfternoonSession.checkedRadioButtonId != -1) {
                    textViewSelectTimeText.text =
                        dialogView.findViewById<RadioButton>(radioGroupAfternoonSession.checkedRadioButtonId).text
                }
                dialog.dismiss()
            }

            alertDialog.setNegativeButton(getString(R.string.label_cancel)) { dialog, _ ->
                dialog.dismiss()
            }

            alertDialog.setView(dialogView)
            alertDialog.create().show()

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonMakeAppointment -> {

                if (editTextUserName.text.isNullOrEmpty()) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_enter_name),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (editTextUserContact.text.isNullOrEmpty()) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_enter_number),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (textViewSelectTimeText.text == v.context.getString(R.string.label_select_time)) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_select_appointment),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                var selectedGender = ""
                if (radioGroupGender.checkedRadioButtonId != -1) {
                    selectedGender =
                        if (radioButtonMale.isChecked) activity!!.getString(R.string.label_male) else activity!!.getString(
                            R.string.label_female
                        )
                } else {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.message_select_gender),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                databaseReference.child((lastId + 1).toString()).setValue(
                    UserDetailsDataClass(
                        lastId + 1,
                        editTextUserName.text.toString(),
                        editTextUserContact.text.toString(),
                        textViewSelectTimeText.text.toString(),
                        selectedGender
                    )
                )

                editTextUserName.text!!.clear()
                editTextUserContact.text!!.clear()
                textViewSelectTimeText.text = v.context.getString(R.string.label_select_time)

                Snackbar.make(
                    view!!,
                    getString(R.string.message_success),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}