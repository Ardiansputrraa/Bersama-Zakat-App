package com.example.bersamazakatapp.konten

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.databinding.FragmentPengertianBinding
import com.example.bersamazakatapp.databinding.FragmentZakatEmasBinding

class PengertianFragment : Fragment() {

    private var _pengertianBinding : FragmentPengertianBinding? = null
    private val pengertianBinding get() = _pengertianBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _pengertianBinding = FragmentPengertianBinding.inflate(inflater,container,false)
        val view = pengertianBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _pengertianBinding = FragmentPengertianBinding.bind(view)

        val positionZakat = arguments?.getString("PositionZakat")
        when(positionZakat?.toInt()) {
            0 -> {
                pengertianBinding.textViewKontenPengertian.text = context?.getString(R.string.pengertian_zakat_emas)
            }
            1 -> {

            }
            2 -> {
                pengertianBinding.textViewKontenPengertian.text = context?.getString(R.string.pengertian_zakat_fitrah)
            }
            3 -> {
                pengertianBinding.textViewKontenPengertian.text = context?.getString(R.string.pengertian_zakat_pertanian)
            }
            4 -> {

            }
            5 -> {

            }
            6 -> {

            }
        }
    }
}