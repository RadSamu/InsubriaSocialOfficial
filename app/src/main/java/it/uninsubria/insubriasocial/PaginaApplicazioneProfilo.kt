package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaApplicazioneProfilo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applicazione_profilo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnHome5).setOnClickListener {
            val pgHome = Intent(this, PaginaApplicazioneHome::class.java)
            startActivity(pgHome)
        }

        findViewById<Button>(R.id.btnSearch5).setOnClickListener {
            val pgCerca = Intent(this, PaginaApplicazioneCerca::class.java)
            startActivity(pgCerca)
        }

        findViewById<Button>(R.id.btnMap5).setOnClickListener {
            val pgMappa = Intent(this, PaginaApplicazioneMappa::class.java)
            startActivity(pgMappa)
        }

        findViewById<Button>(R.id.btnDashboard5).setOnClickListener {
            val pgBacheca = Intent(this, PaginaApplicazioneBacheca::class.java)
            startActivity(pgBacheca)
        }
    }
}