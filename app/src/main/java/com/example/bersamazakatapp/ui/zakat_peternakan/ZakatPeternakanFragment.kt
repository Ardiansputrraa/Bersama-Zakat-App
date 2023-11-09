package com.example.bersamazakatapp.ui.zakat_peternakan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatPeternakanBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class ZakatPeternakanFragment : Fragment() {

    private var _zakatPeternakanBinding : FragmentZakatPeternakanBinding? = null
    private val zakatPeternakanBinding get() = _zakatPeternakanBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatPeternakanBinding = FragmentZakatPeternakanBinding.inflate(inflater,container,false)
        val view = zakatPeternakanBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _zakatPeternakanBinding = FragmentZakatPeternakanBinding.bind(view)

        val items = listOf("Kambing/Domba", "Sapi/Kerbau/Kuda", "Unta")
        val adapterListPilihHewan = ArrayAdapter(requireContext(), R.layout.list_item_hasil_panen,items)
        zakatPeternakanBinding.autoCompletePilihHewan.apply {
            setAdapter(adapterListPilihHewan)
            onItemClickListener = AdapterView.OnItemClickListener {
                    adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
            }
        }
        zakatPeternakanBinding.buttonHitungZakatPeternakan.setOnClickListener {
            val viewDialog: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(this.requireContext())
            dialog.setContentView(viewDialog)
            dialog.show()

            val imageButtonCloseBottomSheetDialog =
                view.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            imageButtonCloseBottomSheetDialog.setOnClickListener {
                dialog.dismiss()
            }
        }
        zakatPeternakanBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatPertanianFragment_to_homeFragment)
        }
        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")

        zakatPeternakanBinding.viewpagerZakatPeternakan.adapter = adapterViewPager
        zakatPeternakanBinding.tablayoutZakatPeternakan.setupWithViewPager(zakatPeternakanBinding.viewpagerZakatPeternakan)
    }
}