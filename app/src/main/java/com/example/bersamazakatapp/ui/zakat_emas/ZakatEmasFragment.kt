package com.example.bersamazakatapp.ui.zakat_emas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.ViewPagerAdapter
import com.example.bersamazakatapp.databinding.FragmentZakatEmasBinding

class ZakatEmasFragment : Fragment() {

    private var _zakatEmasBinding : FragmentZakatEmasBinding? = null
    private val zakatEmasBinding get() = _zakatEmasBinding!!
    private lateinit var adapterViewPager : ViewPagerAdapter

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


        adapterViewPager = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapterViewPager.addFragment(PengertianFragment(), "Pengertian")
        adapterViewPager.addFragment(SyaratFragment(), "Syarat")
        adapterViewPager.addFragment(TataCaraFragment(), "Tata Cara")
        adapterViewPager.addFragment(RefrensiPandanganFragment(), "Refrensi Pandangan")


        zakatEmasBinding.viewpager.adapter = adapterViewPager
        zakatEmasBinding.tablayout.setupWithViewPager(zakatEmasBinding.viewpager)

    }

//    lifecycleScope.launchWhenResumed {
//        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
//    }
}