package com.kaizen_team.kopiku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.model.Coffee
import kotlinx.android.synthetic.main.item_menu.view.*

class ItemAdapter(private val product: List<Coffee>, private val callback: (Coffee) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(coffee: Coffee, callback: (Coffee) -> Unit) {
            view.item_name.text = coffee.nama_barang
            view.price.text = coffee.harga_barang.toString()
            Glide.with(view).
                  load(coffee.fotoBarang)
                  .into(view.img_v)
            view.itemMenu.setOnClickListener {
                callback(coffee)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product[position], callback)
    }

    override fun getItemCount(): Int = product.size

}