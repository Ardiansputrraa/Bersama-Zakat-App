package com.example.bersamazakatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bersamazakatapp.R
import com.example.bersamazakatapp.data.ProfilPengembang
import com.google.android.material.imageview.ShapeableImageView

class AdapterAbout(private val pengembangList : ArrayList<ProfilPengembang>) :
    RecyclerView.Adapter<AdapterAbout.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_pengembang_aplikasi,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pengembangList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pengembangList[position]
        holder.ivPengembang.setImageResource(currentItem.ivPengembang)
        holder.tvPosition.text = currentItem.tvPosition
        holder.namaPengembang.text = currentItem.namaPengembang
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val ivPengembang : ShapeableImageView = itemView.findViewById(R.id.ivPengembang)
        val tvPosition : TextView =  itemView.findViewById(R.id.tvPosition)
        val namaPengembang : TextView = itemView.findViewById(R.id.namaPengembang)

    }
}