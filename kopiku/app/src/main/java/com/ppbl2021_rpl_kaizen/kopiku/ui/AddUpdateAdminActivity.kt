package com.ppbl2021_rpl_kaizen.kopiku.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.ppbl2021_rpl_kaizen.kopiku.Model.Coffee
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_POSITION
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_QUOTE
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_ADD
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_DELETE
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_add_update_admin.*

class AddUpdateAdminActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var roleSpinnerArray = ArrayList<String>()
    private var coffee: Coffee? = null
    private var position: Int = 0
    private var roleSelection: Int = 0
    private var roleName: String = "0"
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_admin)

        firestore = Firebase.firestore
        auth = Firebase.auth
        roleSpinnerArray = getCategories()
        coffee = intent.getParcelableExtra(EXTRA_QUOTE)
        if (coffee != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            coffee = Coffee()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            btn_hapus.setOnClickListener{
                firestore.collection("admin").document(coffee?.id.toString())
                    .delete()
                    .addOnSuccessListener {
                        Log.d("delete", "DocumentSnapshot successfully deleted!"+coffee?.id.toString())
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    }.addOnFailureListener { e ->
                        Log.w("a", "Error deleting document", e)
                        Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()}
            }
            coffee?.let {
                edt_title.setText(it.namaBarang)
                edt_password.setText(it.jumlahBarang)
                edt_harga.setText(it.hargaBarang)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
            btn_hapus.setEnabled(false)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn_simpan.text = btnTitle
        btn_simpan.setOnClickListener(this)
        backbutton.setOnClickListener{
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }
    }

    private fun getCategories(): ArrayList<String> {
        firestore.collection("kategori")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    coffee?.let {
                        if (name == it.kategori) {
                            roleSelection = selection
                        }
                    }
                    roleSpinnerArray.add(name)
                    selection++
                }
                setCategories(roleSpinnerArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Categories cannot be retrieved ", Toast.LENGTH_SHORT).show()
            }
        return roleSpinnerArray
    }

    private fun setCategories(roleSpinnerArray: ArrayList<String>) {
        var spinnerAdapter= ArrayAdapter(this, android.R.layout.simple_list_item_1,roleSpinnerArray)
        edt_categories.adapter=spinnerAdapter
        edt_categories.setSelection(roleSelection)
        edt_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                roleName = edt_categories.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_simpan) {
            val name = edt_title.text.toString().trim()
            val password = edt_password.text.toString().trim()
            if (name.isEmpty()) {
                edt_title.error = "Data tidak boleh kosong"
                return
            }
            if (isEdit) {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "name" to name,
                    "password" to password,
                    "role" to roleName,
                )
                firestore.collection("admin").document(coffee?.id.toString())
                    .set(user)
                    .addOnSuccessListener {
                        setResult(RESULT_UPDATE, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()}
            } else {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "name" to name,
                    "password" to password,
                    "role" to roleName,
                )
                firestore.collection("admin").document(coffee?.id.toString())
                    .set(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this,
                            "Berhasil menambahkan data",
                            Toast.LENGTH_SHORT).show()
                        setResult(RESULT_ADD, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()}
            }
        }
    }
}


