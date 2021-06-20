package com.ppbl2021_rpl_kaizen.kopiku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ppbl2021_rpl_kaizen.kopiku.Model.Coffee
import com.ppbl2021_rpl_kaizen.kopiku.R

class CreateCoffeeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtNamaBarang: EditText
    private lateinit var edtHarga: EditText
    private lateinit var edtDeskripsi: EditText
    private lateinit var btnSave: Button
    private  lateinit var  ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_coffee)
        ref = FirebaseDatabase.getInstance().getReference("Coffee")
        edtNamaBarang = findViewById(R.id.edt_namabarang)
        edtHarga = findViewById(R.id.edt_harga)
        edtDeskripsi = findViewById(R.id.edt_deskripsi)
        btnSave = findViewById(R.id.btn_simpan)

        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        saveData()
    }

    fun saveData() {
        val namaBarang = edtNamaBarang.text.toString().trim()
        val hargaBarang = edtHarga.text.toString().trim()
        val deskripsi = edtDeskripsi.text.toString().trim()
        if (namaBarang.isEmpty()){
            edtNamaBarang.error="isi nama barang"
            return
        }
        if (hargaBarang.isEmpty()){
            edtHarga.error="isi harga barang"
            return
        }
        if (deskripsi.isEmpty()){
            edtDeskripsi.error="isi deskripsi"
            return
        }

        val coffeeId = ref.push().key
        val kopi = Coffee(coffeeId,namaBarang,hargaBarang,deskripsi)
        if (coffeeId != null){
            ref.child(coffeeId).setValue(kopi).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil di tambah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}