package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
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
        val filtro = intent.getBooleanExtra("filtro", false)
        val corsoSelezionato = intent.getStringExtra("corsoSelezionato")
        val listView: ListView = findViewById(R.id.simpleListViewCerca)
        var user = ""
        val barraRicerca = findViewById<SearchView>(R.id.SearchView)
        val profili = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, profili)
        listView.adapter = adapter
        //val corsiDiLaurea = intent.getStringExtra("corsiDiLaurea")
        val currentUser = intent.getStringExtra("currentUser")
        val espandiProfilo = Intent(this, PaginaProfiloUtente::class.java)
            .putExtra("currentUser", currentUser)
        val proprioProfilo = Intent(this, PaginaApplicazioneProfilo::class.java)
            .putExtra("currentUser", currentUser)

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
                            if(filtro == false){
                                val trovato = document.getString("username")
                                user = user + "$trovato"
                                profili.add(user)
                                user = ""
                            }else{
                                if(document.getString("corso di laurea") == corsoSelezionato){
                                    val trovato = document.getString("username")
                                    user = user + "$trovato"
                                    profili.add(user)
                                    user = ""
                                }
                            }


                        }
                    }
                    adapter.notifyDataSetChanged()
                    if(newText == ""){
                        adapter.clear()
                        adapter.notifyDataSetChanged()
                    }
                    listView.setOnItemClickListener { parent, view, position, id ->
                        val selectedItem = parent.getItemAtPosition(position).toString()
                        if(selectedItem == currentUser){
                            startActivity(proprioProfilo)
                        }else{
                            espandiProfilo.putExtra("selectedItem",selectedItem)
                            startActivity(espandiProfilo)
                        }

                    }

                }
                return true
            }
        })

        findViewById<Button>(R.id.btnFiltro).setOnClickListener{
            val corsiDiLaurea = intent.getStringArrayListExtra("corsiDiLaurea")
            val currentUser = intent.getStringExtra("currentUser")
            val apriFiltri = Intent(this, PaginaApplicaFiltro::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("corsiDiLaurea", corsiDiLaurea)
            startActivity(apriFiltri)
        }

        btmNav = findViewById(R.id.navBar)
        btmNav.setOnItemSelectedListener {
            item -> when(item.itemId) {
                R.id.home -> {
                    val currentUser = intent.getStringExtra("currentUser")
                    val intentHome = Intent(this, PaginaApplicazioneHome::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentHome)
                    true
                }
                R.id.search -> {
                    val currentUser = intent.getStringExtra("currentUser")
                    val intentSearch = Intent(this, PaginaApplicazioneCerca::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentSearch)
                    true
                }
                R.id.dashboard -> {
                    val currentUser = intent.getStringExtra("currentUser")
                    val intentDashboard = Intent(this, PaginaApplicazioneBacheca::class.java)
                        .putExtra("currentUser", currentUser)
                    startActivity(intentDashboard)
                    true
                }
                R.id.profile -> {
                    val currentUser = intent.getStringExtra("currentUser")
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
