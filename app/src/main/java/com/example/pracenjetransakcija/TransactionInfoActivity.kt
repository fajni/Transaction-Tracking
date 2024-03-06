package com.example.pracenjetransakcija

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pracenjetransakcija.databinding.ActivityTransactionInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TransactionInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionInfoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Transactions")

        val key: TextView = findViewById(R.id.key)
        key.text = intent.getStringExtra("key").toString()

        val email: TextView = findViewById(R.id.email_transaction)
        email.text = firebaseAuth.currentUser?.email.toString()

        val id: TextView = findViewById(R.id.id)
        id.text = firebaseAuth.currentUser?.uid.toString()

        val amount: TextView = findViewById(R.id.amount_transaction)
        amount.text = intent.getStringExtra("amount").toString()
        amount.setTextColor(Color.rgb(59, 128, 34))

        val info:TextView = findViewById(R.id.info_transaction)
        info.text = intent.getStringExtra("info").toString()

        val date:TextView = findViewById(R.id.date_transaction)
        date.text = intent.getStringExtra("date").toString()

        var title: TextView = findViewById(R.id.title_transaction)
        title.text = intent.getStringExtra("title").toString()
        title.setBackgroundResource(R.drawable.rounded_corners_green)
        title.setTextColor(Color.rgb(59, 128, 34))

        var img: ImageView = findViewById(R.id.image_symbol_transaction)
        img.setImageResource(R.drawable.add_symbol)
        if(amount.text.contains('-')){
            img.setImageResource(R.drawable.remove_symbol)
            amount.setTextColor(Color.rgb(143, 33, 33))
            title.setBackgroundResource(R.drawable.rounded_corners_red)
            title.setTextColor(Color.rgb(143, 33, 33))
        }

        binding.deleteTransaction.setOnClickListener {
            deleteTransaction(key.text.toString())
        }

        binding.updateTransaction.setOnClickListener {
            updateTransaction(key.text.toString(), amount.text.toString(), info.text.toString(), date.text.toString(), title.text.toString())
        }

        /*
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Transactions")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (childSnapshot in snapshot.children) {
                    val childKey = childSnapshot.key
                    val childValue = childSnapshot.value

                    if(childKey == key.text)
                        Toast.makeText(baseContext, childKey, Toast.LENGTH_SHORT).show()

                    //Toast.makeText(baseContext, childKey, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
         */


    }

    private fun deleteTransaction(key: String){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Transactions").child(key)
        reference.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Deleted transaction $key", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to delete transaction $key!", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateTransaction(key: String, amount: String, info: String, date: String, title: String){

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Transactions").child(key)
        val newData = mapOf(
            "title" to title,
            "amount" to amount.toDouble(),
            "info" to info,
            "date" to date
        )
        reference.updateChildren(newData).addOnSuccessListener {
            Toast.makeText(this, "Updated Transaction $key", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed To Update Transaction $key", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}