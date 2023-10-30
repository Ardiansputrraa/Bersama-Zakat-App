package com.example.bersamazakatapp.home_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.adapter.RecyclerAdapter
import com.example.bersamazakatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _homeBinding : FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

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