package com.task.drapp.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
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
import com.task.drapp.authentication.dataclass.RegisterUserDataClass
import com.task.drapp.dataclass.UserDetailsDataClass
import java.text.SimpleDateFormat
import java.util.*

class AppointmentFragment : BaseFragment(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    companion object {
        private const val TABLE_NAME = "appointmentDetails"
        private var lastId = 0
    }

    private var userName: String = ""
    private var userPhone: String = ""
    private var userGender: String = ""

    private lateinit var radioGroupMorningSession: RadioGroup
    private lateinit var radioGroupAfternoonSession: RadioGroup

    private lateinit var cardViewSelectTime: CardView
    private lateinit var textViewSelectTimeText: AppCompatTextView

    private lateinit var cardViewSelectDate: CardView
    private lateinit var textViewSelectDateText: AppCompatTextView

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

        setUpUserDetails()

        buttonMakeAppointment = view.findViewById(R.id.buttonMakeAppointment)

        cardViewSelectTime = view.findViewById(R.id.cardViewSelectTime)
        textViewSelectTimeText = view.findViewById(R.id.textViewSelectTimeText)

        cardViewSelectDate = view.findViewById(R.id.cardViewSelectDate)
        textViewSelectDateText = view.findViewById(R.id.textViewSelectDateText)

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

        cardViewSelectDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setUpUserDetails() {
        databaseReference = FirebaseDatabase.getInstance().reference.child("register_user")
        val pref = activity!!.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val user_id = pref.getInt("userId", -1)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val value = it.getValue(RegisterUserDataClass::class.java)
                    if (value!!.id == user_id) {
                        userPhone = value.number
                        userName = value.name
                        userGender = value.gender
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this.context!!,
            R.style.DatePickerDialogTheme, { _, year, monthOfYear, dayOfMonth ->
                textViewSelectDateText.text = getDate(
                    (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year),
                    "dd MMM, yyyy"
                )
            },
            mYear,
            mMonth,
            mDay
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun getDate(inputDate: String, dateFormat: String): String {
        return try {
            val inputFormat = SimpleDateFormat("dd-mm-yyyy")
            val outputFormat = SimpleDateFormat(dateFormat)
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
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

                if (textViewSelectTimeText.text == v.context.getString(R.string.label_select_time)) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_select_appointment),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (textViewSelectDateText.text == v.context.getString(R.string.hint_select_date)) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_select_date),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                databaseReference.child((lastId + 1).toString()).setValue(
                    UserDetailsDataClass(
                        lastId + 1,
                        userName,
                        userPhone,
                        textViewSelectTimeText.text.toString(),
                        textViewSelectDateText.text.toString(),
                        userGender
                    )
                )

                textViewSelectTimeText.text = v.context.getString(R.string.label_select_time)
                textViewSelectDateText.text = v.context.getString(R.string.hint_select_date)
                Snackbar.make(
                    view!!,
                    getString(R.string.message_success),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}