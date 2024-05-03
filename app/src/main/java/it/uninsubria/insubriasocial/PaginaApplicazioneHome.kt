package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class PaginaApplicazioneHome : AppCompatActivity() {
    private lateinit var btmNav: BottomNavigationView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applicazione_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btmNav = findViewById(R.id.navBar)
        btmNav.setOnItemSelectedListener {
            item -> when(item.itemId) {
                R.id.home -> {
                    val intentHome = Intent(this, PaginaApplicazioneHome::class.java)
                    startActivity(intentHome)
                    true
                }
                R.id.search -> {
                    val intentSearch = Intent(this, PaginaApplicazioneCerca::class.java)
                    startActivity(intentSearch)
                    true
                }
                R.id.dashboard -> {
                    val intentDashboard = Intent(this, PaginaApplicazioneBacheca::class.java)
                    startActivity(intentDashboard)
                    true
                }
                R.id.profile -> {
                    val intentProfile = Intent(this, PaginaApplicazioneProfilo::class.java)
                    startActivity(intentProfile)
                    true
                }
                else -> false
            }
        }
    }
}



