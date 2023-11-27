package com.example.bersamazakatapp.konten

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.databinding.FragmentPengertianBinding
import com.example.bersamazakatapp.databinding.FragmentRefrensiPandanganBinding

class RefrensiPandanganFragment : Fragment() {

    private var _refrensiPandanganBinding : FragmentRefrensiPandanganBinding? = null
    private val refrensiPandanganBinding get() = _refrensiPandanganBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _refrensiPandanganBinding = FragmentRefrensiPandanganBinding.inflate(inflater,container,false)
        val view = refrensiPandanganBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _refrensiPandanganBinding = FragmentRefrensiPandanganBinding.bind(view)

        val positionZakat = arguments?.getString("PositionZakat")
        when(positionZakat?.toInt()) {
            0 -> {
                refrensiPandanganBinding.textViewKontenRefrensiPandangan.text = context?.getString(R.string.refrensi_pandangan_zakat_emas)
            }
            1 -> {

            }
            2 -> {

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
