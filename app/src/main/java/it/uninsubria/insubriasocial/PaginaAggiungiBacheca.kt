package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class PaginaAggiungiBacheca : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_aggiungi_bacheca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            findViewById<Button>(R.id.btnIndietro).setOnClickListener{
                val tornaIndietro = Intent(this, PaginaApplicazioneBacheca::class.java)
                startActivity(tornaIndietro)
            }

        findViewById<Button>(R.id.btnInvia).setOnClickListener{
            val pubblicaAnnuncio = Intent(this, PaginaApplicazioneHome::class.java)
            startActivity(pubblicaAnnuncio)
        }

    }

}

