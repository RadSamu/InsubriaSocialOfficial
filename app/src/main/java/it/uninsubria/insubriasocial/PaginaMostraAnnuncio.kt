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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PaginaMostraAnnuncio : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_mostra_annuncio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = FirebaseFirestore.getInstance()
        val currentUser = intent.getStringExtra("currentUser")
        val selectedItem = intent.getStringExtra("selectedItem")
        val sottostringa = selectedItem!!.split("\n")
        val titolo = sottostringa[3].toString()
        var annuncio = ""
        var luogo = ""
        //query
        val loadQuery: Query =
            db.collection("InsubriaSocial_Annunci")
                .whereEqualTo("titolo", titolo)
        loadQuery.get().addOnCompleteListener {task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val autore = document.getString("autore")
                    val data = document.getString("data")
                    val titolo = document.getString("titolo")
                    val descrizione = document.getString("descrizione")
                    val posizione = document.getString("posizione")
                    annuncio = annuncio + "$data"
                    annuncio = annuncio + "\n"
                    annuncio = annuncio + "\n$titolo"
                    annuncio = annuncio + "\n"
                    annuncio = annuncio + "\n$descrizione"
                    annuncio = annuncio + "\n"
                    annuncio = annuncio + "\n-$autore"
                    findViewById<TextView>(R.id.textViewMostraAnnuncio).setText(annuncio)
                    luogo = luogo + "$posizione"
                }
            }
        }

        findViewById<Button>(R.id.btnApriMappa).setOnClickListener {
            val apriMappa = Intent(this, PaginaMostraMappa::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("posizione", luogo)
                .putExtra("annuncio", annuncio)
            startActivity(apriMappa)
        }
        findViewById<Button>(R.id.btnIndietro3).setOnClickListener {
            val intent = Intent(this, PaginaApplicazioneHome::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(intent)
        }
    }
}