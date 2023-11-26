package com.example.bersamazakatapp.adapter

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.data.RecyclerZakat
import com.example.bersamazakatapp.databinding.CardModelBinding

class RecyclerAdapter(private val recyclerZakatList : List<RecyclerZakat>) :
    RecyclerView.Adapter<RecyclerAdapter.ZakatViewHolder>() {
    class ZakatViewHolder(val binding: CardModelBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZakatViewHolder {
        val binding = CardModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZakatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recyclerZakatList.size
    }

    override fun onBindViewHolder(holder: ZakatViewHolder, position: Int) {
        val zakat = recyclerZakatList[position]
        holder.binding.imageViewHomePage.setImageResource(zakat.zakatImage)
        holder.binding.cardViewHomePage.setOnClickListener {
            val bundle = Bundle()
            when(position) {
                0 -> bundle.putString("PositionZakat", "0")
                1 -> bundle.putString("PositionZakat", "1")
                2 -> bundle.putString("PositionZakat", "2")
                3 -> bundle.putString("PositionZakat", "3")
                4 -> bundle.putString("PositionZakat", "4")
                5 -> bundle.putString("PositionZakat", "5")
            }

            when(position) {
                0 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatEmasFragment,bundle)
                1 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatProfesiFragment)

                2 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatFitrahFragment)
                3 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatPertanianFragment)

                4 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatPerikananFragment)
                5 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatPeternakanFragment)
            }
        }
    }
}
