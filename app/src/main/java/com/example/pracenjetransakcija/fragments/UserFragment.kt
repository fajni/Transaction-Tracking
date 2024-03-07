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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class UserFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    private fun setUser(view: View){
        val userEmail: TextView = view.findViewById(R.id.user_email)
        userEmail.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        val userId: TextView = view.findViewById(R.id.user_id)
        userId.text = FirebaseAuth.getInstance().currentUser?.uid.toString()
    }

    private fun setMoney(view: View){

        val user = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Transactions")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                var availableMoney = 0.0
                var spentMoney = 0.0
                var addedMoney = 0.0
                var numberOfTransactions = 0
                for (childSnapshot in snapshot.children) {
                    // val childKey = childSnapshot.key
                    // val childValue = childSnapshot.value

                    if(user == childSnapshot.child("userId").getValue(String::class.java)){
                        val amount = childSnapshot.child("amount").getValue(Double::class.java)
                        availableMoney += amount!!
                        numberOfTransactions++

                        if(amount < 0)
                            spentMoney -= amount

                        if(amount > 0)
                            addedMoney += amount
                    }

                }
                view.findViewById<TextView>(R.id.money_available).text = availableMoney.toString()
                view.findViewById<TextView>(R.id.money_spent).text = spentMoney.toString()
                view.findViewById<TextView>(R.id.money_added).text = addedMoney.toString()
                view.findViewById<TextView>(R.id.transaction_number).text = numberOfTransactions.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_user, container, false)

        setUser(view)
        setMoney(view)

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