package com.kaizen_team.kopiku.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.model.Coffee
import com.kaizen_team.kopiku.ui.admin.helper.EXTRA_POSITION
import com.kaizen_team.kopiku.ui.admin.helper.EXTRA_QUOTE
import com.kaizen_team.kopiku.ui.admin.helper.RESULT_ADD
import com.kaizen_team.kopiku.ui.admin.helper.RESULT_DELETE
import com.kaizen_team.kopiku.ui.admin.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_add_update.*

class AddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var roleSpinnerArray = ArrayList<String>()
    private var coffee: Coffee? = null
    private var position: Int = 0
    private var catSelection: Int = 0
    private var catName: String = "0"
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update)

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
                firestore.collection("Coffee").document(coffee?.id.toString())
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
                edt_title.setText(it.nama_barang)
            }
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
                            catSelection = selection
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
        edt_categories.setSelection(catSelection)
        edt_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                catName = edt_categories.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_simpan) {
            val namaBarang = edt_title.text.toString().trim()
            val hargaBarang = edt_harga.text.toString().toInt()
            val deskripsi = edt_deskripsi.text.toString().trim()
            if (namaBarang.isEmpty()) {
                edt_title.error = "Data tidak boleh kosong"
                return
            }
            if (isEdit) {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "nama_barang" to namaBarang,
                    "harga_barang" to hargaBarang,
                    "deksripsi" to deskripsi,
                    "kategori" to catName
                )
                firestore.collection("Coffee").document(coffee?.id.toString())
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
                    "nama_barang" to namaBarang,
                    "harga_barang" to hargaBarang,
                    "deksripsi" to deskripsi,
                    "kategori" to catName
                )
                firestore.collection("Coffee")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        //Toast.makeText(this,"${documentReference.id}", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_ADD, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()}
            }
        }
    }
}