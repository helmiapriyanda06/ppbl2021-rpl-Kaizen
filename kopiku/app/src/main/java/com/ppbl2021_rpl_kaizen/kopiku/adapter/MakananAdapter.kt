package com.ppbl2021_rpl_kaizen.kopiku.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ppbl2021_rpl_kaizen.kopiku.Model.Coffee
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ItemAdminBinding
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ItemMainBinding
import com.ppbl2021_rpl_kaizen.kopiku.helper
import com.ppbl2021_rpl_kaizen.kopiku.ui.AddUpdateAdminActivity

class MakananAdapter (private val activity: Activity): RecyclerView.Adapter<MakananAdapter.QuoteViewHolder>() {
    var listQuotes = ArrayList<Coffee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listQuotes.size
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(listQuotes[position],position)
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMainBinding.bind(itemView)
        fun bind(kopi: Coffee, position: Int) {
            binding.itemName.text = kopi.namaBarang
            binding.price.text = kopi.hargabarang.toString()
            Glide.with(binding.imgV)
                .load(kopi.fotoBarang)
                .into(binding.imgV)

        }
    }


}