package com.task.drapp.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.task.drapp.R
import com.task.drapp.viewmodel.MainActivityViewModel

class ProspectionServiceFragment : Fragment() {

    lateinit var prospectionViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prospection_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prospectionViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        prospectionViewModel.fetchProspectionServicesDetails()

        val textViewAboutProspectionService =
            view.findViewById<AppCompatTextView>(R.id.textViewAboutProspectionService)
        val spannableTextViewMakeAppointment =
            SpannableString(textViewAboutProspectionService.text.toString())



        val toggleButtonLanguage =
            view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonLanguage)
        val textViewAboutProspectionServiceData =
            view.findViewById<AppCompatTextView>(R.id.textViewAboutProspectionServiceData)
        val buttonEnglish = view.findViewById<MaterialButton>(R.id.buttonEnglish)
        val textViewWhyTherapyText = view.findViewById<AppCompatTextView>(R.id.textViewWhyTherapyText)
        val textViewWhyTherapy = view.findViewById<AppCompatTextView>(R.id.textViewWhyTherapy)

        buttonEnglish.isChecked = true
        var english = ""
        var gujju = ""

        var why_english = ""
        var why_gujju = ""


        prospectionViewModel.getLangAboutMagnetTherapy.observe(this, {
            textViewAboutProspectionServiceData.text = it[0]
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
                        textViewAboutProspectionServiceData.text = english
                        textViewWhyTherapy.text = view.context.getString(R.string.label_why_prospection_eng)
                        textViewWhyTherapyText.text = why_english
                    }
                }

                R.id.buttonGujarati -> {
                    if (isChecked) {
                        textViewAboutProspectionServiceData.text = gujju
                        textViewWhyTherapy.text = view.context.getString(R.string.label_why_prospection_guj)
                        textViewWhyTherapyText.text = why_gujju
                    }
                }
            }
        }

        spannableTextViewMakeAppointment.setSpan(
            UnderlineSpan(),
            0,
            textViewAboutProspectionService.text.length,
            0
        )

        textViewAboutProspectionService.text = spannableTextViewMakeAppointment
    }
}