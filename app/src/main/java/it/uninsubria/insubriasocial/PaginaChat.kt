package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime

class PaginaChat : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = FirebaseFirestore.getInstance()

        val currentUser = intent.getStringExtra("currentUser").toString()
        val utente2 = intent.getStringExtra("selectedItem")
        findViewById<TextView>(R.id.textViewNomeUtenteChat).text = utente2

        val listView = findViewById<ListView>(R.id.listViewChat)
        val messaggi = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messaggi)
        listView.adapter = adapter
        // query che cerca se nella raccolta compaiono gli username degli utenti della chat in qualsiasi ordine (grazie all'uso della listOf di listOf's)
        val queryRefresh: Query =
           db.collection("InsubriaSocial_Chat")
               .whereIn("utenti", listOf(
                   listOf(currentUser, utente2),
                   listOf(utente2, currentUser)
               ))
        queryRefresh.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val docId = document.id
                    val docRef = db.collection("InsubriaSocial_Chat").document(docId).collection("InsubriaSocial_Messaggi")
                    val subQuery: Query =
                        docRef.orderBy("timestamp", Query.Direction.ASCENDING)
                    subQuery.get().addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            for(document in task.result){
                                val sender = document.getString("sender")
                                val testo = document.getString("testo")
                                var text = ""
                                text = text + "$testo"
                                text = text + "\n -$sender"
                                messaggi.add(text)
                                text = ""
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
            }

            findViewById<Button>(R.id.btnInviaMessaggio).setOnClickListener{
                var stringa = findViewById<EditText>(R.id.editTextScriviMessaggio).text.toString()
                val timestamp = LocalDateTime.now().toString()
// query che crea la sottocollezione Messaggi se già non esiste e vi salva il messaggio
                val writeQuery: Query =
                    db.collection("InsubriaSocial_Chat")
                        .orderBy("timestamp", Query.Direction.ASCENDING)
                queryRefresh.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                            for (document in task.result) {
                                val docId = document.id
                                val docRef = db.collection("InsubriaSocial_Chat").document(docId).collection("InsubriaSocial_Messaggi")
                                docRef.get().addOnCompleteListener { subcollectionTask ->
                                    if (!subcollectionTask.isSuccessful || subcollectionTask.result.isEmpty) {
                                        db.collection("InsubriaSocial_Chat").document(docId)
                                            .collection("InsubriaSocial_Messaggi")
                                            .add(hashMapOf<String, Any>())
                                    }
                                }

                                val subQuery: Query =
                                    docRef
                                subQuery.get().addOnCompleteListener {task ->
                                    if(task.isSuccessful){

                                        val update = hashMapOf<String, Any>(
                                            "receiver" to findViewById<TextView>(R.id.textViewNomeUtenteChat).text.toString(),
                                            "sender" to currentUser.toString(),
                                            "testo" to stringa,
                                            "timestamp" to timestamp
                                        )
                                        stringa = stringa + "\n -$currentUser"
                                        docRef.add(update)
                                        messaggi.add(stringa)

                                    }
                                    findViewById<EditText>(R.id.editTextScriviMessaggio).text.clear()
                                    adapter.notifyDataSetChanged()
                                }
                            }
                    }
                }
            }
            // tornare indietro
            findViewById<Button>(R.id.btnIndietro3).setOnClickListener {
                val tornaIndietro = Intent(this, PaginaElencoChat::class.java)
                    .putExtra("currentUser", currentUser)
                startActivity(tornaIndietro)
            }
        }
    }
}