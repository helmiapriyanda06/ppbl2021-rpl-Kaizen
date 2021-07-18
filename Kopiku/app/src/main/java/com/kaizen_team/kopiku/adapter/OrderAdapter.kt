package com.kaizen_team.kopiku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaizen_team.kopiku.R
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter(val callback:(String) -> Unit) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(callback: (String) -> Unit){
            view.itemOrder.setOnClickListener {
                callback("helo")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(callback)
    }

    override fun getItemCount(): Int = 2

}