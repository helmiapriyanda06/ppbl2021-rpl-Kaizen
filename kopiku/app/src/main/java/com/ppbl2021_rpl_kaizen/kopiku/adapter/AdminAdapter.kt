package com.ppbl2021_rpl_kaizen.kopiku.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ppbl2021_rpl_kaizen.kopiku.Model.Coffee
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ItemAdminBinding
import com.ppbl2021_rpl_kaizen.kopiku.helper
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_POSITION
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_QUOTE
import com.ppbl2021_rpl_kaizen.kopiku.helper.REQUEST_UPDATE
import com.ppbl2021_rpl_kaizen.kopiku.ui.AddUpdateAdminActivity

class AdminAdapter (private val activity: Activity): RecyclerView.Adapter<AdminAdapter.QuoteViewHolder>() {
    var listQuotes = ArrayList<Coffee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listQuotes.size
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(listQuotes[position],position)
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAdminBinding.bind(itemView)
        fun bind(kopi: Coffee, position: Int) {
            binding.itemName.text = kopi.namaBarang
            binding.ctyItem.text = kopi.kategori
            binding.price.text = kopi.hargaBarang
            Glide.with(binding.imgV)
                .load(kopi.fotoBarang)
                .into(binding.imgV)

            binding.btnUpdate.setOnClickListener {
                val intent = Intent(activity, AddUpdateAdminActivity::class.java)
                intent.putExtra(EXTRA_POSITION, position)
                intent.putExtra(EXTRA_QUOTE, kopi)
                activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }


}