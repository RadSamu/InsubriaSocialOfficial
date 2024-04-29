package it.uninsubria.insubriasocial

import android.content.Intent
import android.icu.text.IDNA.Info
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class PaginaRecuperoPassword : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val Utenti = db.collection("InsubriaSocial_Utenti")
    lateinit var auth: FirebaseAuth
    private lateinit var eMail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_recupero_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViewById<Button>(R.id.btnAvanti).setOnClickListener {
            eMail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
            val userEMail = eMail.text.toString().trim()
            val intent: Intent = Intent(this, PaginaRecuperoPasswordPt2::class.java).apply {
                putExtra("eMail", userEMail)
            }

            startActivity(intent)
            }

        }
    }
