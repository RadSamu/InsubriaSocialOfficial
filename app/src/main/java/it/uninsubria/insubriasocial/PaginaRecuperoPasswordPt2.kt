package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

class PaginaRecuperoPasswordPt2 : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val Utenti = db.collection("InsubriaSocial_Utenti")
    private lateinit var nuovaPassword: EditText
    private lateinit var confermaNuovaPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_recupero_password_pt2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        nuovaPassword = findViewById<EditText>(R.id.editTextRecPsw)
        confermaNuovaPassword = findViewById<EditText>(R.id.editTextConfRecPsw)

        findViewById<Button>(R.id.btnRecuperaPsw).setOnClickListener {

            val recupera = Intent(this, PaginaAccesso::class.java)
            val address = intent.getStringExtra("eMail")
            val psw = nuovaPassword.text.toString().trim()
            val confPsw = confermaNuovaPassword.text.toString().trim()


            // query per cercare l'utente
            val srcQuery: Query =
                db.collection("InsubriaSocial_Utenti")
                    .whereEqualTo("eMail", address)
            srcQuery.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result) {
                        val userMail = document.getString("eMail")
                        if(userMail == address && (psw == confPsw) && (psw != document.getString("password"))){
                            //db.collection("InsubriaSocial_Utenti").document().update("password", psw)
                            val update: MutableMap<String, Any> = HashMap()
                            update["password"] = psw
                            Utenti.document(document.id).set(update, SetOptions.merge())
                            startActivity(recupera)
                            Toast.makeText(
                                this,
                                "Password reimpostata con successo!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else{
                            Toast.makeText(
                                this,
                                "Errore, controlla i campi e riprova!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                }
            }



        }
    }
}