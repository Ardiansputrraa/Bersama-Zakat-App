package com.example.bersamazakatapp.ui.zakat_peternakan
import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatPeternakanBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ZakatPeternakanFragment : Fragment() {

    private var _zakatPeternakanBinding : FragmentZakatPeternakanBinding? = null
    private val zakatPeternakanBinding get() = _zakatPeternakanBinding!!
    private  var indexPositionItems : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _zakatPeternakanBinding = FragmentZakatPeternakanBinding.inflate(inflater,container,false)
        val view = zakatPeternakanBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _zakatPeternakanBinding = FragmentZakatPeternakanBinding.bind(view)

        zakatPeternakanBinding.textInputBanyakHewan.addTextChangedListener(onTextChangedListener(zakatPeternakanBinding.textInputBanyakHewan))

        val items = listOf("Unta", "Sapi/Kerbau/Kuda", "Kambing/Domba")
        val autoComplete : AutoCompleteTextView = view.findViewById(R.id.textInputJenisHewan)
        val adapter = ArrayAdapter(this.requireContext(),R.layout.list_item_hasil_panen, items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            indexPositionItems = i
            zakatPeternakanBinding.textInputBanyakHewan.text?.clear()
        }

        zakatPeternakanBinding.buttonHitungZakatPeternakan.setOnClickListener {
            val viewDialog: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            dialog.setContentView(viewDialog)

            var jenisHewanTernak = zakatPeternakanBinding.textInputJenisHewan.text.toString()
            var banyakHewanTernak = zakatPeternakanBinding.textInputBanyakHewan.text.toString().replace(",", "")
            val textViewJenisZakat = dialog.findViewById<TextView>(R.id.textViewJenisZakat)
            val imageButtonCloseBottomSheetDialog = dialog.findViewById<ImageButton>(R.id.imageButtonCloseBottomSheetDialog)
            val textViewDetailPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatA)
            val textViewHasilPerhitunganZakatA = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatA)
            val textViewDetailPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewDetailPerhitunganZakatB)
            val textViewHasilPerhitunganZakatB = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatB)
            val textViewHasilPerhitunganZakatC = dialog.findViewById<TextView>(R.id.textViewHasilPerhitunganZakatC)

            textViewJenisZakat?.text = context?.getString(R.string.zakat_peternakan)
            textViewDetailPerhitunganZakatA?.text = context?.getString(R.string.perhitungan_zakat_peternakan)
            if (jenisHewanTernak.isEmpty() && banyakHewanTernak.isEmpty()) {
                zakatPeternakanBinding.textInputJenisHewan.error = "Silahkan masukan jenis hewan ternak!"
                zakatPeternakanBinding.textInputJenisHewan.requestFocus();
                zakatPeternakanBinding.textInputBanyakHewan.error = "Silahkan masukan jumlah hewan ternak!"
                zakatPeternakanBinding.textInputBanyakHewan.requestFocus();
                return@setOnClickListener
            } else if (jenisHewanTernak.isEmpty()) {
                zakatPeternakanBinding.textInputJenisHewan.error = "Silahkan masukan jenis hewan ternak!"
                zakatPeternakanBinding.textInputJenisHewan.requestFocus();
                return@setOnClickListener
            } else if (banyakHewanTernak.isEmpty()) {
                zakatPeternakanBinding.textInputBanyakHewan.error = "Silahkan masukan jumlah hewan ternak!"
                zakatPeternakanBinding.textInputBanyakHewan.requestFocus();
                return@setOnClickListener
            } else {
                dialog.show()
                if (indexPositionItems == 0) {
                    textViewHasilPerhitunganZakatA?.text = kalkulatorZakatHewanUnta(banyakHewanTernak.toInt()).toString() + "\n\n"
                    textViewHasilPerhitunganZakatC?.text = kalkulatorZakatHewanUnta(banyakHewanTernak.toInt()).toString()
                    if (banyakHewanTernak.toInt() >= 5) {
                        textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    } else if (banyakHewanTernak.toInt() < 5) {
                        textViewDetailPerhitunganZakatA?.visibility = View.GONE
                        textViewHasilPerhitunganZakatA?.visibility = View.GONE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                    }
                } else if (indexPositionItems == 1) {
                    textViewHasilPerhitunganZakatA?.text = kalkulatorZakatHewanSapi(banyakHewanTernak.toInt()).toString() + "\n\n"
                    textViewHasilPerhitunganZakatC?.text = kalkulatorZakatHewanSapi(banyakHewanTernak.toInt()).toString()
                    if (banyakHewanTernak.toInt() >= 30) {
                        textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    } else if (banyakHewanTernak.toInt() < 30) {
                        textViewDetailPerhitunganZakatA?.visibility = View.GONE
                        textViewHasilPerhitunganZakatA?.visibility = View.GONE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.VISIBLE
                    }
                } else if (indexPositionItems == 2) {
                    textViewHasilPerhitunganZakatA?.text = kalkulatorZakatHewanKambing(banyakHewanTernak.toInt()).toString() + "\n\n"
                    textViewHasilPerhitunganZakatC?.text = kalkulatorZakatHewanKambing(banyakHewanTernak.toInt()).toString()
                    if (banyakHewanTernak.toInt() >= 40) {
                        textViewDetailPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewHasilPerhitunganZakatA?.visibility = View.VISIBLE
                        textViewDetailPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatB?.visibility = View.GONE
                        textViewHasilPerhitunganZakatC?.visibility = View.GONE
                    } else if (banyakHewanTernak.toInt() < 40) {
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
        zakatPeternakanBinding.imageButtonBackToHome.setOnClickListener{
            it.findNavController().navigate(R.id.action_zakatPeternakanFragment_to_homeFragment)
        }
        val args = arguments?.getString("PositionZakat")
        val adapterViewPager = ViewPagerAdapter(this, Bundle().apply { putString("PositionZakat", args) })
        zakatPeternakanBinding.viewpagerZakatPeternakan.adapter = adapterViewPager
        TabLayoutMediator(zakatPeternakanBinding.tablayoutZakatPeternakan,zakatPeternakanBinding.viewpagerZakatPeternakan){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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
    fun kalkulatorZakatHewanUnta(banyakHewanTernak : Int) : Any {
        if (banyakHewanTernak >= 5 && banyakHewanTernak < 25) {
            return "Zakat dibayarkan dengan ${(banyakHewanTernak / 5)} kambing."
        } else {
            return when {
                banyakHewanTernak in 25..35 -> "1 unta betina berumur 1 tahun."
                banyakHewanTernak in 36..45 -> "1 unta berumur 2 tahun."
                banyakHewanTernak in 46..60 -> "1 unta berumur 3 tahun."
                banyakHewanTernak in 61..75 -> "1 unta berumur 4 tahun."
                banyakHewanTernak in 76..90 -> "2 unta berumur 2 tahun."
                banyakHewanTernak in 91..120 -> "2 unta berumur 3 tahun."
                banyakHewanTernak in 121..129 -> "3 unta berumur 2 tahun."
                banyakHewanTernak in 130..139 -> "1 unta berumur 3 tahun dan 2 ekor unta berumur 2 tahun."
                banyakHewanTernak > 139 -> "140 ekor ke atas, setiap kelipatan 40 = 1 bintu labun dan setiap kelipatan 50 = 1 hiqqoh." +
                        "\n\nBINTU LABUN: unta betina berumur 2 tahun\nHIQQOH: unta betina berumur 3 tahun"
                else -> "Hasil ternak belum mencapai nisab. Tidak dikenakan kewajiban zakat peternakan\nTerimakasih.\n\n\n"
            }
        }
        return "Hasil ternak belum mencapai nisab. Tidak dikenakan kewajiban pajak\nTerimakasih.\n\n"
    }
    fun kalkulatorZakatHewanSapi(banyakHewanTernak: Int):Any {
        return when {
            banyakHewanTernak in 30..39 -> "1 ekor sapi jantan/betina umur 1 tahun memasuki tahun ke-2."
            banyakHewanTernak in 40..59 -> "1 ekor sapi betina sapi berumur 2 tahun memasuki tahun ke-3."
            banyakHewanTernak in 60..69 -> "2 ekor sapi jantan/betina umur 1 tahun memasuki tahun ke-2."
            banyakHewanTernak in 70..79 -> "2 ekor sapi, 1 ekor umur 1 tahun memasuki tahun ke-2 dan 1 ekor umur 2 tahun memasuki tahun ke-3."
            banyakHewanTernak in 80..89 -> "2 ekor sapi umur 2 tahun memasuki tahun ke-3."
            banyakHewanTernak in 90..99 -> "3 ekor umur 1 tahun memasuki tahun ke-2."
            banyakHewanTernak in 100..109 -> "3 ekor sapi, 1 ekor umur 1 tahun memasuki tahun ke-2 dan 2 ekor umur 2 tahun memasuki tahun ke-3."
            banyakHewanTernak in 110..120 -> "3 ekor sapi, 2 ekor umur 2 tahun memasuki tahun ke-3 dan 1 ekor umur 1 tahun memasuki tahun ke-2."
            banyakHewanTernak > 120 -> "3 ekor anak sapi betina atau 3 ekor anak sampi jantan. Setiap 30 ekor: 1 tabi' atau tabi'ah, setiap 40 ekor: 1 musinnah."
            else -> "Hasil ternak belum mencapai nisab. Tidak dikenakan kewajiban zakat peternakan\nTerimakasih.\n\n\n"
        }
    }
    fun kalkulatorZakatHewanKambing(banyakHewanTernak: Int):Any {
        return when {
            banyakHewanTernak in 40..120 -> "1 kambing dari jenis domba yang berumur 1 tahun atau 1 kambing dari jenis ma\'iz yang berumur 2 tahun."
            banyakHewanTernak > 120 -> "${(banyakHewanTernak / 100)} ekor kambing."
            else -> "Hasil ternak belum mencapai nisab. Tidak dikenakan kewajiban zakat peternakan\nTerimakasih.\n\n\n"
        }
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