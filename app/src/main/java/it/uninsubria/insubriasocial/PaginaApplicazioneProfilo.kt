package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class PaginaApplicazioneProfilo : AppCompatActivity() {
    private lateinit var btmNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applicazione_profilo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val currentUser = intent.getStringExtra("currentUser")
        findViewById<TextView>(R.id.textView10).setText(currentUser)
        btmNav = findViewById(R.id.navBar)
        btmNav.setOnItemSelectedListener {
            item -> when(item.itemId) {
                R.id.home -> {
                    val intentHome = Intent(this, PaginaApplicazioneHome::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentHome)
                    true
                }
                R.id.search -> {
                    val intentSearch = Intent(this, PaginaApplicazioneCerca::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentSearch)
                    true
                }
                R.id.dashboard -> {
                    val intentDashboard = Intent(this, PaginaApplicazioneBacheca::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentDashboard)
                    true
                }
                R.id.profile -> {
                    val intentProfile = Intent(this, PaginaApplicazioneProfilo::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentProfile)
                    true
                }
                else -> false
            }
        }
    }
}