package it.uninsubria.insubriasocial


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.View


class PaginaRegistrazione : AppCompatActivity() {
    private val firestore: Any
        get() {
            TODO()
        }
    private lateinit var nome: EditText
    private lateinit var cognome: EditText
    private lateinit var eMail: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confPassword: EditText
    private lateinit var corsoDiLaurea: Spinner
    val db = FirebaseFirestore.getInstance()
    val collectionName = "InsubriaSocial_Utenti"
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_registrazione)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nome = findViewById<EditText>(R.id.editTextNome)
        cognome =  findViewById<EditText>(R.id.editTextCognome)
        corsoDiLaurea = findViewById<Spinner>(R.id.spinner2)
        eMail = findViewById<EditText>(R.id.editTextEmail)
        username = findViewById<EditText>(R.id.editTextRegUser)
        password = findViewById<EditText>(R.id.editTextRegPsw)
        confPassword = findViewById<EditText>(R.id.editTextRegConfPsw)
        auth = Firebase.auth

        val corsiDiLaurea = arrayListOf("Economia", "Giurisprudenza", "Informatica", "Medicina")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, corsiDiLaurea)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        corsoDiLaurea.adapter = adapter

// setting del bottone
        findViewById<Button>(R.id.btnRegistrazione).setOnClickListener {
            val registra = Intent(this, PaginaAccesso::class.java)
                .putExtra("corsiDiLaurea", corsiDiLaurea)
            val nome = nome.text.toString().trim()
            val cognome = cognome.text.toString().trim()
            val eMail = eMail.text.toString().trim()
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()
            val confPassword = confPassword.text.toString().trim()
            val mailCheck = "@studenti.uninsubria.it"
            var corsoSelezionato = corsoDiLaurea.selectedItem.toString()

// Setting dell'hash map
            val userMap = hashMapOf(
                "nome" to nome,
                "cognome" to cognome,
                "corso di laurea" to corsoSelezionato,
                "eMail" to eMail,
                "username" to username,
                "password" to password
            )

// Check del contenuto delle EditText
            if(!nome.isEmpty() && !cognome.isEmpty() && (!eMail.isEmpty() && mailCheck in eMail) && !username.isEmpty() && (!password.isEmpty() && (confPassword == password)) && !(corsoSelezionato == "null")){

            db.collection("InsubriaSocial_Utenti")
                .add(userMap)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
                Toast.makeText(
                    this,
                    "Account creato con successo!",
                    Toast.LENGTH_SHORT,
                ).show()
                    startActivity(registra)}
            else{
                Toast.makeText(
                    this,
                    "Errore, controlla i campi e riprova",
                    Toast.LENGTH_SHORT,
                ).show()
            }


        }
    }
}


private fun Any.collection(): CollectionReference {
    TODO("Not yet implemented")
}




