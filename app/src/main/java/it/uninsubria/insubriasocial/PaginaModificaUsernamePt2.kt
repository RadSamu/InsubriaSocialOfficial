package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
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

class PaginaModificaUsernamePt2 : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val Utenti = db.collection("InsubriaSocial_Utenti")
    private lateinit var nuovaUsername: EditText
    private lateinit var confermaNuovoUsername: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_modifica_username_pt2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        nuovaUsername = findViewById<EditText>(R.id.editTextEditUser)
        confermaNuovoUsername = findViewById<EditText>(R.id.editTextConfEditUser)

        findViewById<Button>(R.id.btnEditUser).setOnClickListener {

            val recupera = Intent(this, PaginaAccesso::class.java)
            val address = intent.getStringExtra("eMail")
            val user = nuovaUsername.text.toString().trim()
            val confUser = confermaNuovoUsername.text.toString().trim()


            // Query per cercare l'utente
            val srcQuery: Query =
                db.collection("InsubriaSocial_Utenti")
                    .whereEqualTo("eMail", address)
            srcQuery.get().addOnCompleteListener{task ->
                if(task.isSuccessful){
                    for(document in task.result) {
                        val userMail = document.getString("eMail")
                        if(userMail == address && (user == confUser) && (user != document.getString("username"))){
                            //db.collection("InsubriaSocial_Utenti").document().update("password", psw)
                            val update: MutableMap<String, Any> = HashMap()
                            update["username"] = user
                            Utenti.document(document.id).set(update, SetOptions.merge())
                            startActivity(recupera)
                            Toast.makeText(
                                this,
                                "Username modificato con successo!",
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