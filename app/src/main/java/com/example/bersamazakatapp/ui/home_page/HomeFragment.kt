package com.example.bersamazakatapp.ui.home_page

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.RecyclerAdapter
import com.example.bersamazakatapp.data.RecyclerZakat
import com.example.bersamazakatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _homeBinding : FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

    private var switchMode: SwitchCompat? = null
    private var nightMode = false
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private lateinit var recyclerZakatList: ArrayList<RecyclerZakat>
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = homeBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(homeBinding)
        switchMode = view.findViewById(R.id.switchMode)

        sharedPreferences = requireActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences!!.getBoolean("nightMode", false)

        if (nightMode) {
            switchMode!!.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        switchMode!!.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences!!.edit()
                editor!!.putBoolean("nightMode", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences!!.edit()
                editor!!.putBoolean("nightMode", true)
            }
            editor!!.apply()
        }
    }
    private fun init(binding: FragmentHomeBinding){
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        recyclerZakatList = ArrayList()
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        addDataToList()

        recyclerAdapter = RecyclerAdapter(recyclerZakatList)
        binding.recyclerView.adapter = recyclerAdapter
    }

    private fun addDataToList(){
        recyclerZakatList.add(RecyclerZakat(R.drawable.zakat_emas))
        recyclerZakatList.add(RecyclerZakat(R.drawable.zakat_profesi))
        recyclerZakatList.add(RecyclerZakat(R.drawable.zakat_fitrah))
        recyclerZakatList.add(RecyclerZakat(R.drawable.zakat_pertanian))
        recyclerZakatList.add(RecyclerZakat(R.drawable.zakat_perikanan))
        recyclerZakatList.add(RecyclerZakat(R.drawable.zakat_peternakan))
    }
}