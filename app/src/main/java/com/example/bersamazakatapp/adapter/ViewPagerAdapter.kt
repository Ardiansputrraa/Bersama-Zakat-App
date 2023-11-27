package com.example.bersamazakatapp.adapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bersamazakatapp.konten.PengertianFragment
import com.example.bersamazakatapp.konten.RefrensiPandanganFragment
import com.example.bersamazakatapp.konten.SyaratFragment
import com.example.bersamazakatapp.konten.TataCaraFragment

class ViewPagerAdapter(manager: Fragment, bundle : Bundle) : FragmentStateAdapter(manager) {

    private var bundle = bundle
    init {
        this.bundle = bundle
    }
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
                0 -> fragment = PengertianFragment()
                1 -> fragment = SyaratFragment()
                2 -> fragment = TataCaraFragment()
                3 -> fragment = RefrensiPandanganFragment()
        }
        fragment?.arguments = this.bundle
        return fragment as Fragment
    }
}