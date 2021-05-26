package com.ppbl2021_rpl_kaizen.kopiku.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.ViewModel.LoginViewModel

class ScreenLogin : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        auth = FirebaseAuth.getInstance()

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
            } else {
                viewModel.login(email,password)
            }
        }
        viewModel.successListener().observe(this,  {
            startActivity(Intent(this, MainActivity::class.java))
        })

        viewModel.messageListener().observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        }
    }

