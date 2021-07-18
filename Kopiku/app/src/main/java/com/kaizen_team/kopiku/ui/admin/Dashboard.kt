package com.kaizen_team.kopiku.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.adapter.AdminAdapter
import com.kaizen_team.kopiku.databinding.ActivityDashboardBinding
import com.kaizen_team.kopiku.model.Coffee
import com.kaizen_team.kopiku.ui.admin.helper.REQUEST_ADD
import com.kaizen_team.kopiku.ui.admin.helper.REQUEST_UPDATE
import com.kaizen_team.kopiku.ui.admin.helper.RESULT_ADD
import com.kaizen_team.kopiku.ui.admin.helper.RESULT_DELETE
import com.kaizen_team.kopiku.ui.admin.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.item_admin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity() {
    private lateinit var adapter: AdminAdapter
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvCoffee.layoutManager = LinearLayoutManager(this)
        binding.rvCoffee.setHasFixedSize(true)
        adapter = AdminAdapter(this)

        firestore = Firebase.firestore
        auth = Firebase.auth


        fab_add.setOnClickListener {
            val intent = Intent(this, AddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
        loadQuotes()
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val quotesList = ArrayList<Coffee>()
            firestore.collection("Coffee")
                .get()
                .addOnSuccessListener { result ->
                    progressbar.visibility = View.INVISIBLE
                    for (document in result) {
                        val id = document.id
                        val namaBarang = document.get("nama_barang").toString()
                        val hargaBarang = document.get("harga_barang").toString().toInt()
                        val kategori = document.get("kategori").toString()
                        quotesList.add(Coffee(id, namaBarang, hargaBarang, kategori))
                    }
                    if (quotesList.size > 0) {
                        binding.rvCoffee.adapter = adapter
                        adapter.listCoffee = quotesList
                    } else {
                        adapter.listCoffee = ArrayList()
                        showSnackbarMessage("Tidak ada data saat ini")
                    }
                }

        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvCoffee, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    loadQuotes()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }
}