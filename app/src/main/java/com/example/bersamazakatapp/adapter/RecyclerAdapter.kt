package com.example.bersamazakatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bersamazakatapp.home_page.RecyclerZakat
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
        holder.binding.imageView.setImageResource(zakat.zakatImage)
    }
}
