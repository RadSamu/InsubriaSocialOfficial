package it.uninsubria.insubriasocial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaAccesso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pagina_accesso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //passare alla pagina di registrazione
        findViewById<TextView>(R.id.textViewVaiPgReg).setOnClickListener {
            val passReg = Intent(this, PaginaRegistrazione::class.java)
            startActivity(passReg)
        }

        //passare alla pagina di recupero password
        findViewById<TextView>(R.id.recuperoPasswordtxt).setOnClickListener {
            val passRecPsw = Intent(this, PaginaRecuperoPassword::class.java)
            startActivity(passRecPsw)
        }

        //accedere all'applicazione
        findViewById<Button>(R.id.btnAccedi).setOnClickListener {
            val accesso = Intent(this, PaginaApplicazione::class.java)
            startActivity(accesso)
        }
    }
}