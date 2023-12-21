package com.example.bersamazakatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.data.RecyclePanduan

class AdapterPanduan (private val panduanList : ArrayList<RecyclePanduan>) :
    RecyclerView.Adapter<AdapterPanduan.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_panduan,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return panduanList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = panduanList[position]
        holder.tvJudulPanduan.text = currentItem.tvJudulPanduan
        holder.ivKontenPanduan.setImageResource(currentItem.ivKontenPanduan)
        holder.tvPanduan.text = currentItem.tvPanduan
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val tvJudulPanduan : TextView =  itemView.findViewById(R.id.tvJudulPanduan)
        val ivKontenPanduan : ImageView = itemView.findViewById(R.id.ivKontenPanduan)
        val tvPanduan : TextView = itemView.findViewById(R.id.tvPanduan)

    }
}