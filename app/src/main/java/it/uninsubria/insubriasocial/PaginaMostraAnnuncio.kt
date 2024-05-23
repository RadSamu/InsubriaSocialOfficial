package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FieldValue
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
        //query per recuperare annuncio
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
// aggiunta ai preferiti creando un campo di un documento della raccolta se non esiste, se esiste aggiunge solamente il currentUser
        findViewById<Button>(R.id.btnAggiuntiPreferiti).setOnClickListener {
            val addQuery: Query =
                db.collection("InsubriaSocial_Annunci")
                    .whereEqualTo("titolo", titolo)
            addQuery.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        val docId = document.id
                        val soggetto = db.collection("InsubriaSocial_Annunci").document(docId)
                        if(document.contains("salvato_da")){
                            soggetto.update("salvato_da", FieldValue.arrayUnion(currentUser))
                        }else{
                            val updates = hashMapOf<String, Any>(
                                "salvato_da" to arrayListOf(currentUser)
                            )
                            soggetto.update(updates)
                                .addOnSuccessListener { Log.d(ContentValues.TAG, "Campo aggiunto con successo") }
                                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Errore durante l'aggiunta del campo", e) }
                        }
                        Toast.makeText(
                            this,
                            "Annuncio aggiunto ai preferiti!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
// rimuovere dai preferiti
        findViewById<Button>(R.id.btnTogliPreferiti).setOnClickListener {
            val removeQuery: Query =
                db.collection("InsubriaSocial_Annunci")
                    .whereEqualTo("titolo", titolo)
            removeQuery.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        val documentId = document.id
                        val soggetto = db.collection("InsubriaSocial_Annunci").document(documentId)
                        val updates = hashMapOf<String, Any>(
                            "salvato_da" to FieldValue.arrayRemove(currentUser)
                        )
                        soggetto.update(updates)
                            .addOnSuccessListener { Log.d(ContentValues.TAG, "Campo eliminato con successo") }
                            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Errore durante l'eliminazione del campo", e) }
                        Toast.makeText(
                            this,
                            "Annuncio rimosso dai preferiti!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
// visualizzazione dell'annuncio sulla mappa
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