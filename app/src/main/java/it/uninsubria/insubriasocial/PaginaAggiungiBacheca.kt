package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.time.LocalDate

class PaginaAggiungiBacheca : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    val collectionName = "InsubriaSocial_Annunci"

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
        //val autore = auth.currentUser.toString()

            findViewById<Button>(R.id.btnIndietro).setOnClickListener{
                val tornaIndietro = Intent(this, PaginaApplicazioneBacheca::class.java)
                startActivity(tornaIndietro)
            }

        findViewById<Button>(R.id.btnInvia).setOnClickListener{
            val pubblicaAnnuncio = Intent(this, PaginaApplicazioneBacheca::class.java)
            val dataAnnuncio = findViewById<EditText>(R.id.editTextData)
            val titoloAnnuncio = findViewById<EditText>(R.id.editTextTitolo)
            val descrizioneAnnuncio = findViewById<EditText>(R.id.editTextDescrizione)


            val currentUser = intent.getStringExtra("currentUser")
            val titolo = titoloAnnuncio.text.toString().trim()
            val descrizione = descrizioneAnnuncio.text.toString().trim()
            val data = dataAnnuncio.text.toString().trim()


            val noticeMap = hashMapOf(
                "data" to data,
                "titolo" to titolo,
                "descrizione" to descrizione,
                "autore" to currentUser
            )

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

