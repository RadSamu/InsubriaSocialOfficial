package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaProfiloUtente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_profilo_utente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectedItem = intent.getStringExtra("selectedItem")
        findViewById<TextView>(R.id.textViewNomeProfilo).setText(selectedItem)

        findViewById<Button>(R.id.btnIndietro).setOnClickListener{
            val currentUser = intent.getStringExtra("currentUser")
            val tornaIndietro = Intent(this, PaginaApplicazioneCerca::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(tornaIndietro)
        }
    }
}