package com.ppbl2021_rpl_kaizen.kopiku.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ppbl2021_rpl_kaizen.kopiku.Model.Admin
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ItemAdminBinding
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_POSITION
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_QUOTE
import com.ppbl2021_rpl_kaizen.kopiku.helper.REQUEST_UPDATE
import com.ppbl2021_rpl_kaizen.kopiku.ui.AddUpdateAdminActivity

class AdminAdapter (private val activity: Activity): RecyclerView.Adapter<AdminAdapter.QuoteViewHolder>() {
    var listQuotes = ArrayList<Admin>()

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
        fun bind(quote: Admin, position: Int) {
            binding.tvName.text = quote.name
            binding.tvPassword.text = quote.password
            binding.tvRole.text = quote.role

            binding.cvItemQuote.setOnClickListener{
                val intent = Intent(activity, AddUpdateAdminActivity::class.java)
                intent.putExtra(EXTRA_POSITION, position)
                intent.putExtra(EXTRA_QUOTE, quote)
                activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }
}