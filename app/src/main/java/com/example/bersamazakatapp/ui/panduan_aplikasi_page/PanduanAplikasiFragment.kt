package com.example.bersamazakatapp.ui.panduan_aplikasi_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.AdapterAbout
import com.example.bersamazakatapp.databinding.FragmentAboutBinding
import com.example.bersamazakatapp.databinding.FragmentPanduanAplikasiBinding

class PanduanAplikasiFragment : Fragment() {

    private var _panduanAplikasiBinding : FragmentPanduanAplikasiBinding? = null
    private val panduanAplikasiBinding get() = _panduanAplikasiBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _panduanAplikasiBinding = FragmentPanduanAplikasiBinding.inflate(inflater,container,false)
        val view = panduanAplikasiBinding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _panduanAplikasiBinding = FragmentPanduanAplikasiBinding.bind(view)

        Glide.with(this).load(R.drawable.video_mode).into(panduanAplikasiBinding.giftMode)
        Glide.with(this).load(R.drawable.video_home_page).into(panduanAplikasiBinding.giftHomePage)
        Glide.with(this).load(R.drawable.video_perhitungan).into(panduanAplikasiBinding.giftPerhitungan)
        Glide.with(this).load(R.drawable.video_konten).into(panduanAplikasiBinding.giftKonten)
        Glide.with(this).load(R.drawable.video_hasil_perhitungan).into(panduanAplikasiBinding.giftHasilPerhitungan)
        Glide.with(this).load(R.drawable.video_bottom_navigation).into(panduanAplikasiBinding.giftBottomNavigation)
        Glide.with(this).load(R.drawable.video_about).into(panduanAplikasiBinding.giftAbout)
    }
}