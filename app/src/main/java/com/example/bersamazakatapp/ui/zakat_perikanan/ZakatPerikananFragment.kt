package com.example.bersamazakatapp.ui.zakat_perikanan

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatPerikananBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class ZakatPerikananFragment : Fragment() {
    private var _zakatPerikananBinding : FragmentZakatPerikananBinding? = null
    private val zakatPerikananBinding get() = _zakatPerikananBinding!!

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

        zakatPerikananBinding.textInputHasilPanen.addTextChangedListener(onTextChangedListener(zakatPerikananBinding.textInputHasilPanen))
        zakatPerikananBinding.textInputHargaEmas.addTextChangedListener(onTextChangedListener(zakatPerikananBinding.textInputHargaEmas))

        zakatPerikananBinding.buttonHitungZakatPerikanan.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            dialog.setContentView(viewDialog)

            var hasilPanen = zakatPerikananBinding.textInputHasilPanen.text.toString().replace(",", "")
            var hargaEmas = zakatPerikananBinding.textInputHargaEmas.text.toString().replace(",", "")
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
        val args = arguments?.getString("PositionZakat")
        val adapterViewPager = ViewPagerAdapter(this, Bundle().apply { putString("PositionZakat", args) })
        zakatPerikananBinding.viewpagerZakatPerikanan.adapter = adapterViewPager
        TabLayoutMediator(zakatPerikananBinding.tablayoutZakatPerikanan,zakatPerikananBinding.viewpagerZakatPerikanan){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.pengertian,
            R.string.syarat,
            R.string.tata_cara,
            R.string.refrensi_pandangan
        )
    }
    private fun onTextChangedListener(editText: EditText): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                editText.removeTextChangedListener(this)
                try {
                    var originalString = s.toString()
                    val longval: Long
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    longval = originalString.toLong()
                    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString = formatter.format(longval)

                    editText.setText(formattedString)
                    editText.setSelection(editText.getText().length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                editText.addTextChangedListener(this)
            }
        }
    }
}