package com.example.bersamazakatapp.ui.zakat_emas

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    private lateinit var tipePengairan : String

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
        val items = listOf("Beras Putih", "Padi Gabah Kering", "Kacang Hijau")
        val autoComplete : AutoCompleteTextView = view.findViewById(R.id.textInputHasilPanen)
        val adapter = ArrayAdapter(this.requireContext(),R.layout.list_item_hasil_panen, items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            zakatPertanianBinding.textInputLayoutKuantitas.apply {
                hint = "Hasil Panen ${itemSelected} (Kg)"
            }
            zakatPertanianBinding.textInputKuantitas.text?.clear()
        }

        zakatPertanianBinding.pilih.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId == R.id.radioBtnBerbayar){
                tipePengairan = "Berbayar"
            } else if (checkedId == R.id.radioBtnTadahHujan){
                tipePengairan = "Tadah Hujan"
            }
        }

        zakatPertanianBinding.buttonHitungZakatPertanian.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewDialog)
            dialog.show()

            var jenisHasilPanen = zakatPertanianBinding.textInputHasilPanen.text.toString()
            var beratHasilPanen = zakatPertanianBinding.textInputKuantitas.text.toString()
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewJenisZakat?.text = context?.getString(R.string.zakat_pertanian)
            textViewHasilPerhitunganZakatC?.text = context?.getString(R.string.tidak_wajib_zakat_pertanian)

            if(jenisHasilPanen.isEmpty() && beratHasilPanen.isEmpty()){
                zakatPertanianBinding.textInputHasilPanen.error = "Silahkan pilih jenis panen!"
                zakatPertanianBinding.textInputHasilPanen.requestFocus();
                zakatPertanianBinding.textInputKuantitas.error = "Silahkan masukan hasil panen!"
                zakatPertanianBinding.textInputKuantitas.requestFocus();
                return@setOnClickListener
            } else if (jenisHasilPanen.isEmpty()){
                zakatPertanianBinding.textInputHasilPanen.error = "Silahkan pilih jenis panen!"
                zakatPertanianBinding.textInputHasilPanen.requestFocus();
                return@setOnClickListener
            } else if (beratHasilPanen.isEmpty()){
                zakatPertanianBinding.textInputKuantitas.error = "Silahkan masukan hasil panen!"
                zakatPertanianBinding.textInputKuantitas.requestFocus();
                return@setOnClickListener
            } else {
                dialog.show()
                textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_pertanian)
                if (jenisHasilPanen.equals("Beras Putih")) {
                    val hasilZakatBerasPutih = kalkulatorZakaktPertanianBerasPutih(beratHasilPanen.toDouble(), tipePengairan)
                    textViewHasilPerhitunganZakatA?.text = hasilZakatBerasPutih.toString() + " kg\n\n"
                    if (hasilZakatBerasPutih > 0) {
                        textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    } else {
                        textViewDetailPerhitunganZakatA?.visibility = View.GONE
                        textViewHasilPerhitunganZakatA?.visibility = View.GONE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                    }
                } else if (jenisHasilPanen.equals("Padi Gabah Kering")) {
                    val hasilZakatPadiGabah = kalkulatorZakaktPertanianPadiGabahKering(beratHasilPanen.toDouble(), tipePengairan)
                    textViewHasilPerhitunganZakatA?.text = hasilZakatPadiGabah.toString() + " kg\n\n"
                    if (hasilZakatPadiGabah > 0) {
                        textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    } else {
                        textViewDetailPerhitunganZakatA?.visibility = View.GONE
                        textViewHasilPerhitunganZakatA?.visibility = View.GONE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                    }
                } else if (jenisHasilPanen.equals("Kacang Hijau")) {
                    val hasilZakatKacangHijau = kalkulatorZakaktPertanianKacangHijau(beratHasilPanen.toDouble(), tipePengairan)
                    textViewHasilPerhitunganZakatA?.text = hasilZakatKacangHijau.toString() + " kg\n\n"
                    if (hasilZakatKacangHijau > 0) {
                        textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    } else {
                        textViewDetailPerhitunganZakatA?.visibility = View.GONE
                        textViewHasilPerhitunganZakatA?.visibility = View.GONE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                    }
                }
            }

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
    fun kalkulatorZakaktPertanianBerasPutih(beratHasilPanen: Double, tipePengairan: String): Double {
        val nisabBerasPutih = 815.758
        val kadar = if (tipePengairan.equals("Berbayar")) 5 else 10
        if (beratHasilPanen > nisabBerasPutih) {
            return beratHasilPanen * kadar / 100
        } else {
            return 0.0
        }
    }
    fun kalkulatorZakaktPertanianPadiGabahKering(beratHasilPanen: Double, tipePengairan: String): Double {
        val nisabPadiGabah = 1631.516
        val kadar = if (tipePengairan.equals("Berbayar")) 5 else 10
        if (beratHasilPanen > nisabPadiGabah) {
            return beratHasilPanen * kadar / 100
        } else {
            return 0.0
        }
    }
    fun kalkulatorZakaktPertanianKacangHijau(beratHasilPanen: Double, tipePengairan: String): Double {
        val nisabKacangHijau = 780.036
        val kadar = if (tipePengairan.equals("Berbayar")) 5 else 10
        if (beratHasilPanen > nisabKacangHijau) {
            return beratHasilPanen * kadar / 100
        } else {
            return 0.0
        }
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