package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PaginaApplicazioneProfilo : AppCompatActivity() {
    private lateinit var btmNav: BottomNavigationView
    val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
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

        // query per ritornare la bio dell'utente
        val bioQuery: Query =
            db.collection("InsubriaSocial_Utenti")
                .whereEqualTo("username", currentUser)
        bioQuery.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val biografia = document.getString("biografia")
                    findViewById<TextView>(R.id.textViewBio).setText(biografia)
                }

            }
        }

// setting e gestione del fragment
        val fragment = ModificaFragment()
        val bundle = Bundle()
        bundle.putString("currentUser", currentUser)
        fragment.arguments = bundle
        findViewById<Button>(R.id.button3).setOnClickListener {
               supportFragmentManager.beginTransaction()
                   .replace(R.id.fragment_container, fragment)
                   .addToBackStack(null)
                   .commit()
        }


        // creazione e setting activities
        val intentHome = Intent(this, PaginaApplicazioneHome::class.java)
            .putExtra("currentUser", currentUser)

        val intentSearch = Intent(this, PaginaApplicazioneCerca::class.java)
            .putExtra("currentUser", currentUser)

        val intentDashboard = Intent(this, PaginaApplicazioneBacheca::class.java)
            .putExtra("currentUser", currentUser)

        btmNav = findViewById(R.id.navBar)
        btmNav.setSelectedItemId(R.id.profile)
        btmNav.setOnItemSelectedListener {
            item -> when(item.itemId) {
                R.id.home -> {
                    startActivity(intentHome)
                    true
                }
                R.id.search -> {
                    startActivity(intentSearch)
                    true
                }
                R.id.dashboard -> {
                    startActivity(intentDashboard)
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }
}