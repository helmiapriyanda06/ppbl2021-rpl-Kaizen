package com.ppbl2021_rpl_kaizen.kopiku.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ppbl2021_rpl_kaizen.kopiku.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        fab_add.setOnClickListener({
            startActivity(Intent(this, AddUpdateAdminActivity::class.java))
            }
        )
    }
}