package com.task.drapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.drapp.R
import com.task.drapp.adapter.DashboardRecyclerViewAdapter
import com.task.drapp.dataclass.DashboardInfo
import com.task.drapp.viewmodel.MainActivityViewModel

class HomeFragment : BaseFragment() {

    lateinit var doctorViewModel: MainActivityViewModel
    lateinit var textViewWelcome: AppCompatTextView
    lateinit var imageViewMagnet: AppCompatImageView
    lateinit var imageViewDrImage: AppCompatImageView
    lateinit var textViewClincName: AppCompatTextView
    lateinit var progressMagnetImage: ProgressBar
    lateinit var progressDrImage: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        doctorViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        doctorViewModel.fetchDoctorDetails()

        val arrayListDashboardInfo = ArrayList<DashboardInfo>()

        arrayListDashboardInfo.add(
            DashboardInfo(1, R.drawable.magnet, getString(R.string.label_about_magnet))
        )

        arrayListDashboardInfo.add(
            DashboardInfo(2, R.drawable.service, getString(R.string.label_prospection_service))
        )

        arrayListDashboardInfo.add(
            DashboardInfo(3, R.drawable.gallery, getString(R.string.label_gallery))
        )

        arrayListDashboardInfo.add(
            DashboardInfo(4, R.drawable.feedback, getString(R.string.label_feedback))
        )

        val recyclerViewDashboard = view.findViewById<RecyclerView>(R.id.recyclerViewDashboard)
        val dashboardRecyclerViewAdapter = DashboardRecyclerViewAdapter(navigator)

        dashboardRecyclerViewAdapter.setDashboardList(arrayListDashboardInfo)
        recyclerViewDashboard.apply {
            layoutManager = GridLayoutManager(activity!!, 2, RecyclerView.VERTICAL, false)
            adapter = dashboardRecyclerViewAdapter
        }

        doctorViewModel.getClinicName.observe(this, {
            textViewClincName.text = it
            textViewWelcome.text = view.context.getString(R.string.label_welcome_om_magnet_therapy_clinic,it)
        })

        doctorViewModel.getDrImage.observe(this, {
            progressDrImage.visibility = View.GONE
            Glide.with(view.context).load(it).into(imageViewDrImage)
            imageViewDrImage.visibility = View.VISIBLE
        })

        doctorViewModel.getMagnetImage.observe(this, {
            progressMagnetImage.visibility = View.GONE
            Glide.with(view.context).load(it).into(imageViewMagnet)
            imageViewMagnet.visibility = View.VISIBLE
        })
    }

    private fun init() {
        textViewClincName = view!!.findViewById(R.id.textViewClincName)
        textViewWelcome = view!!.findViewById(R.id.textViewWelcome)
        imageViewDrImage = view!!.findViewById(R.id.imageViewDrImage)
        imageViewMagnet = view!!.findViewById(R.id.imageViewMagnet)
        progressMagnetImage = view!!.findViewById(R.id.progressMagnetImage)
        progressDrImage = view!!.findViewById(R.id.progressDrImage)
    }
}