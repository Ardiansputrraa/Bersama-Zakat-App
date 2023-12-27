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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatEmasBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
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

        zakatEmasBinding.textInputBeratEmas.addTextChangedListener(onTextChangedListener(zakatEmasBinding.textInputBeratEmas))
        zakatEmasBinding.textInputHargaEmas.addTextChangedListener(onTextChangedListener(zakatEmasBinding.textInputHargaEmas))

        zakatEmasBinding.buttonHitungZakatEmas.setOnClickListener{
            val viewDialog : View = layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            dialog.setContentView(viewDialog)

            var beratEmas = zakatEmasBinding.textInputBeratEmas.text.toString().replace(",", "")
            var hargaEmas = zakatEmasBinding.textInputHargaEmas.text.toString().replace(",", "")
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewJenisZakat?.text = context?.getString(R.string.zakat_emas)
            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_emas_dengan_emas)
            textViewDetailPerhitunganZakatB?.text = context?.getString(R.string.perhitungan_zakat_emas_dengan_uang)
            textViewHasilPerhitunganZakatC?.text = context?.getString(R.string.tidak_wajib_zakat_emas)

            if (beratEmas.isEmpty() && hargaEmas.isEmpty()) {
                zakatEmasBinding.textInputBeratEmas.error = "Silahkan masukan berat emas!"
                zakatEmasBinding.textInputBeratEmas.requestFocus();
                zakatEmasBinding.textInputHargaEmas.error = "Silahkan masukan harga emas saat ini!"
                zakatEmasBinding.textInputHargaEmas.requestFocus();
                return@setOnClickListener
            } else if (beratEmas.isEmpty()) {
                zakatEmasBinding.textInputBeratEmas.error = "Silahkan masukan berat emas!"
                zakatEmasBinding.textInputBeratEmas.requestFocus();
                return@setOnClickListener
            } else if (hargaEmas.isEmpty()) {
                zakatEmasBinding.textInputHargaEmas.error = "Silahkan masukan harga emas saat ini!"
                zakatEmasBinding.textInputHargaEmas.requestFocus();
                return@setOnClickListener
            } else {
                dialog.show()
                if (beratEmas.toInt() >= 85) {
                    textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                    textViewDetailPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatB?.visibility = View.VISIBLE
                    textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    val zakatEmasDenganUang = kalkulatorZakatEmasDenganUang(hargaEmas.toDouble(), beratEmas.toDouble())
                    textViewHasilPerhitunganZakatB?.text = zakatEmasDenganUang.formatRupiah().toString() + "\n\n"
                    textViewHasilPerhitunganZakatA?.text = kalkulatorZakatEmasDenganEmas(beratEmas.toDouble()).toString() + " gram"
                } else if (beratEmas.toInt() < 85) {
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
        zakatEmasBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatEmasFragment_to_homeFragment)
        }
        val args = arguments?.getString("PositionZakat")
        val adapterViewPager = ViewPagerAdapter(this, Bundle().apply { putString("PositionZakat", args) })
        zakatEmasBinding.viewpagerZakatEmas.adapter = adapterViewPager
        TabLayoutMediator(zakatEmasBinding.tablayoutZakatEmas,zakatEmasBinding.viewpagerZakatEmas){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    fun kalkulatorZakatEmasDenganUang(hargaEmas: Double, beratEmas: Double): Double {
        val zakatEmas = 0.025
        return (hargaEmas * beratEmas) * zakatEmas
    }

    fun kalkulatorZakatEmasDenganEmas(beratEmas: Double): Double {
        val zakatEmas = 0.025
        return beratEmas * zakatEmas
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