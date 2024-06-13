package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class PaginaAggiungiBacheca : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_aggiungi_bacheca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


            findViewById<Button>(R.id.btnInviaMessaggio).setOnClickListener{
                val currentUser = intent.getStringExtra("currentUser")
                val tornaIndietro = Intent(this, PaginaApplicazioneBacheca::class.java)
                    .putExtra("currentUser", currentUser)
                startActivity(tornaIndietro)
            }
// spinner e adapter
        val posizione = findViewById<Spinner>(R.id.spinner3)
        val posizioni = arrayListOf("Pad. Seppilli", "Pad. Morselli", "Pad. Antonini", "Pad. Monte Generoso", "Mensa Monte Generoso")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, posizioni)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        posizione.adapter = adapter

        // aggiunta e pubblicazione di un nuovo annuncio
        findViewById<Button>(R.id.btnInvia).setOnClickListener{
            val currentUser = intent.getStringExtra("currentUser")
            val pubblicaAnnuncio = Intent(this, PaginaApplicazioneBacheca::class.java)
                .putExtra("currentUser", currentUser)
            val dataAnnuncio = findViewById<EditText>(R.id.editTextData)
            val titoloAnnuncio = findViewById<EditText>(R.id.editTextTitolo)
            val descrizioneAnnuncio = findViewById<EditText>(R.id.editTextDescrizione)



            val titolo = titoloAnnuncio.text.toString().trim()
            val descrizione = descrizioneAnnuncio.text.toString().trim()
            val data = dataAnnuncio.text.toString().trim()
            val posizioneSelezionata = posizione.selectedItem.toString()


            val noticeMap = hashMapOf(
                "data" to data,
                "titolo" to titolo,
                "descrizione" to descrizione,
                "autore" to currentUser,
                "posizione" to posizioneSelezionata
            )
// controllo sulle editText
            if(!data.isEmpty() && !titolo.isEmpty() && !descrizione.isEmpty()){
                db.collection("InsubriaSocial_Annunci")
                    .add(noticeMap)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
                Toast.makeText(
                    this,
                    "Annuncio pubblicato correttamente!",
                    Toast.LENGTH_SHORT,
                ).show()
                startActivity(pubblicaAnnuncio)
            }else{
                Toast.makeText(
                    this,
                    "Errore, controlla i campi e riprova",
                    Toast.LENGTH_SHORT,
                ).show()
            }

        }

    }

}

