package com.example.bersamazakatapp.ui.zakat_emas

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatPertanianBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ZakatPertanianFragment : Fragment() {

    private var _zakatPertanianBinding : FragmentZakatPertanianBinding? = null
    private val zakatPertanianBinding get() = _zakatPertanianBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter
    private var tipePembayaranRadioButton : String = "berbayar"

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

        zakatPertanianBinding.pilih.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioBtnBerbayar){
                zakatPertanianBinding.textInputLayoutKuantitas.apply {
                    hint = "Harga Panen adalah (Rp)"
                }
                tipePembayaranRadioButton = "berbayar"
            } else if (checkedId == R.id.radioBtnTadahHujan){
                zakatPertanianBinding.textInputKuantitas.apply {
                    hint = "Harga Panen adalah (Rp)"
                }
                tipePembayaranRadioButton = "tadah hujan"
            }
        }

        zakatPertanianBinding.buttonHitungZakatPertanian.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewDialog)
            dialog.show()

            var hasilPanen = zakatPertanianBinding.textInputHasilPanen.text.toString()
            var hargaPanen = zakatPertanianBinding.textInputKuantitas.text.toString()
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewJenisZakat?.text = context?.getString(R.string.zakat_pertanian)
            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_pertanian)

            if(hasilPanen.isEmpty() && hargaPanen.isEmpty()){
                zakatPertanianBinding.textInputHasilPanen.error = "Silahkan masukan hasil panen!"
                zakatPertanianBinding.textInputHasilPanen.requestFocus();
                zakatPertanianBinding.textInputKuantitas.error = "Silahkan masukan harga panen!"
                zakatPertanianBinding.textInputKuantitas.requestFocus();
                return@setOnClickListener
            } else if (hasilPanen.isEmpty()){
                zakatPertanianBinding.textInputHasilPanen.error = "Silahkan masukan hasil panen!"
                zakatPertanianBinding.textInputHasilPanen.requestFocus();
                return@setOnClickListener
            } else if (hargaPanen.isEmpty()){
                zakatPertanianBinding.textInputKuantitas.error = "Silahkan masukan harga panen!"
                zakatPertanianBinding.textInputKuantitas.requestFocus();
                return@setOnClickListener
            } else {
                if (hasilPanen.toInt() >= 653) {
                    textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    //hasil perhitungan zakat pertanian
                } else if (hasilPanen.toInt() < 653){
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
        zakatPertanianBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatPertanianFragment_to_homeFragment)
        }
        val args = arguments?.getString("PositionZakat")
        val adapterViewPager = ViewPagerAdapter(this, Bundle().apply { putString("PositionZakat", args) })
        zakatPertanianBinding.viewpagerZakatPertanian.adapter = adapterViewPager
        TabLayoutMediator(zakatPertanianBinding.tablayoutZakatPertanian,zakatPertanianBinding.viewpagerZakatPertanian){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    fun besaranZakatPertanianBerbayar(): Double{
        return 0.5
    }
    fun besaranZakatPertanianAlami(): Double{
        return 0.1
    }
    fun kalkulatorZakatPertanian(hasilPanen: Int, hargaPanen: Double): Double {
        var besaranZakatPertanian: Double = 0.0
        if(tipePembayaranRadioButton == "berbayar"){
            besaranZakatPertanian = besaranZakatPertanianBerbayar()
        } else if (tipePembayaranRadioButton == "tadah hujan"){
            besaranZakatPertanian = besaranZakatPertanianAlami()
        }
        return (besaranZakatPertanian * hasilPanen) * hargaPanen
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
}