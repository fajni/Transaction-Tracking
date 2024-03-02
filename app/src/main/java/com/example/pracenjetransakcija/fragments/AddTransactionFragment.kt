package com.example.pracenjetransakcija.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pracenjetransakcija.R
import com.example.pracenjetransakcija.models.TransactionData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddTransactionFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_add_transaction, container, false)

        view.findViewById<Button>(R.id.saveBtn).setOnClickListener {

            val title = view.findViewById<EditText>(R.id.title_edit).text.toString().capitalize()
            val amount = view.findViewById<EditText>(R.id.amount_edit).text.toString().toDouble()
            val info = view.findViewById<EditText>(R.id.info_edit).text.toString().capitalize()
            val date = view.findViewById<EditText>(R.id.date_edit).text.toString()

            firebaseAuth = FirebaseAuth.getInstance()
            val userId = firebaseAuth.currentUser?.uid.toString()

            database = FirebaseDatabase.getInstance().getReference("Transactions")

            val transaction = TransactionData(title, info, amount, date, userId)

            if(title.isNotEmpty() && info.isNotEmpty() && date.isNotEmpty() && amount!=0.0 ){
                val reference = database.push()
                val key = reference.key?.hashCode() ?: 0
                database.child(key.toString()).setValue(transaction).addOnSuccessListener {

                    view.findViewById<EditText>(R.id.title_edit).text.clear()
                    view.findViewById<EditText>(R.id.amount_edit).text.clear()
                    view.findViewById<EditText>(R.id.info_edit).text.clear()
                    view.findViewById<EditText>(R.id.date_edit).text.clear()

                    Toast.makeText(activity, "Successfully Saved! ", Toast.LENGTH_SHORT).show()

                    //go to transaction list fragment after adding new transaction
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, TransactionListFragment())
                    fragmentTransaction.addToBackStack(null) //add current fragment to backstack
                    fragmentTransaction.commit()

                }.addOnFailureListener {
                    Toast.makeText(activity, "Failed to Save!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity, "Empty Fields Are Not Allowed!!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}