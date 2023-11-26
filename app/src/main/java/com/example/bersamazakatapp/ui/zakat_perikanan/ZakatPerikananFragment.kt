package com.example.bersamazakatapp.ui.zakat_perikanan

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
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
import java.util.*


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

            var hasilPanen = zakatPerikananBinding.textInputHasilPanen.text.toString()
            var hargaEmas = zakatPerikananBinding.textInputHargaEmas.text.toString()
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)



            textViewJenisZakat?.text = context?.getString(R.string.zakat_perikanan)
            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_perikanan)
            textViewHasilPerhitunganZakatC?.text = context?.getString(R.string.tidak_wajib_zakat_perikanan)

            if (hasilPanen.isEmpty() && hargaEmas.isEmpty()) {
                zakatPerikananBinding.textInputHasilPanen.error = "Silahkan masukan berat hasil panen!"
                zakatPerikananBinding.textInputHasilPanen.requestFocus();
                zakatPerikananBinding.textInputHargaEmas.error = "Silahkan masukan harga hasil panen!"
                zakatPerikananBinding.textInputHargaEmas.requestFocus();
                return@setOnClickListener
            } else if (hasilPanen.isEmpty()) {
                zakatPerikananBinding.textInputHasilPanen.error = "Silahkan masukan berat hasil panen!"
                zakatPerikananBinding.textInputHasilPanen.requestFocus();
                return@setOnClickListener
            } else if (hargaEmas.isEmpty()) {
                zakatPerikananBinding.textInputHargaEmas.error = "Silahkan masukan harga hasil panen!"
                zakatPerikananBinding.textInputHargaEmas.requestFocus();
                return@setOnClickListener
            } else {
                dialog.show()
                val hasilPerhitunganZakatPerikanan = kalkulatorZakatPerikanan(hasilPanen.toDouble(), hargaEmas.toDouble())
                if (hasilPerhitunganZakatPerikanan.toInt() > 0) {
                    textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatC?.visibility = View.GONE

                    textViewHasilPerhitunganZakatA?.text = hasilPerhitunganZakatPerikanan.formatRupiah().toString() + "\n\n"
                } else if (hasilPerhitunganZakatPerikanan.toInt() <= 0) {
                    textViewDetailPerhitunganZakatA?.visibility = View.GONE
                    textViewHasilPerhitunganZakatA?.visibility = View.GONE
                    textViewDetailPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                }
            }
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
    fun kalkulatorZakatPerikanan(hasilPanen : Double, hargaEmas : Double): Double {
        val nisab = hargaEmas * 85
        return if (hasilPanen >= nisab) {
            hasilPanen * 0.025
        } else {
            0.0
        }
    }

    fun Double.formatRupiah(): String {
        val localeID = Locale("in", "ID")
        val decimalFormat = DecimalFormat.getCurrencyInstance(localeID) as DecimalFormat
        val symbol = decimalFormat.decimalFormatSymbols
        symbol.currencySymbol = "Rp "
        decimalFormat.decimalFormatSymbols = symbol
        return decimalFormat.format(this)
    }
}