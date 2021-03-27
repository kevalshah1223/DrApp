package com.task.drapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.task.drapp.dataclass.DoctorDataCLass
import com.task.drapp.dataclass.MagnetTherapyDataClass
import com.task.drapp.dataclass.ProspectionServicesDataClass

class MainActivityViewModel : ViewModel() {

    val getDoctorName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getClinicName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getDrImage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getMagnetImage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getLangAboutUS: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>()
    }

    val getProfileDetails: MutableLiveData<DoctorDataCLass> by lazy {
        MutableLiveData<DoctorDataCLass>()
    }

    fun fetchDoctorDetails() {
        val firebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference("personal_details")

        firebaseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorDetails = snapshot.getValue(DoctorDataCLass::class.java)
                getDoctorName.value = doctorDetails!!.name
                getClinicName.value = doctorDetails.clinic_name
                getDrImage.value = doctorDetails.profile_image
                getMagnetImage.value = doctorDetails.magnet_image
                getLangAboutUS.value =
                    arrayListOf(doctorDetails.about_eng, doctorDetails.about_gujju)
                getProfileDetails.value = doctorDetails
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    val getLangAboutMagnetTherapy: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>()
    }

    val getMagnetTherapyImages: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>()
    }

    fun fetchMagnetTherapyDetails() {
        val firebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference("magnet_therapy")

        firebaseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val magnetTherapyData = snapshot.getValue(MagnetTherapyDataClass::class.java)
                getLangAboutMagnetTherapy.value =
                    arrayListOf(magnetTherapyData!!.about_eng, magnetTherapyData.about_guj,magnetTherapyData.why_eng, magnetTherapyData.why_guj)

                magnetTherapyData.let {
                    getMagnetTherapyImages.value = it.images
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun fetchProspectionServicesDetails() {
        val firebaseDatabaseReference =
            FirebaseDatabase.getInstance().getReference("prospection_service")

        firebaseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val magnetTherapyData = snapshot.getValue(ProspectionServicesDataClass::class.java)
                getLangAboutMagnetTherapy.value =
                    arrayListOf(magnetTherapyData!!.about_eng, magnetTherapyData.about_guj,magnetTherapyData.why_eng, magnetTherapyData.why_guj)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}