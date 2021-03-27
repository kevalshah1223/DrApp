package com.task.drapp.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.task.drapp.R
import com.task.drapp.viewmodel.MainActivityViewModel

class AboutMagnetTherapyFragment : BaseFragment() {

    lateinit var magnetViewModel: MainActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_magnet_therapy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        magnetViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        magnetViewModel.fetchMagnetTherapyDetails()

        val textViewAboutMagnetTherapy =
            view.findViewById<AppCompatTextView>(R.id.textViewAboutMagnetTherapy)
        val spannableTextViewMakeAppointment =
            SpannableString(textViewAboutMagnetTherapy.text.toString())
        val toggleButtonLanguage =
            view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonLanguage)
        val textViewAboutMagnetTherapyData =
            view.findViewById<AppCompatTextView>(R.id.textViewAboutMagnetTherapyData)
        val buttonEnglish = view.findViewById<MaterialButton>(R.id.buttonEnglish)
        val textViewWhyTherapyText = view.findViewById<AppCompatTextView>(R.id.textViewWhyTherapyText)
        val textViewWhyTherapy = view.findViewById<AppCompatTextView>(R.id.textViewWhyTherapy)

        buttonEnglish.isChecked = true
        var english = ""
        var gujju = ""

        var why_english = ""
        var why_gujju = ""

        magnetViewModel.getLangAboutMagnetTherapy.observe(this, {
            textViewAboutMagnetTherapyData.text = it[0]
            textViewWhyTherapyText.text = it[2]
            english = it[0]
            gujju = it[1]
            why_english = it[2]
            why_gujju = it[3]
        })

        toggleButtonLanguage.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                R.id.buttonEnglish -> {
                    if (isChecked) {
                        textViewAboutMagnetTherapyData.text = english
                        textViewWhyTherapy.text = view.context.getString(R.string.label_why_this_therapy)
                        textViewWhyTherapyText.text = why_english
                    }
                }

                R.id.buttonGujarati -> {
                    if (isChecked) {
                        textViewAboutMagnetTherapyData.text = gujju
                        textViewWhyTherapy.text = view.context.getString(R.string.label_why_this_therapy_guj)
                        textViewWhyTherapyText.text = why_gujju
                    }
                }
            }
        }

        spannableTextViewMakeAppointment.setSpan(
            UnderlineSpan(),
            0,
            textViewAboutMagnetTherapy.text.length,
            0
        )

        textViewAboutMagnetTherapy.text = spannableTextViewMakeAppointment
    }

}