package com.ppbl2021_rpl_kaizen.kopiku.ui
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ppbl2021_rpl_kaizen.kopiku.Model.Admin
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.adapter.AdminAdapter
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ActivityDashboardBinding
import com.ppbl2021_rpl_kaizen.kopiku.helper.REQUEST_ADD
import com.ppbl2021_rpl_kaizen.kopiku.helper.REQUEST_UPDATE
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_ADD
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_DELETE
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_dashboard.*
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
        binding.rvQuotes.layoutManager = LinearLayoutManager(this)
        binding.rvQuotes.setHasFixedSize(true)
        adapter = AdminAdapter(this)

        firestore = Firebase.firestore
        auth = Firebase.auth

        fab_add.setOnClickListener {
            val intent = Intent(this, AddUpdateAdminActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
        loadQuotes()
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val quotesList = ArrayList<Admin>()
            val currentUser = auth.currentUser
            firestore.collection("admin")
                .get()
                .addOnSuccessListener { result ->
                    progressbar.visibility = View.INVISIBLE
                    for (document in result) {
                        val id = document.id
                        val name = document.get("name").toString()
                        val password = document.get("password").toString()
                        val role = document.get("role").toString()
                        quotesList.add(Admin(id, name, password, role))
                    }
                    if (quotesList.size > 0) {
                        binding.rvQuotes.adapter = adapter
                        adapter.listQuotes = quotesList
                    } else {
                        adapter.listQuotes = ArrayList()
                        showSnackbarMessage("Tidak ada data saat ini")
                    }
                }

        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
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