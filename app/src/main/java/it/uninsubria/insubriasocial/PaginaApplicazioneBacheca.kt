package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
            var data = ""
            var titolo = ""
            var descrizione = ""
            var autore = ""

            val listView: ListView = findViewById(R.id.simpleListViewCerca)
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
                        data = document.getString("data")!!
                        titolo = document.getString("titolo")!!
                        descrizione = document.getString("descrizione")!!
                        autore = document.getString("autore")!!
                        if(autore == currentUser){
                            annuncio = annuncio + "\n$data"
                            annuncio = annuncio + "\n"
                            annuncio = annuncio + "\n$titolo"
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
                    val sottostringhe = selectedItem.split("\n")
                    val data1 = sottostringhe[1].toString()
                    val titolo1 = sottostringhe[3].toString()
                    val descrizione1 = sottostringhe[4].toString()

                    val pgModificaAnnuncio = Intent(this, PaginaModificaAnnuncio::class.java)
                        .putExtra("data",data1)
                        .putExtra("titolo",titolo1)
                        .putExtra("descrizione",descrizione1)
                        .putExtra("currentUser", currentUser)
                    startActivity(pgModificaAnnuncio)
                }

                // creazione e setting activities
                val intentHome = Intent(this, PaginaApplicazioneHome::class.java)
                    .putExtra("currentUser", currentUser)

                val intentSearch = Intent(this, PaginaApplicazioneCerca::class.java)
                    .putExtra("currentUser", currentUser)

                val intentProfile = Intent(this, PaginaApplicazioneProfilo::class.java)
                    .putExtra("currentUser", currentUser)

            btmNav = findViewById(R.id.navBar)
            btmNav.setSelectedItemId(R.id.dashboard)
            btmNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        startActivity(intentHome)
                        true
                    }

                    R.id.search -> {
                        startActivity(intentSearch)
                        true
                    }

                    R.id.dashboard -> {
                        true
                    }

                    R.id.profile -> {
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