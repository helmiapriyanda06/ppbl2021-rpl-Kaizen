package com.ppbl2021_rpl_kaizen.kopiku.ui
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
import com.ppbl2021_rpl_kaizen.kopiku.Model.Admin
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.adapter.AdminAdapter
import com.ppbl2021_rpl_kaizen.kopiku.databinding.ActivityDashboardBinding
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
        setContentView(R.layout.activity_dashboard)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // inisialisasi firestore
        firestore = Firebase.firestore
        auth = Firebase.auth

        supportActionBar?.title = "Data Admin"
        binding.rvQuotes.layoutManager = LinearLayoutManager(this)
        binding.rvQuotes.setHasFixedSize(true)
        adapter = AdminAdapter(this)

        loadQuotes()
        fab_add.setOnClickListener({
            startActivity(Intent(this, AddUpdateAdminActivity::class.java))
        }
        )

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
//                .addOnFailureListener { exception ->
//                    progressbar.visibility = View.INVISIBLE
//                    Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT
//                    ).show()
//                }

        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }
}