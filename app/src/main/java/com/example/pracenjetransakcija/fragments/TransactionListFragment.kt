package com.example.pracenjetransakcija.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pracenjetransakcija.R
import com.example.pracenjetransakcija.adapters.TransactionAdapter
import com.example.pracenjetransakcija.models.TransactionData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TransactionListFragment : Fragment() {

    private lateinit var dbref: DatabaseReference
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var transactionArrayList: ArrayList<TransactionData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_transaction_list, container, false)

        transactionRecyclerView = view.findViewById(R.id.transactionList)
        transactionRecyclerView.layoutManager = LinearLayoutManager(context)
        transactionRecyclerView.setHasFixedSize(true)

        transactionArrayList = arrayListOf<TransactionData>()
        getTransactionsData()

        return view
    }

    private fun getTransactionsData(){

        dbref = FirebaseDatabase.getInstance().getReference("Transactions")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = FirebaseAuth.getInstance()
                val id = user.currentUser?.uid.toString()
                // in snapshot we're getting data from firebase
                if (snapshot.exists()) {
                    transactionArrayList.clear()
                    for (transactionSnapshot in snapshot.children) {
                        val transaction = transactionSnapshot.getValue(TransactionData::class.java)

                        if (transaction?.userId == id) {
                            transactionArrayList.add(transaction)
                        }
                    }
                    transactionArrayList.sortByDescending { transactionData -> transactionData.date }

                    transactionRecyclerView.adapter = TransactionAdapter(transactionArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}