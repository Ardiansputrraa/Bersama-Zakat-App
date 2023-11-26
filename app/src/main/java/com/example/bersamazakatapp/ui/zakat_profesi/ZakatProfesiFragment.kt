package com.example.bersamazakatapp.ui.zakat_profesi

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
import com.example.bersamazakatapp.databinding.FragmentZakatProfesiBinding
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

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

            var pemasukanBulanan = zakatProfesiBinding.textInputPenghasilan.text.toString()
            var pengeluaranBulanan = zakatProfesiBinding.textInputPengeluaran.text.toString()
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = view.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewJenisZakat?.text = context?.getString(R.string.zakat_profesi)
            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_profesi)
            textViewHasilPerhitunganZakatC?.text = context?.getString(R.string.tidak_wajib_zakat_profesi)

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
                if (pemasukanBulanan.toInt() > 0) {
                    textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatB?.visibility = View.GONE
                    textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    // hasil perhitungan zakat profesi
                    val zakatEmasDenganUang = kalkulatorZakatProfesi(pemasukanBulanan.toDouble(), pengeluaranBulanan.toDouble())
                    textViewHasilPerhitunganZakatA?.text = zakatEmasDenganUang.formatRupiah().toString() + "\n\n\n"
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
        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")


        zakatProfesiBinding.viewpagerZakatProfesi.adapter = adapterViewPager
        zakatProfesiBinding.tablayoutZakatProfesi.setupWithViewPager(zakatProfesiBinding.viewpagerZakatProfesi)
    }
    fun kalkulatorZakatProfesi(pemasukanBulanan: Double, pengeluaranBulanan: Double): Double {
        val zakatProfesi = 0.025
        return (pemasukanBulanan - pengeluaranBulanan) * zakatProfesi
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