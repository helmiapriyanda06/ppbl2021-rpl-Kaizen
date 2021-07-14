package com.ppbl2021_rpl_kaizen.kopiku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ppbl2021_rpl_kaizen.kopiku.Model.Coffee
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.adapter.AdminAdapter
import com.ppbl2021_rpl_kaizen.kopiku.adapter.MakananAdapter
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvNoMeja : TextView
    private lateinit var adapter: MakananAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RVMakanan.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.RVMakanan.setHasFixedSize(true)
        adapter = MakananAdapter(this)

        firestore = Firebase.firestore
        auth = Firebase.auth

        tvNoMeja = findViewById(R.id.user_name)

        if(intent.extras != null)
        {
            tvNoMeja.setText(intent.getStringExtra("data1"))
        }
        loadQuotes()
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            val quotesList = ArrayList<Coffee>()
            firestore.collection("Coffee")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val id = document.id
                        val namaBarang = document.get("nama_barang").toString()
                        val hargaBarang = document.get("harga_barang").toString()
                        quotesList.add(Coffee(id, namaBarang , hargaBarang))
                    }
                    if (quotesList.size > 0) {
                        binding.RVMakanan.adapter = adapter
                        adapter.listQuotes = quotesList
                    } else {
                        adapter.listQuotes = ArrayList()
                        showSnackbarMessage("Tidak ada data saat ini")
                    }
                }

        }
    }
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.RVMakanan, message, Snackbar.LENGTH_SHORT).show()
    }
}