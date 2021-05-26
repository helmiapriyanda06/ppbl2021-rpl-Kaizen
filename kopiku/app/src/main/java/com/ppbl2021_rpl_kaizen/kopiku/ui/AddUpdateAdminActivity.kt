package com.ppbl2021_rpl_kaizen.kopiku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.ppbl2021_rpl_kaizen.kopiku.Model.Admin
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_POSITION
import com.ppbl2021_rpl_kaizen.kopiku.helper.EXTRA_QUOTE
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_ADD
import com.ppbl2021_rpl_kaizen.kopiku.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_add_update_admin.*

class AddUpdateAdminActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var roleSpinnerArray = ArrayList<String>()
    private var admin: Admin? = null
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
        admin = intent.getParcelableExtra(EXTRA_QUOTE)
        if (admin != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            admin = Admin()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            admin?.let {
                edt_title.setText(it.name)
                edt_password.setText(it.password)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn_simpan.text = btnTitle
        btn_simpan.setOnClickListener(this)
        btn_simpan.setOnClickListener{

        }
    }

    private fun getCategories(): ArrayList<String> {
        firestore.collection("role")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    admin?.let {
                        if (name == it.role) {
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

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}


