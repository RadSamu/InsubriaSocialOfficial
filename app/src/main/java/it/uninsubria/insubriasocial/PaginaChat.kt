package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class PaginaChat : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val selectedItem = intent.getStringExtra("selectedItem")
        findViewById<TextView>(R.id.textViewNomeUtenteChat).setText(selectedItem)

        findViewById<Button>(R.id.btnIndietro2).setOnClickListener {
            val currentUser = intent.getStringExtra("currentUser")
            val tornaIndietro = Intent(this, PaginaProfiloUtente::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("selectedItem", selectedItem)
            startActivity(tornaIndietro)
        }
    }
}