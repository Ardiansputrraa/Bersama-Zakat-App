package com.example.bersamazakatapp.ui.zakat_emas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatEmasBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates

class ZakatEmasFragment : Fragment() {

    private var _zakatEmasBinding : FragmentZakatEmasBinding? = null
    private val zakatEmasBinding get() = _zakatEmasBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter
    private var totalZakatEmasDenganEmas by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatEmasBinding = FragmentZakatEmasBinding.inflate(inflater,container,false)
        val view = zakatEmasBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _zakatEmasBinding = FragmentZakatEmasBinding.bind(view)

        zakatEmasBinding.buttonHitungZakatEmas.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewDialog)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatEmasDenganUang = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatEmasDenganUang)
            val textViewHasilPerhitunganZakatEmasDenganUang = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatEmasDenganUang)
            val textViewDetailPerhitunganZakatEmasDenganEmas = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatEmasDenganEmas)
            val textViewHasilPerhitunganZakatEmasDenganEmas = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatEmasDenganEmas)
            val textViewHasilPerhitunganZakatEmas = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatEmas)
            if (zakatEmasBinding.textInputBeratEmas.text.toString() != "" && zakatEmasBinding.textInputHargaEmas.text.toString() != "") {
                var beratEmas : Int = zakatEmasBinding.textInputBeratEmas.text.toString().toInt()
                val hargaEmas : Int = zakatEmasBinding.textInputHargaEmas.text.toString().toInt()
                if (beratEmas >= 85) {
                    textViewDetailPerhitunganZakatEmasDenganUang?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatEmasDenganUang?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatEmasDenganEmas?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatEmasDenganEmas?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatEmas?.visibility = View.GONE
                    // hasil perhitungan zakat dengan uang
                    textViewHasilPerhitunganZakatEmasDenganUang?.text = kalkulatorZakatEmasDenganUang(hargaEmas.toDouble(), beratEmas.toDouble()).toInt().toString()
                    // hasil perhitungan zakat dengan emas
                    textViewHasilPerhitunganZakatEmasDenganEmas?.text = kalkulatorZakatEmasDenganEmas(beratEmas.toDouble()).toString()
                } else if (beratEmas < 85) {
                    textViewDetailPerhitunganZakatEmasDenganUang?.visibility = View.GONE
                    textViewHasilPerhitunganZakatEmasDenganUang?.visibility = View.GONE
                    textViewDetailPerhitunganZakatEmasDenganEmas?.visibility = View.GONE
                    textViewHasilPerhitunganZakatEmasDenganEmas?.visibility = View.GONE
                    textViewHasilPerhitunganZakatEmas?.visibility = View.VISIBLE
                }
            } else {
                textViewDetailPerhitunganZakatEmasDenganUang?.visibility = View.GONE
                textViewHasilPerhitunganZakatEmasDenganUang?.visibility = View.GONE
                textViewDetailPerhitunganZakatEmasDenganEmas?.visibility = View.GONE
                textViewHasilPerhitunganZakatEmasDenganEmas?.visibility = View.GONE
                textViewHasilPerhitunganZakatEmas?.visibility = View.VISIBLE
            }

            dialog.show()

            imageButtonCloseBottomSheetDialog?.setOnClickListener {
                dialog.dismiss()
            }
        }
        zakatEmasBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatEmasFragment_to_homeFragment)
        }
        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")

        zakatEmasBinding.viewpagerZakatEmas.adapter = adapterViewPager
        zakatEmasBinding.tablayoutZakagEmas.setupWithViewPager(zakatEmasBinding.viewpagerZakatEmas)
    }

    fun kalkulatorZakatEmasDenganUang(hargaEmas: Double, beratEmas : Double) : Double {
        val zakatEmas = 0.025
        return (hargaEmas * beratEmas) * zakatEmas
    }

    fun kalkulatorZakatEmasDenganEmas(beratEmas : Double) : Double {
        val zakatEmas = 0.025
        return beratEmas * zakatEmas
    }
}