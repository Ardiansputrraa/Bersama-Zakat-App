package com.example.bersamazakatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bersamazakatapp.ui.zakat_emas.ZakatEmasFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}