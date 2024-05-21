package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ModificaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModificaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser = arguments?.getString("currentUser")
        val view = inflater.inflate(R.layout.fragment_modifica, container, false)

        // Ottieni il riferimento alla TextView
        val closeTextView = view.findViewById<Button>(R.id.buttonClose)
        val passwordTextView = view.findViewById<TextView>(R.id.textViewPassword)
        passwordTextView.setOnClickListener{
            val intent = Intent(activity, PaginaRecuperoPassword::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(intent)

        }
        // Aggiungi un OnClickListener alla TextView
        closeTextView.setOnClickListener {

            // Chiudi il Fragment quando la TextView viene cliccata
            requireActivity().supportFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btnLogOut).setOnClickListener {
            val logOut = Intent(activity, PaginaAccesso::class.java)
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Sei sicuro di voler effettuare il logout?")
                .setCancelable(false)
                .setPositiveButton("Sì") { dialog, id ->
                    startActivity(logOut)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()

        }

        val annunciTextView = view.findViewById<TextView>(R.id.textViewAnnunciSalvati)
        annunciTextView.setOnClickListener {
            val intent = Intent(activity, PaginaAnnunciSalvati::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(intent)
        }
        val bioTextView = view.findViewById<TextView>(R.id.textViewBiografia)
        bioTextView.setOnClickListener{
            val modificaBio = Intent(activity, PaginaModificaBiografia::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(modificaBio)
        }

        val userTextView = view.findViewById<TextView>(R.id.textViewUser)
        userTextView.setOnClickListener {
            val intent = Intent(activity, PaginaModificaUsername::class.java)
                .putExtra("currentUser", currentUser)
            startActivity(intent)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModificaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModificaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}