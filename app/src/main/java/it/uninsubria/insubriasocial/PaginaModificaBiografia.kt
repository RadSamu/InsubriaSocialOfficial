package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PaginaModificaBiografia : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_modifica_biografia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val currentUser = intent.getStringExtra("currentUser")
        val tornaIndietro = Intent(this, PaginaApplicazioneProfilo::class.java)
            .putExtra("currentUser", currentUser)
        findViewById<Button>(R.id.btnInviaMessaggio).setOnClickListener {
            startActivity(tornaIndietro)
        }


        findViewById<Button>(R.id.btnSalvaBio).setOnClickListener{
            val biografia = findViewById<EditText>(R.id.editTextModificaBio).text.toString()
            val queryBio: Query =
            db.collection("InsubriaSocial_Utenti")
                .whereEqualTo("username", currentUser)
            queryBio.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val docId = document.id
                        val soggetto = db.collection("InsubriaSocial_Utenti").document(docId)
                        val updates = hashMapOf<String, Any>(
                            "biografia" to biografia

                        )
                        soggetto.update(updates)
                            .addOnSuccessListener { Log.d(TAG, "Campo aggiunto con successo") }
                            .addOnFailureListener { e -> Log.w(TAG, "Errore durante l'aggiunta del campo", e) }
                        Toast.makeText(
                            this,
                            "Biografia modificata con successo!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    startActivity(tornaIndietro)
                }

            }
        }

        findViewById<Button>(R.id.btnEliminaBio).setOnClickListener {
            val deleteBio: Query =
                db.collection("InsubriaSocial_Utenti")
                    .whereEqualTo("currentUser", currentUser)
            deleteBio.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        val documentId = document.id
                        val soggetto = db.collection("InsubriaSocial_Utenti").document(documentId)
                        val updates = hashMapOf<String, Any>(
                            "biografia" to FieldValue.delete()
                        )
                        soggetto.update(updates)
                            .addOnSuccessListener { Log.d(TAG, "Campo eliminato con successo") }
                            .addOnFailureListener { e -> Log.w(TAG, "Errore durante l'eliminazione del campo", e) }
                        Toast.makeText(
                            this,
                            "Biografia eliminata con successo!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    startActivity(tornaIndietro)
                }
            }
        }

}
}