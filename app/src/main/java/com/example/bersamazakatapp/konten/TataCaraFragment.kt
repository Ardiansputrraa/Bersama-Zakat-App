package com.example.bersamazakatapp.konten

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.databinding.FragmentSyaratBinding
import com.example.bersamazakatapp.databinding.FragmentTataCaraBinding

class TataCaraFragment : Fragment() {

    private var _tataCaraBinding : FragmentTataCaraBinding? = null
    private val tataCaraBinding get() = _tataCaraBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _tataCaraBinding = FragmentTataCaraBinding.inflate(inflater,container,false)
        val view = tataCaraBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _tataCaraBinding = FragmentTataCaraBinding.bind(view)

        val positionZakat = arguments?.getString("PositionZakat")
        when(positionZakat?.toInt()) {
            0 -> {
                tataCaraBinding.textViewKontenTataCara.text = context?.getString(R.string.tata_cara_zakat_emas)
            }
            1 -> {

            }
            2 -> {
                tataCaraBinding.textViewKontenTataCara.text = context?.getString(R.string.tata_cara_zakat_fitrah)
            }
            3 -> {

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