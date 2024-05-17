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

class PaginaElencoChat : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_elenco_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = FirebaseFirestore.getInstance()
        val currentUser = intent.getStringExtra("currentUser").toString()
        val listView = findViewById<ListView>(R.id.listViewChat)
        val chat = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chat)
        listView.adapter = adapter

        val queryLoad: Query =
            db.collection("InsubriaSocial_Chat")
                .whereArrayContains("utenti", currentUser)
        queryLoad.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val utenti = document.get("utenti") as List<String>
                    for(string in utenti){
                        if(string == currentUser){

                        }else{
                            chat.add(string)
                        }
                    }

                }
            }
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val portaAllaChat = Intent(this, PaginaChat::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("selectedItem", selectedItem)
            startActivity(portaAllaChat)
        }

        findViewById<Button>(R.id.btnIndietroAllaHome).setOnClickListener {
            val currentUser = intent.getStringExtra("currentUser")
            val tornaAllaHome = Intent(this, PaginaApplicazioneHome::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(tornaAllaHome)
        }

    }
}