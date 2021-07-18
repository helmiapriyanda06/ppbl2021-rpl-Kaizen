package com.kaizen_team.kopiku.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.kaizen_team.kopiku.LandingScreenActivity
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.ui.admin.Dashboard

class ScreenLogin : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_login)

        auth = FirebaseAuth.getInstance()

        val buttonback = findViewById<ImageButton>(R.id.backbutton)
        buttonback.setOnClickListener{
            startActivity(Intent(this, LandingScreenActivity::class.java))
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
            startActivity(Intent(this, Dashboard::class.java))
        })

        viewModel.messageListener().observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}