package com.ppbl2021_rpl_kaizen.kopiku

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ScreenLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        val buttonback = findViewById<ImageButton>(R.id.backbutton)
        buttonback.setOnClickListener{
            startActivity(Intent(this, LandingScreen::class.java))
            finish()
        }

        val login = findViewById<Button>(R.id.login)
        login.setOnClickListener {
            val inputEmail = findViewById<TextInputEditText>(R.id.inputUsrname)
            val email = inputEmail.text.toString()
            val inputPassword = findViewById<TextInputEditText>(R.id.inputPassword)
            val password = inputPassword.text.toString()
            if (email.isEmpty()|| password.isEmpty()) {
                Toast.makeText(this, "Tolong Masukan Email dan Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email != "admin01@gmail.com"|| password != "admin01") {
                Toast.makeText(this, "Email dan Password Salah", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(email == "admin01@gmail.com" || password == "admin01"){
                val progressDialog = ProgressDialog(this,
                        R.style.Theme_MaterialComponents_Light_Dialog)
                progressDialog.isIndeterminate = true
                progressDialog.setMessage("Loading...")
                progressDialog.show()
                val intent = Intent (this,Dashboard::class.java)
                startActivity(intent)
                finish()
            }
        }
        }
    }

