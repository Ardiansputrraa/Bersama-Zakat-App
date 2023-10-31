package com.example.bersamazakatapp.ui.zakat_penghasilan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatProfesiBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class ZakatProfesiFragment : Fragment() {
    private var _zakatProfesiBinding : FragmentZakatProfesiBinding? = null
    private val zakatProfesiBinding get() = _zakatProfesiBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatProfesiBinding = FragmentZakatProfesiBinding.inflate(inflater,container,false)
        val view = zakatProfesiBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _zakatProfesiBinding = FragmentZakatProfesiBinding.bind(view)

        zakatProfesiBinding.buttonHitungZakatProfesi.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(this.requireContext())
            dialog.setContentView(viewDialog)
            dialog.show()

            val imageButtonCloseBottomSheetDialog = view.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            imageButtonCloseBottomSheetDialog.setOnClickListener{
                dialog.dismiss()
            }

        }

        zakatProfesiBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatProfesiFragment_to_homeFragment)
        }
        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")


        zakatProfesiBinding.viewpagerZakatProfesi.adapter = adapterViewPager
        zakatProfesiBinding.tablayoutZakatProfesi.setupWithViewPager(zakatProfesiBinding.viewpagerZakatProfesi)

    }
}