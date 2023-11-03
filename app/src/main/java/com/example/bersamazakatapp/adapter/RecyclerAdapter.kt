package com.example.bersamazakatapp.adapter

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
            when(position) {
                0 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatEmasFragment)
                1 -> it.findNavController().navigate(R.id.action_homeFragment_to_zakatProfesiFragment)
            }

        }
    }
}
