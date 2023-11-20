package com.example.bersamazakatapp.ui.zakat_emas

import android.icu.text.DecimalFormat
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
import com.example.bersamazakatapp.databinding.FragmentZakatFitrahBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class ZakatFitrahFragment : Fragment() {

    private var _zakatFitrahBinding : FragmentZakatFitrahBinding? = null
    private val zakatFitrahBinding get() = _zakatFitrahBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter
    private var tipePembayaranRadioButton : String = "liter"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatFitrahBinding = FragmentZakatFitrahBinding.inflate(inflater,container,false)
        val view = zakatFitrahBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _zakatFitrahBinding = FragmentZakatFitrahBinding.bind(view)

        zakatFitrahBinding.radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            if (checkedId == R.id.radioButtonLiter) {
                zakatFitrahBinding.textInputLayoutHargaBeras.apply {
                    hint = "Harga Beras per liter (Rp)"
                }
                tipePembayaranRadioButton = "liter"
            } else if (checkedId == R.id.radioButtonKilogram) {
                zakatFitrahBinding.textInputLayoutHargaBeras.apply {
                    hint = "Harga Beras per kilogram (Rp)"
                }
                tipePembayaranRadioButton = "kilogram"
            }
        }
        zakatFitrahBinding.buttonHitungZakatFitrah.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewDialog)

            var jumlahOrang = zakatFitrahBinding.textInputJumlahOrang.text.toString()
            var hargaBeras = zakatFitrahBinding.textInputHargaBeras.text.toString()
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewJenisZakat?.text = context?.getString(R.string.zakat_fitrah)
            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_fitrah_dengan_uang)
            textViewDetailPerhitunganZakatB?.text = context?.getString(R.string.perhitungan_zakat_fitrah_dengan_beras)

            if (jumlahOrang.isEmpty() && hargaBeras.isEmpty()) {
                zakatFitrahBinding.textInputJumlahOrang.error = "Silahkan masukan jumlah orang!"
                zakatFitrahBinding.textInputJumlahOrang.requestFocus();
                zakatFitrahBinding.textInputHargaBeras.error = "Silahkan masukan harga beras!"
                zakatFitrahBinding.textInputHargaBeras.requestFocus();
                return@setOnClickListener
            } else if (jumlahOrang.isEmpty()) {
                zakatFitrahBinding.textInputJumlahOrang.error = "Silahkan masukan jumlah orang!"
                zakatFitrahBinding.textInputJumlahOrang.requestFocus();
                return@setOnClickListener
            } else if (hargaBeras.isEmpty()) {
                zakatFitrahBinding.textInputHargaBeras.error = "Silahkan masukan harga beras!"
                zakatFitrahBinding.textInputHargaBeras.requestFocus();
                return@setOnClickListener
            } else {
                if (tipePembayaranRadioButton != null) {
                    textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    // hasil perhitungan zakat dengan uang
                    val zakatFitrahDenganUang = kalkulatorzakatFitrahDenganUang(jumlahOrang.toInt(), hargaBeras.toDouble())
                    textViewHasilPerhitunganZakatA?.text = zakatFitrahDenganUang.formatRupiah()
                    // hasil perhitungan zakat dengan beras
                    textViewHasilPerhitunganZakatB?.text = "${kalkulatorzakatFitrahDenganBeras(jumlahOrang.toInt()).toString()} $tipePembayaranRadioButton\n\n"
                } else {
                    textViewDetailPerhitunganZakatA?.visibility = View.GONE
                    textViewHasilPerhitunganZakatA?.visibility = View.GONE
                    textViewDetailPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                }
            }
            dialog.show()
            imageButtonCloseBottomSheetDialog?.setOnClickListener {
                dialog.dismiss()
            }
        }
        zakatFitrahBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatFitrahFragment_to_homeFragment)
        }
        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")

        zakatFitrahBinding.viewpagerZakatFitrah.adapter = adapterViewPager
        zakatFitrahBinding.tablayoutZakatFitrah.setupWithViewPager(zakatFitrahBinding.viewpagerZakatFitrah)
    }
    fun besaranZakatFitrahLiter(): Double {
        return 3.5
    }
    fun besaranZakatFitrahKilogram(): Double {
        return 2.5
    }
    fun kalkulatorzakatFitrahDenganUang(jumlahOrang: Int, hargaBeras: Double): Double {
        var besaranZakatFitrah: Double = 0.0
        if (tipePembayaranRadioButton == "liter") {
            besaranZakatFitrah = besaranZakatFitrahLiter()
        } else if (tipePembayaranRadioButton == "kilogram") {
            besaranZakatFitrah = besaranZakatFitrahKilogram()
        }
        return jumlahOrang * besaranZakatFitrah * hargaBeras
    }
    fun kalkulatorzakatFitrahDenganBeras(jumlahOrang: Int): Double {
        var besaranZakatFitrah: Double = 0.0
        if (tipePembayaranRadioButton == "liter") {
            besaranZakatFitrah = besaranZakatFitrahLiter()
        } else if (tipePembayaranRadioButton == "kilogram") {
            besaranZakatFitrah = besaranZakatFitrahKilogram()
        }
        return jumlahOrang * besaranZakatFitrah
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