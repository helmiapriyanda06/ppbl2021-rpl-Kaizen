package com.kaizen_team.kopiku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.kaizen_team.kopiku.ui.login.ScreenLogin

class LandingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)
        val btn_pesan = findViewById<Button>(R.id.pesan_now)
        val btn_admin = findViewById<Button>(R.id.admin)
        btn_pesan.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        })
        btn_admin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ScreenLogin::class.java))
            finish()
        })
    }
}