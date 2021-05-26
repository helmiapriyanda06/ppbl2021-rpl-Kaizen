package com.ppbl2021_rpl_kaizen.kopiku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ppbl2021_rpl_kaizen.kopiku.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvNoMeja : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvNoMeja = findViewById(R.id.user_name)

        if(intent.extras != null)
        {
            tvNoMeja.setText(intent.getStringExtra("data1"))
        }
    }
}