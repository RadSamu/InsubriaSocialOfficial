package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PaginaAccesso : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val Utenti = db.collection("InsubriaSocial_Utenti")
    //val collectionName = "InsubriaSocial_Utenti"
    lateinit var auth: FirebaseAuth

    private lateinit var logUsername: EditText
    private lateinit var logPassword: EditText


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pagina_accesso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logUsername = findViewById<EditText>(R.id.editTextLogUser)
        logPassword = findViewById<EditText>(R.id.editTextLogPsw)

        // Passare alla pagina di registrazione
        findViewById<TextView>(R.id.textViewVaiPgReg).setOnClickListener {
            val passReg = Intent(this, PaginaRegistrazione::class.java)
            startActivity(passReg)
        }

        // Passare alla pagina di recupero password
        findViewById<TextView>(R.id.recuperoPasswordtxt).setOnClickListener {
            val passRecPsw = Intent(this, PaginaRecuperoPassword::class.java)
            startActivity(passRecPsw)
        }

        // Accedere all'applicazione
        findViewById<Button>(R.id.btnAccedi).setOnClickListener {
            val accesso = Intent(this, PaginaApplicazione::class.java)
            val username = logUsername.text.toString().trim()
            val password = logPassword.text.toString().trim()
            var userTrovato: Boolean = false
            var pswTrovato: Boolean = false

            // Controllo delle credenziali nel DB
                // Check user
            val myQuery: Query =
                db.collection("InsubriaSocial_Utenti")
                    .whereEqualTo("username", username)
            myQuery.get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        val logUser = document.getString("username")
                        if(logUser == username){
                            userTrovato = true
                        }
                    }

                }
            }

                // Check psw
            val myQuery2: Query =
                db.collection("InsubriaSocial_Utenti")
                    .whereEqualTo("password", password)
            myQuery.get().addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    for(document in task.result){
                        val logPassword = document.getString("password")
                        if(logPassword == password){
                            pswTrovato = true
                        }
                    }
                }

                // Check login permit
                if(userTrovato && pswTrovato){
                    startActivity(accesso)
                    Toast.makeText(
                        this,
                        "Accesso effettuato con successo!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }else{
                    Toast.makeText(
                        this,
                        "Errore, controlla i campi e riprova",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
                }



            }



    }






}

