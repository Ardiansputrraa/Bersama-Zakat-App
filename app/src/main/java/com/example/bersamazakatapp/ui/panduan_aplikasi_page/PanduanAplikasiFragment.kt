package com.example.bersamazakatapp.ui.panduan_aplikasi_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.AdapterPanduan
import com.example.bersamazakatapp.data.ProfilPengembang
import com.example.bersamazakatapp.data.RecyclePanduan
import com.example.bersamazakatapp.databinding.FragmentPanduanAplikasiBinding

class PanduanAplikasiFragment : Fragment() {

    private var _panduanAplikasiBinding: FragmentPanduanAplikasiBinding? = null
    private val panduanAplikasiBinding get() = _panduanAplikasiBinding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterPanduan
    private lateinit var panduanArrayList: ArrayList<RecyclePanduan>

    lateinit var ivKontenPanduan: Array<Int>
    lateinit var tvJudulPanduan: Array<String>
    lateinit var tvPanduan: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _panduanAplikasiBinding = FragmentPanduanAplikasiBinding.inflate(inflater, container, false)
        val view = panduanAplikasiBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _panduanAplikasiBinding = FragmentPanduanAplikasiBinding.bind(view)

        dataInitialize()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerViewPanduan)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        adapter = AdapterPanduan(panduanArrayList)
        recyclerView.adapter = adapter


    }
    private fun dataInitialize(){
        panduanArrayList = arrayListOf<RecyclePanduan>()

        ivKontenPanduan = arrayOf(
            R.drawable.video_mode,
            R.drawable.video_home_page,
            R.drawable.video_perhitungan,
            R.drawable.video_hasil_perhitungan,
            R.drawable.video_konten,
            R.drawable.video_about,
            R.drawable.video_about
        )

        tvJudulPanduan = arrayOf(
            "Ubah Mode Gelap/Terang",
            "Halaman Beranda",
            "Kalkulator Zakat",
            "Hasil Perhitungan Zakat",
            "Materi Zakat",
            "Navigasi Lainnya",
            "Tentang Aplikasi"
        )

        tvPanduan = arrayOf(
            getString(R.string.text_fitur_mode),
            getString(R.string.text_home_page),
            getString(R.string.text_perhitungan),
            getString(R.string.text_hasil_perhitungan),
            getString(R.string.text_konten),
            getString(R.string.text_bottom_navigation),
            getString(R.string.text_about)
        )
        for (i in ivKontenPanduan.indices){
            val recyclePanduan = RecyclePanduan(tvJudulPanduan[i], ivKontenPanduan[i], tvPanduan[i])
            panduanArrayList.add(recyclePanduan)
        }

    }
}
