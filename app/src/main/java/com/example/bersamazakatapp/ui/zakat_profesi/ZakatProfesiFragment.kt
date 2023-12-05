package com.example.bersamazakatapp.ui.zakat_profesi

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
import com.example.bersamazakatapp.databinding.FragmentZakatProfesiBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ZakatProfesiFragment : Fragment() {
    private var _zakatProfesiBinding : FragmentZakatProfesiBinding? = null
    private val zakatProfesiBinding get() = _zakatProfesiBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter
    private lateinit var jenisZakatProfesi : String

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

        zakatProfesiBinding.radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            if (checkedId == R.id.radioButtonMUI) {
                zakatProfesiBinding.textInputLayoutPengeluaran.hint = "Harga Emas (gram)"
                jenisZakatProfesi = zakatProfesiBinding.radioButtonMUI.text.toString()
            } else if (checkedId == R.id.radioButtonBaznas) {
                zakatProfesiBinding.textInputLayoutPengeluaran.hint = "Pengeluaran"
                jenisZakatProfesi = zakatProfesiBinding.radioButtonBaznas.text.toString()
            }
        }

        zakatProfesiBinding.buttonHitungZakatProfesi.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(this.requireContext())
            dialog.setContentView(viewDialog)

            var pemasukanBulanan = zakatProfesiBinding.textInputPenghasilan.text.toString()
            var pengeluaranBulanan = zakatProfesiBinding.textInputPengeluaran.text.toString()
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = view.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_profesi_perbulan)
            textViewDetailPerhitunganZakatB?.text = context?.getString(R.string.perhitungan_zakat_profesi_pertahun)

            if (pemasukanBulanan.isEmpty() && pengeluaranBulanan.isEmpty()) {
                zakatProfesiBinding.textInputPenghasilan.error = "Silahkan masukan pemasukan bulanan!"
                zakatProfesiBinding.textInputPenghasilan.requestFocus();
                zakatProfesiBinding.textInputPengeluaran.error = "Silahkan masukan pengeluaran bulanan!"
                zakatProfesiBinding.textInputPengeluaran.requestFocus();
                return@setOnClickListener
            } else if (pemasukanBulanan.isEmpty()) {
                zakatProfesiBinding.textInputPenghasilan.error = "Silahkan masukan pemasukan bulanan!"
                zakatProfesiBinding.textInputPenghasilan.requestFocus();
                return@setOnClickListener
            } else if (pengeluaranBulanan.isEmpty()) {
                zakatProfesiBinding.textInputPengeluaran.error = "Silahkan masukan pengeluaran bulanan!"
                zakatProfesiBinding.textInputPengeluaran.requestFocus();
                return@setOnClickListener
            } else {
                // hasil perhitungan zakat profesi
                val hasilZakatProfesi = kalkulatorZakatProfesi(pemasukanBulanan.toDouble(), pengeluaranBulanan.toDouble(), jenisZakatProfesi)
                if (jenisZakatProfesi == "MUI") {
                    textViewJenisZakat?.text = context?.getString(R.string.zakat_profesi_mui)
                    textViewHasilPerhitunganZakatC?.text = context?.getString(R.string.tidak_wajib_zakat_profesi_mui)
                } else if (jenisZakatProfesi == "Baznas") {
                    textViewJenisZakat?.text = context?.getString(R.string.zakat_profesi_baznas)
                    textViewHasilPerhitunganZakatC?.text = context?.getString(R.string.tidak_wajib_zakat_profesi_baznas)
                }
                if (hasilZakatProfesi > 0) {
                    textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    textViewHasilPerhitunganZakatA?.text = hasilZakatProfesi.formatRupiah().toString() + "\n"
                    textViewHasilPerhitunganZakatB?.text = (hasilZakatProfesi * 12).formatRupiah().toString() + "\n\n\n"
                } else {
                    textViewDetailPerhitunganZakatA?.visibility = View.GONE
                    textViewHasilPerhitunganZakatA?.visibility = View.GONE
                    textViewDetailPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                }

            }
            dialog.show()
            imageButtonCloseBottomSheetDialog?.setOnClickListener{
                dialog.dismiss()
            }
        }

        zakatProfesiBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatProfesiFragment_to_homeFragment)
        }
        val args = arguments?.getString("PositionZakat")
        val adapterViewPager = ViewPagerAdapter(this, Bundle().apply { putString("PositionZakat", args) })
        zakatProfesiBinding.viewpagerZakatProfesi.adapter = adapterViewPager
        TabLayoutMediator(zakatProfesiBinding.tablayoutZakatProfesi,zakatProfesiBinding.viewpagerZakatProfesi){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    fun kalkulatorZakatProfesi(pemasukanBulanan: Double, pengeluaranBulanan: Double, jenisZakatProfesi: String): Double {
        val zakatProfesi = 0.025
        val nisabZakatProfesi = 85
        var hasilZakatProfesi = 0.0
        if (jenisZakatProfesi.equals("MUI")) {
            if ((pemasukanBulanan * 12) > (pengeluaranBulanan * nisabZakatProfesi)) {
                hasilZakatProfesi = pemasukanBulanan * zakatProfesi
            }
        } else if (jenisZakatProfesi.equals("Baznas")) {
            if ((pemasukanBulanan - pengeluaranBulanan) > 6_828_806) {
                return (pemasukanBulanan - pengeluaranBulanan) * zakatProfesi
            }
        }
        return hasilZakatProfesi
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