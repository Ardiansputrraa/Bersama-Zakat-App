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

            Bundle().apply {
                when(position) {
                    0 -> {
                        putString("PositionZakat", "0")
                        it.findNavController().navigate(R.id.action_homeFragment_to_zakatEmasFragment,this)
                    }
                    1 -> {
                        putString("PositionZakat", "1")
                        it.findNavController().navigate(R.id.action_homeFragment_to_zakatProfesiFragment, this)
                    }
                    2 -> {
                        putString("PositionZakat", "2")
                        it.findNavController().navigate(R.id.action_homeFragment_to_zakatFitrahFragment, this)
                    }
                    3 -> {
                        putString("PositionZakat", "3")
                        it.findNavController().navigate(R.id.action_homeFragment_to_zakatPertanianFragment, this)
                    }
                    4 -> {
                        putString("PositionZakat", "4")
                        it.findNavController().navigate(R.id.action_homeFragment_to_zakatPerikananFragment, this)
                    }
                    5 -> {
                        putString("PositionZakat", "5")
                        it.findNavController().navigate(R.id.action_homeFragment_to_zakatPeternakanFragment, this)
                    }
                }
            }
        }
    }
}
