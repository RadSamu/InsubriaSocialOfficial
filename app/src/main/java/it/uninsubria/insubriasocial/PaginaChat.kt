package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.w3c.dom.Text

class PaginaChat : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        val currentUser = intent.getStringExtra("currentUser")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewChat)
        val messaggiList : MutableList<Message> = mutableListOf()
        val adapter = ChatAdapter(messaggiList)
        recyclerView.adapter = adapter
        val query : Query =
            db.collection("InsubriaSocial_Chat")
                .whereEqualTo("username", currentUser)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val messaggio = document.getString("msg")
                    val msg = Message.obtain()
                    msg.obj = messaggio
                    messaggiList.add(msg)
                }
            }
            adapter.notifyDataSetChanged()
        }
        val selectedItem = intent.getStringExtra("selectedItem")
        findViewById<TextView>(R.id.textViewNomeUtenteChat).setText(selectedItem)

        findViewById<Button>(R.id.btnInviaMessaggio).setOnClickListener {
            val reciver = findViewById<TextView>(R.id.textViewNomeUtenteChat).text.toString()
            val testo = findViewById<EditText>(R.id.editTextScriviMessaggio).text.toString().trim()
            val queryAggiungi : Query =
                db.collection("InsubriaSocial_Chat")
                    .whereEqualTo("username", currentUser)
            query.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val docId = document.id
                        val utente2 = document.getString("reciver")
                        if (reciver == utente2) {
                            val tmp = db.collection("InsubriaSocial_Chat").document(docId)
                            val updates = hashMapOf<String, Any>(
                                "msg" to testo
                            )
                            tmp.update(updates)
                        }
                    }
                }
                findViewById<EditText>(R.id.editTextScriviMessaggio).setText("")
            }
        }

        findViewById<Button>(R.id.btnIndietro2).setOnClickListener {
            val tornaIndietro = Intent(this, PaginaProfiloUtente::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("selectedItem", selectedItem)
            startActivity(tornaIndietro)
        }

    }
}