package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaApplicaFiltro : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applica_filtro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val currentUser = intent.getStringExtra("currentUser")
        var filtro = false
        val corsiDiLaurea = arrayListOf("Economia", "Giurisprudenza", "Informatica", "Medicina")
        val corsoDiLaurea = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, corsiDiLaurea)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        corsoDiLaurea.adapter = adapter


        findViewById<Button>(R.id.btnInviaMessaggio).setOnClickListener{
            val tornaIndietro = Intent(this, PaginaApplicazioneCerca::class.java)
                .putExtra("filtro", filtro)
                .putExtra("currentUser", currentUser)
            startActivity(tornaIndietro)
        }

        findViewById<Button>(R.id.btnApplica).setOnClickListener {
            val corsoSelezionato = findViewById<Spinner>(R.id.spinner).selectedItem.toString()
            filtro = true
            val applicaFiltro = Intent(this, PaginaApplicazioneCerca::class.java)
                .putExtra("corsoSelezionato", corsoSelezionato)
                .putExtra("currentUser", currentUser)
                .putExtra("filtro", filtro)
            startActivity(applicaFiltro)
        }

        findViewById<Button>(R.id.btnCancella).setOnClickListener {
            val cancellaFiltro = Intent(this, PaginaApplicazioneCerca::class.java)
                .putExtra("filtro", filtro)
                .putExtra("currentUser", currentUser)
            startActivity(cancellaFiltro)
        }
    }
}