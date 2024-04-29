package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaApplicazioneCerca : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applicazione_cerca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnHome2).setOnClickListener {
            val pgHome = Intent(this, PaginaApplicazioneHome::class.java)
            startActivity(pgHome)
        }

        findViewById<Button>(R.id.btnMap2).setOnClickListener {
            val pgMappa = Intent(this, PaginaApplicazioneMappa::class.java)
            startActivity(pgMappa)
        }

        findViewById<Button>(R.id.btnDashboard2).setOnClickListener {
            val pgBacheca = Intent(this, PaginaApplicazioneBacheca::class.java)
            startActivity(pgBacheca)
        }

        findViewById<Button>(R.id.btnProfile2).setOnClickListener {
            val pgProfilo = Intent(this, PaginaApplicazioneProfilo::class.java)
            startActivity(pgProfilo)
        }

    }
}