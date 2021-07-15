package com.ppbl2021_rpl_kaizen.kopiku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ppbl2021_rpl_kaizen.kopiku.Model.Cart
import com.ppbl2021_rpl_kaizen.kopiku.Model.Coffee
import com.ppbl2021_rpl_kaizen.kopiku.Model.ItemCart
import com.ppbl2021_rpl_kaizen.kopiku.R
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter(val cart: Cart, val callback : (ItemCart) -> Unit) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(coffee: ItemCart,callback : (ItemCart) -> Unit,callbackdua : (ItemCart) -> Unit){
            Glide.with(view).load(coffee.coffee.fotoBarang).into(view.img_v)
            view.item_name.text = coffee.coffee.namaBarang
            view.qty_item.text = "Qty : ${coffee.quantity}"
            view.price.text = "Rp ${coffee.quantity * (coffee.coffee.hargaBarang?:0)}"
            view.btnhapus.setOnClickListener {
                callback(coffee)
                callbackdua(coffee)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cart.coffees[position],callback){
            cart.coffees.remove(it)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = cart.coffees.size

}