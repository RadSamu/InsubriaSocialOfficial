package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PaginaModificaUsername : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val Utenti = db.collection("InsubriaSocial_Utenti")
    lateinit var auth: FirebaseAuth
    private lateinit var eMail: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_modifica_username)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        findViewById<Button>(R.id.btnAvantiU).setOnClickListener {
            eMail = findViewById<EditText>(R.id.editTextTextEmailAddressUser)
            val userEMail = eMail.text.toString().trim()
            val intent = Intent(this, PaginaModificaUsernamePt2::class.java).apply {
                putExtra("eMail", userEMail)
            }

            startActivity(intent)
        }

    }
}
