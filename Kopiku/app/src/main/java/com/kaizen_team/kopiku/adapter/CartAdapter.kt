package com.kaizen_team.kopiku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.model.Cart
import com.kaizen_team.kopiku.model.ItemCart
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter(val cart: Cart, val callback : (ItemCart) -> Unit) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(product: ItemCart,callback : (ItemCart) -> Unit,callbackdua : (ItemCart) -> Unit){
            Glide.with(view).load(product.coffee.fotoBarang).into(view.img_v)
            view.item_name.text = product.coffee.nama_barang
            view.qty_item.text = "Qty : ${product.quantity}"
            view.tvCartTotalPrice.text = "Rp ${product.quantity * (product.coffee.harga_barang?:0)}"
            view.btnhapus.setOnClickListener {
                callback(product)
                callbackdua(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cart.products[position],callback){
            cart.products.remove(it)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = cart.products.size

}