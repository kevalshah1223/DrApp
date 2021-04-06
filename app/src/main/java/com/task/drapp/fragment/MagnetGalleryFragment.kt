package com.task.drapp.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.drapp.R
import com.task.drapp.adapter.MagnetGalleryAdapter
import com.task.drapp.viewmodel.MainActivityViewModel

class MagnetGalleryFragment : Fragment() {

    private lateinit var recyclerViewMagnetGallery: RecyclerView
    private lateinit var magnetGalleryViewModel: MainActivityViewModel
    private lateinit var magnetGalleryAdapter: MagnetGalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_magnet_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewMagnetGallery = view.findViewById(R.id.recyclerViewMagnetGallery)
        magnetGalleryAdapter = MagnetGalleryAdapter()
        magnetGalleryViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        magnetGalleryViewModel.fetchMagnetTherapyDetails()

        val textViewMagnetGallery =
            view.findViewById<AppCompatTextView>(R.id.textViewMagnetGallery)
        val spannableTextViewMakeAppointment =
            SpannableString(textViewMagnetGallery.text.toString())

        spannableTextViewMakeAppointment.setSpan(
            UnderlineSpan(),
            0,
            textViewMagnetGallery.text.length,
            0
        )
        textViewMagnetGallery.text = spannableTextViewMakeAppointment

        recyclerViewMagnetGallery.apply {
            layoutManager = GridLayoutManager(activity!!, 2, RecyclerView.VERTICAL, false)
            recyclerViewMagnetGallery.adapter = magnetGalleryAdapter
        }

        magnetGalleryViewModel.getMagnetTherapyImages.observe(this, {
            it.removeAt(0)
            magnetGalleryAdapter.fillAdapter(it)
            recyclerViewMagnetGallery.adapter!!.notifyDataSetChanged()
        })

    }

}