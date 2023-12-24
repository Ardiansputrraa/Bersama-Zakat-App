package com.example.bersamazakatapp.ui.zakat_emas

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
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
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ZakatFitrahFragment : Fragment() {

    private var _zakatFitrahBinding : FragmentZakatFitrahBinding? = null
    private val zakatFitrahBinding get() = _zakatFitrahBinding!!
    private var tipePembayaranRadioButton : String = "Liter"
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

        //editTextDecimal(zakatFitrahBinding.textInputHargaBeras)

        zakatFitrahBinding.radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            if (checkedId == R.id.radioButtonLiter) {
                zakatFitrahBinding.textInputLayoutHargaBeras.apply {
                    hint = "Harga Beras Per Liter (Rp)"
                }
                zakatFitrahBinding.textInputHargaBeras.text?.clear()
                tipePembayaranRadioButton = "Liter"
            } else if (checkedId == R.id.radioButtonKilogram) {
                zakatFitrahBinding.textInputLayoutHargaBeras.apply {
                    hint = "Harga Beras Per Kilogram (Rp)"
                }
                zakatFitrahBinding.textInputHargaBeras.text?.clear()
                tipePembayaranRadioButton = "Kilogram"
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
                dialog.show()
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
                    textViewHasilPerhitunganZakatB?.text = "${kalkulatorzakatFitrahDenganBeras(jumlahOrang.toInt())} $tipePembayaranRadioButton\n\n"
                } else {
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
        zakatFitrahBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatFitrahFragment_to_homeFragment)
        }
        val args = arguments?.getString("PositionZakat")
        val adapterViewPager = ViewPagerAdapter(this, Bundle().apply { putString("PositionZakat", args) })
        zakatFitrahBinding.viewpagerZakatFitrah.adapter = adapterViewPager
        TabLayoutMediator(zakatFitrahBinding.tablayoutZakatFitrah,zakatFitrahBinding.viewpagerZakatFitrah){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    fun besaranZakatFitrahLiter(): Double {
        return 3.5
    }
    fun besaranZakatFitrahKilogram(): Double {
        return 2.5
    }
    fun kalkulatorzakatFitrahDenganUang(jumlahOrang: Int, hargaBeras: Double): Double {
        var besaranZakatFitrah: Double = 0.0
        if (tipePembayaranRadioButton == "Liter") {
            besaranZakatFitrah = besaranZakatFitrahLiter()
        } else if (tipePembayaranRadioButton == "Kilogram") {
            besaranZakatFitrah = besaranZakatFitrahKilogram()
        }
        return jumlahOrang * besaranZakatFitrah * hargaBeras
    }
    fun kalkulatorzakatFitrahDenganBeras(jumlahOrang: Int): Double {
        var besaranZakatFitrah: Double = 0.0
        if (tipePembayaranRadioButton == "Liter") {
            besaranZakatFitrah = besaranZakatFitrahLiter()
        } else if (tipePembayaranRadioButton == "Kilogram") {
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
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.pengertian,
            R.string.syarat,
            R.string.tata_cara,
            R.string.refrensi_pandangan
        )
    }

    fun editTextDecimal(editText: EditText) {
        var current: String = ""
        editText.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged (s: Editable?) {
            }

            override fun beforeTextChanged (s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged (s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    editText.removeTextChangedListener(this)
                    val locale: Locale = Locale("in", "ID")
                    val currency = Currency.getInstance(locale)
                    val cleanString =
                        s.toString().replace("""[${currency.symbol},.]""".toRegex(), "")
                    val parsed = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance(locale).format(parsed / 100)
                    current = formatted
                    editText.setText(formatted)
                    editText.setSelection(formatted.length)
                    editText.addTextChangedListener(this)
                }
            }
        })
    }
}