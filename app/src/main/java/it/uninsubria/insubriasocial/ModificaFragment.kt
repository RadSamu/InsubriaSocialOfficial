package it.uninsubria.insubriasocial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

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