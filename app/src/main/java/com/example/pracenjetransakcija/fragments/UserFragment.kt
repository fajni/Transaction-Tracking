package com.example.pracenjetransakcija.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pracenjetransakcija.R
import com.example.pracenjetransakcija.SignIn
import com.google.firebase.auth.FirebaseAuth

class UserFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_user, container, false)

        val userEmail: TextView = view.findViewById(R.id.user_email)
        userEmail.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        val userId: TextView = view.findViewById(R.id.user_id)
        userId.text = FirebaseAuth.getInstance().currentUser?.uid.toString()

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()

            Toast.makeText(activity, "Logged out", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, SignIn::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}