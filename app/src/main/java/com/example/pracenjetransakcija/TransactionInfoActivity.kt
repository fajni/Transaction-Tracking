package com.example.pracenjetransakcija

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
            deleteTransaction()
        }

        binding.updateTransaction.setOnClickListener {
            updateTransaction()
        }

    }

    private fun deleteTransaction(){
        Toast.makeText(this, "Delete transaction", Toast.LENGTH_SHORT).show()
    }

    private fun updateTransaction(){
        Toast.makeText(this, "Update transaction", Toast.LENGTH_SHORT).show()

    }
}