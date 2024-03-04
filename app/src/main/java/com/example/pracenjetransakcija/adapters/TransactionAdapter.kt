package com.example.pracenjetransakcija.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pracenjetransakcija.R
import com.example.pracenjetransakcija.TransactionInfoActivity
import com.example.pracenjetransakcija.models.TransactionData
import com.google.firebase.auth.FirebaseAuth

class TransactionAdapter(private var transactionList: ArrayList<TransactionData>) :
    RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title)
        val info: TextView = itemView.findViewById(R.id.info)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val date: TextView = itemView.findViewById(R.id.date)
        var image_symbol: ImageView = itemView.findViewById(R.id.image_symbol)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var user = FirebaseAuth.getInstance()
        val id = user.currentUser?.uid.toString()

        if (transactionList[position].userId == id) {
            val currentItem = transactionList[position]

            holder.title.text = currentItem.title
            holder.info.text = currentItem.info
            holder.amount.text = currentItem.amount.toString() + " $"
            holder.date.text = currentItem.date

            if (currentItem.title!!.length > 10) {
                holder.title.text = currentItem.title!!.substring(0, 10) + "..."
            }

            if (currentItem.info!!.length > 15)
                holder.info.text = currentItem.info!!.substring(0, 15) + "..."

            //images:
            holder.image_symbol.setImageResource(R.drawable.add_symbol)
            holder.amount.setTextColor(Color.rgb(59, 128, 34))
            if (holder.amount.text.contains('-')) {
                holder.image_symbol.setImageResource(R.drawable.remove_symbol)
                holder.amount.setTextColor(Color.rgb(143, 33, 33))
            }

            //Recyclerview onClickListener to NewActivty
            //prosledjivanje se vrsi putem intent
            holder.itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Transaction key: " + transactionList[position].key, Toast.LENGTH_SHORT).show()
                Toast.makeText(holder.itemView.context, "Transaction number: $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(holder.itemView.context, TransactionInfoActivity::class.java)
                intent.putExtra("key", transactionList[position].key)
                intent.putExtra("title", transactionList[position].title)
                intent.putExtra("amount", transactionList[position].amount.toString())
                intent.putExtra("info", transactionList[position].info)
                intent.putExtra("date", transactionList[position].date)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    fun searchTransactionList(searchList: ArrayList<TransactionData>) {
        transactionList = searchList
        notifyDataSetChanged()
    }
}