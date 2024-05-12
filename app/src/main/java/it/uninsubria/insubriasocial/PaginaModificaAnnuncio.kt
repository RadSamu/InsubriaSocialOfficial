package it.uninsubria.insubriasocial

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

class PaginaModificaAnnuncio : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val Annunci = db.collection("InsubriaSocial_Annunci")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_modifica_annuncio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val data = intent.getStringExtra("data")
        val titolo = intent.getStringExtra("titolo")
        val descrizione = intent.getStringExtra("descrizione")
        findViewById<EditText>(R.id.editTextText).setText(data)
        findViewById<EditText>(R.id.editTextText2).setText(titolo)
        findViewById<EditText>(R.id.editTextText3).setText(descrizione)

        findViewById<Button>(R.id.btnSalva).setOnClickListener{
            val currentUser = intent.getStringExtra("currentUser")
            val salvaModifica = Intent(this, PaginaApplicazioneBacheca::class.java)
                .putExtra("currentUser", currentUser)
            val editQuery: Query =
                db.collection("InsubriaSocial_Annunci")
                    .whereEqualTo("descrizione", descrizione)
            editQuery.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result){

                        val idDoc = document.id
                        val modifica = db.collection("InsubriaSocial_Annunci").document(idDoc)
                        val campiAggiornati = hashMapOf(
                            "data" to findViewById<EditText>(R.id.editTextText).text.toString(),
                            "titolo" to findViewById<EditText>(R.id.editTextText2).text.toString(),
                            "descrizione" to findViewById<EditText>(R.id.editTextText3).text.toString()
                        )
                        modifica.update(campiAggiornati as Map<String, Any>)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Annuncio modificato con successo!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                startActivity(salvaModifica)
                            }
                    }

                }
            }
        }

        findViewById<Button>(R.id.btnElimina).setOnClickListener {
            val currentUser = intent.getStringExtra("currentUser")
            val eliminaAnnuncio = Intent(this, PaginaApplicazioneBacheca::class.java)
                .putExtra("currentUser", currentUser)
            val deleteQuery: Query =
                db.collection("InsubriaSocial_Annunci")
                    .whereEqualTo("descrizione", descrizione)
            deleteQuery.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        val documentId = document.id
                        db.collection("InsubriaSocial_Annunci").document(documentId).delete()
                        Toast.makeText(
                            this,
                            "Annuncio cancellato con successo!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                }
                startActivity(eliminaAnnuncio)

            }

        }


    }

}