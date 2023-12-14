package com.example.bersamazakatapp.ui.about_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.R.layout.list_pengembang_aplikasi
import com.example.bersamazakatapp.adapter.AdapterAbout
import com.example.bersamazakatapp.adapter.RecyclerAdapter
import com.example.bersamazakatapp.data.ProfilPengembang
import com.example.bersamazakatapp.databinding.FragmentAboutBinding
import com.example.bersamazakatapp.databinding.FragmentHomeBinding
import com.example.bersamazakatapp.databinding.FragmentZakatFitrahBinding
import java.text.ParsePosition


class AboutFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterAbout
    private lateinit var pengembangArrayList: ArrayList<ProfilPengembang>
    private var _aboutBinding : FragmentAboutBinding? = null
    private val aboutBinding get() = _aboutBinding!!

    lateinit var ivPengembang: Array<Int>
    lateinit var tvPosition: Array<String>
    lateinit var namaPengembang: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _aboutBinding = FragmentAboutBinding.inflate(inflater,container,false)
        val view = aboutBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _aboutBinding = FragmentAboutBinding.bind(view)

        dataInitialize()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdapterAbout(pengembangArrayList)
        recyclerView.adapter= adapter


    }

    private fun dataInitialize(){
        pengembangArrayList = arrayListOf<ProfilPengembang>()

        ivPengembang = arrayOf(
            R.drawable.fani_muh_ardian_saputra,
            R.drawable.enrio_hernanda,
            R.drawable.anugrah_lanpambudi,
            R.drawable.herika_hayurani,
            R.drawable.toto_heriyanto
        )

        tvPosition = arrayOf(
            "Project Manager",
            "Developer",
            "Developer",
            "Dosen Pembimbing",
            "Klien"
        )

        namaPengembang = arrayOf(
            "Fani Muh Ardian Saputra",
            "Enrio Hernanda",
            "Anugrah Lan Pambudi",
            "Herika Hayurani,M.Kom",
            "Toto Heriyanto,S.Pd.I., M.Ag"
        )
        for (i in ivPengembang.indices){
            val profilPengembang = ProfilPengembang(ivPengembang[i],tvPosition[i], namaPengembang[i])
            pengembangArrayList.add(profilPengembang)
        }

    }



}