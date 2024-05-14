package it.uninsubria.insubriasocial

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

class PaginaProfiloUtente : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_profilo_utente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val currentUser = intent.getStringExtra("currentUser")
        val selectedItem = intent.getStringExtra("selectedItem")
        val caricaBio: Query =
            db.collection("InsubriaSocial_Utenti")
                .whereEqualTo("username", selectedItem)
        caricaBio.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val bio = document.getString("biografia")
                    findViewById<TextView>(R.id.textViewUserBio).setText(bio)
                }
            }
        }


        findViewById<TextView>(R.id.textViewNomeProfilo).setText(selectedItem)

        findViewById<Button>(R.id.btnInviaMessaggio).setOnClickListener{
            val tornaIndietro = Intent(this, PaginaApplicazioneCerca::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(tornaIndietro)
        }

        findViewById<Button>(R.id.btnApriChat).setOnClickListener {
            val apriChat = Intent(this, PaginaChat::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("selectedItem", selectedItem)
            startActivity(apriChat)
        }
    }
}