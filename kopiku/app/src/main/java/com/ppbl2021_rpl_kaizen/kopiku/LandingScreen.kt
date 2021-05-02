package com.ppbl2021_rpl_kaizen.kopiku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LandingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_screen)
        val btn_pesan = findViewById<Button>(R.id.pesan_now)
        val btn_admin = findViewById<Button>(R.id.admin)
        btn_pesan.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ScreenQrscan::class.java))
            finish()
        })
        btn_admin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ScreenLogin::class.java))
            finish()
        })
    }
}