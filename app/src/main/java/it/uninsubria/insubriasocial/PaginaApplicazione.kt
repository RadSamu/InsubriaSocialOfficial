package it.uninsubria.insubriasocial

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.uninsubria.insubriasocial.databinding.ActivityPaginaApplicazioneBinding

class PaginaApplicazione : AppCompatActivity() {
    lateinit var binding: ActivityPaginaApplicazioneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginaApplicazioneBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnHome.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        binding.btnSearch.setOnClickListener {
            replaceFragment(SearchFragment())
        }

        binding.btnMappa.setOnClickListener {
            replaceFragment(MapFragment())
        }

        binding.btnDashboard.setOnClickListener {
            replaceFragment(DashboardFragment())
        }

        binding.btnProfilo.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}