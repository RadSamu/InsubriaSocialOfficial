package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime

class PaginaProfiloUtente : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_profilo_utente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
            val currentUser = intent.getStringExtra("currentUser")
            val tornaIndietro = Intent(this, PaginaApplicazioneCerca::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(tornaIndietro)
        }
// crea una chat con l'utente in questione se gia non esiste, altrimenti la apre
// controllo possibile grazie alla listOf di listOf's (riga 61-64)
        findViewById<Button>(R.id.btnApriChat).setOnClickListener {
            val currentUser = intent.getStringExtra("currentUser")
            val apriChat = Intent(this, PaginaChat::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("selectedItem", selectedItem)

            val chatRef = db.collection("InsubriaSocial_Chat")
            val query: Query =
                chatRef.whereIn("utenti", listOf(
                    listOf(currentUser, selectedItem),
                    listOf(selectedItem, currentUser)
                ))
            query.get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.isEmpty) {
                    val newChatRef = chatRef.add(hashMapOf(
                        "utenti" to arrayListOf(currentUser, selectedItem)
                    )).addOnSuccessListener {DocumentReference ->
                        val newChatId = DocumentReference.id
                        val messagesRef = DocumentReference.collection("InsubriaSocial_Messaggi")
                        val timestamp = LocalDateTime.now().toString()
                        val firstMessage = hashMapOf(
                            "sender" to currentUser,
                            "receiver" to selectedItem,
                            "testo" to "Ciao",
                            "timestamp" to timestamp
                        )
                        messagesRef.add(firstMessage)
                    }
                }
                startActivity(apriChat)
            }
        }
    }
}