package com.kaizen_team.kopiku.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.databinding.ItemAdminBinding
import com.kaizen_team.kopiku.model.Coffee
import com.kaizen_team.kopiku.ui.admin.AddUpdateActivity
import com.kaizen_team.kopiku.ui.admin.helper.EXTRA_POSITION
import com.kaizen_team.kopiku.ui.admin.helper.EXTRA_QUOTE
import com.kaizen_team.kopiku.ui.admin.helper.REQUEST_UPDATE

class AdminAdapter (private val activity: Activity): RecyclerView.Adapter<AdminAdapter.QuoteViewHolder>() {
    var listCoffee = ArrayList<Coffee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listCoffee.size
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(listCoffee[position], position)
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAdminBinding.bind(itemView)
        fun bind(kopi: Coffee, position: Int) {
            binding.itemName.text = kopi.nama_barang
            binding.ctyItem.text = kopi.kategori
            binding.price.text = kopi.harga_barang.toString()
            Glide.with(binding.imgV)
                .load(kopi.fotoBarang)
                .into(binding.imgV)

            binding.btnUpdate.setOnClickListener {
                val intent = Intent(activity, AddUpdateActivity::class.java)
                intent.putExtra(EXTRA_POSITION, position)
                intent.putExtra(EXTRA_QUOTE, kopi)
                activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }
}