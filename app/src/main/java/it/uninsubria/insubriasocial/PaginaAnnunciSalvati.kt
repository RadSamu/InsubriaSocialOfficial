package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PaginaAnnunciSalvati : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_annunci_salvati)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentUser = intent.getStringExtra("currentUser")
        val listView = findViewById<ListView>(R.id.listViewAnnunciSalvati)
        var annuncio = ""
        val annunciSalvati = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, annunciSalvati)
        listView.adapter = adapter

        // query di caricamento degli annunci
        val loadQuery: Query =
            db.collection("InsubriaSocial_Annunci")
                .whereArrayContains("salvato_da", currentUser!!)
        loadQuery.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val data = document.getString("data")
                    val titolo = document.getString("titolo")
                    annuncio = annuncio + "\n$data"
                    annuncio = annuncio + "\n"
                    annuncio = annuncio + "\n$titolo"
                    annunciSalvati.add(annuncio)
                    annuncio = ""
                }
            }
            adapter.notifyDataSetChanged()

            // click dell'annuncio
            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                val visualizza = Intent(this, PaginaMostraAnnuncio::class.java)
                    .putExtra("selectedItem", selectedItem)
                    .putExtra("currentUser", currentUser)
                startActivity(visualizza)

            }

            findViewById<Button>(R.id.btnIndietro4).setOnClickListener{
             val intent = Intent(this, PaginaApplicazioneProfilo::class.java)
                 .putExtra("currentUser", currentUser)
                startActivity(intent)
            }
        }
    }
}