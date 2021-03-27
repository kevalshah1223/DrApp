package com.task.drapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.task.drapp.R
import com.task.drapp.dataclass.DashboardInfo
import com.task.drapp.fragment.AboutMagnetTherapyFragment
import com.task.drapp.fragment.FeedbackFragment
import com.task.drapp.fragment.MagnetGalleryFragment
import com.task.drapp.fragment.ProspectionServiceFragment
import com.task.drapp.navigator.Navigator

class DashboardRecyclerViewAdapter(val navigator: Navigator) :
    RecyclerView.Adapter<DashboardRecyclerViewAdapter.ViewHolder>() {

    companion object {
        private const val DASHBOARD_ABOUT_MAGNET_THERAPY = 1
        private const val DASHBOARD_ABOUT_PROSPECTION_SERVICES = 2
        private const val DASHBOARD_MAGNET_GALLERY = 3
        private const val DASHBOARD_FEEDBACK = 4
    }

    private val arrayListDashboardInfo = ArrayList<DashboardInfo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageViewIcon = itemView.findViewById<AppCompatImageView>(R.id.imageViewIcon)
        private val textViewName = itemView.findViewById<AppCompatTextView>(R.id.textViewName)
        private val cardViewDashboard = itemView.findViewById<CardView>(R.id.cardViewDashboard)


        fun onBind(dashboardInfo: DashboardInfo) {
            imageViewIcon.setImageResource(dashboardInfo.icon)
            textViewName.text = dashboardInfo.label
            cardViewDashboard.setOnClickListener {
                when (arrayListDashboardInfo[adapterPosition].id) {
                    DASHBOARD_ABOUT_MAGNET_THERAPY -> {
                        /*val alert = AlertDialog.Builder(itemView.context)
                        val view = LayoutInflater.from(itemView.context).inflate(R.layout.custom_dialog_image, null)
                        view.findViewById<AppCompatImageView>(R.id.imageViewData).setImageResource(R.drawable.contact)
                        alert.setView(view)
                        val alertDialog = alert.create()
                        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog.setCanceledOnTouchOutside(false)
                        alertDialog.show()
                        view.findViewById<AppCompatImageView>(R.id.imageViewButtonClose).setOnClickListener {
                            alertDialog.dismiss()
                        }*/

                        navigator.openFragment(
                            fragment = AboutMagnetTherapyFragment(),
                            isReplace = true,
                            isBackStack = true
                        )
                    }

                    DASHBOARD_ABOUT_PROSPECTION_SERVICES -> {
                        navigator.openFragment(
                            fragment = ProspectionServiceFragment(),
                            isReplace = true,
                            isBackStack = true
                        )
                    }

                    DASHBOARD_MAGNET_GALLERY -> {
                        navigator.openFragment(
                            fragment = MagnetGalleryFragment(),
                            isReplace = true,
                            isBackStack = true
                        )
                    }

                    DASHBOARD_FEEDBACK -> {
                        navigator.openFragment(
                            fragment = FeedbackFragment(),
                            isReplace = true,
                            isBackStack = true
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_dashboard, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dashboardInfo = arrayListDashboardInfo[position]
        holder.onBind(dashboardInfo)
    }

    override fun getItemCount(): Int {
        return arrayListDashboardInfo.size
    }

    internal fun setDashboardList(arrayList: ArrayList<DashboardInfo>) {
        arrayListDashboardInfo.addAll(arrayList)
    }
}