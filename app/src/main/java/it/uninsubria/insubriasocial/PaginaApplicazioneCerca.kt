package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.contains
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.grpc.Context

class PaginaApplicazioneCerca : AppCompatActivity() {
    private lateinit var btmNav: BottomNavigationView
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_applicazione_cerca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView: ListView = findViewById(R.id.simpleListViewCerca)
        var user = ""
        val barraRicerca = findViewById<SearchView>(R.id.SearchView)
        val profili = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, profili)
        listView.adapter = adapter


        barraRicerca.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val queryCerca: Query =
                    db.collection("InsubriaSocial_Utenti")
                        .whereEqualTo("username", newText)
                queryCerca.get().addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        for(document in task.result){
                            val trovato = document.getString("username")
                            user = user + "$trovato"
                            profili.add(user)
                            user = ""
                        }
                    }
                    adapter.notifyDataSetChanged()
                    if(newText == ""){
                        adapter.clear()
                        adapter.notifyDataSetChanged()
                    }
                    listView.setOnItemClickListener { parent, view, position, id ->
                        val selectedItem = parent.getItemAtPosition(position).toString()
                    }

                }
                return true
            }
        })



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
