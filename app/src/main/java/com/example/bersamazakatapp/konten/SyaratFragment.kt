package com.example.bersamazakatapp.konten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.databinding.FragmentPengertianBinding
import com.example.bersamazakatapp.databinding.FragmentSyaratBinding

class SyaratFragment : Fragment() {

    private var _syaratBinding : FragmentSyaratBinding? = null
    private val syaratBinding get() = _syaratBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _syaratBinding = FragmentSyaratBinding.inflate(inflater,container,false)
        val view = syaratBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _syaratBinding = FragmentSyaratBinding.bind(view)

        val positionZakat = arguments?.getString("PositionZakat")
        when(positionZakat?.toInt()) {
            0 -> {
                syaratBinding.textViewKontenSyarat.text = context?.getString(R.string.syarat_zakat_emas)
            }
            1 -> {

            }
            2 -> {
                syaratBinding.textViewKontenSyarat.text = context?.getString(R.string.syarat_zakat_fitrah)
            }
            3 -> {
                syaratBinding.textViewKontenSyarat.text = context?.getString(R.string.syarat_zakat_pertanian)
            }
            4 -> {
                syaratBinding.textViewKontenSyarat.text = context?.getString(R.string.syarat_zakat_perikanan)
            }
            5 -> {
                syaratBinding.textViewKontenSyarat.text = context?.getString(R.string.syarat_zakat_peternakan)
            }
            6 -> {

            }
        }
    }
}