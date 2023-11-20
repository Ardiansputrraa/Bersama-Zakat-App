package com.example.bersamazakatapp.ui.zakat_perikanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatFitrahBinding
import com.example.bersamazakatapp.databinding.FragmentZakatPerikananBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog


class ZakatPerikananFragment : Fragment() {
    private var _zakatPerikananBinding : FragmentZakatPerikananBinding? = null
    private val zakatPerikananBinding get() = _zakatPerikananBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatPerikananBinding = FragmentZakatPerikananBinding.inflate(inflater,container,false)
        val view = zakatPerikananBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _zakatPerikananBinding = FragmentZakatPerikananBinding.bind(view)

        zakatPerikananBinding.buttonHitungZakatPerikanan.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewDialog)
            dialog.show()

            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            imageButtonCloseBottomSheetDialog?.setOnClickListener {
                dialog.dismiss()
            }
        }
        zakatPerikananBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatPerikananFragment_to_homeFragment)
        }
        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")

        zakatPerikananBinding.viewpagerZakatPerikanan.adapter = adapterViewPager
        zakatPerikananBinding.tablayoutZakatPerikanan.setupWithViewPager(zakatPerikananBinding.viewpagerZakatPerikanan)
    }
}