package com.example.bersamazakatapp.ui.zakat_emas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatEmasBinding
import com.example.bersamazakatapp.databinding.FragmentZakatFitrahBinding
import com.example.bersamazakatapp.databinding.FragmentZakatPertanianBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class ZakatPertanianFragment : Fragment() {

    private var _zakatPertanianBinding : FragmentZakatPertanianBinding? = null
    private val zakatPertanianBinding get() = _zakatPertanianBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatPertanianBinding = FragmentZakatPertanianBinding.inflate(inflater,container,false)
        val view = zakatPertanianBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        _zakatPertanianBinding = FragmentZakatPertanianBinding.bind(view)


        zakatPertanianBinding.buttonHitungZakatPertanian.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewDialog)
            dialog.show()

//            val imageButtonCloseBottomSheetDialog = dialog.findViewById<Button>(R.id.imageButtonCloseBottomSheetDialog)
//            imageButtonCloseBottomSheetDialog?.setOnClickListener {
//                dialog.dismiss()
//            }
        }

        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")


//        zakatFitrahBinding.viewpagerZakatFitrah.adapter = adapterViewPager
//        zakatFitrahBinding.tablayoutZakatFitrah.setupWithViewPager(zakatFitrahBinding.viewpagerZakatFitrah)

    }
}