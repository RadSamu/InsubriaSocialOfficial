package it.uninsubria.insubriasocial

import android.R.layout.simple_list_item_1
import android.R.layout.simple_list_item_2
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PaginaApplicazioneBacheca : AppCompatActivity() {
    private lateinit var btmNav: BottomNavigationView
    val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applicazione_bacheca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentUser = intent.getStringExtra("currentUser")

        var annuncio = ""
        val listView: ListView = findViewById(R.id.simpleListViewBacheca)
        val annunci = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, annunci)
        listView.adapter = adapter

        // query
        val queryRefresh: Query =
            db.collection("InsubriaSocial_Annunci")
                .orderBy("data", Query.Direction.DESCENDING)
        queryRefresh.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val data = document.getString("data")
                    val titolo = document.getString("titolo")
                    val descrizione = document.getString("descrizione")
                    val autore = document.getString("autore")
                    if(autore == currentUser){
                        annuncio = annuncio + "\n$data"
                        annuncio = annuncio + "\n"
                        annuncio = annuncio + "\n$titolo:"
                        annuncio = annuncio + "\n$descrizione"
                        annuncio = annuncio + "\n"
                        annuncio = annuncio + "\n-$autore"
                        annunci.add(annuncio)
                        annuncio = ""
                    }

                }

            }


            adapter.notifyDataSetChanged()

            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
            }


            btmNav = findViewById(R.id.navBar)
            btmNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
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


            findViewById<Button>(R.id.btnAdd).setOnClickListener {
                val pgAggiungiBacheca = Intent(this, PaginaAggiungiBacheca::class.java)
                    .putExtra("currentUser", currentUser)
                startActivity(pgAggiungiBacheca)
            }

        }
    }
}