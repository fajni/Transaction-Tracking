package com.example.pracenjetransakcija.adapters

import com.example.pracenjetransakcija.models.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pracenjetransakcija.R
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(private val user: UserData) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val user_email: TextView = item.findViewById(R.id.user_email)
        val user_id: TextView = item.findViewById(R.id.user_id)

        val available_money: TextView = item.findViewById(R.id.money_available)
        val money_spent: TextView = item.findViewById(R.id.money_spent)
        val money_added: TextView = item.findViewById(R.id.money_added)
        val salary: TextView = item.findViewById(R.id.salary)
        val overdraft: TextView = item.findViewById(R.id.overdraft)

        val transaction_number: TextView = item.findViewById(R.id.transaction_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var acc = FirebaseAuth.getInstance()
        val acc_id = acc.currentUser?.uid.toString()

        user.id = acc_id
        user.email = acc.toString()

        val currentUser = user
        currentUser.id = acc_id
        currentUser.email = FirebaseAuth.getInstance().toString()

        holder.user_email.text = currentUser.email.toString()
        holder.user_id.text = currentUser.id.toString()

        holder.available_money.text = currentUser.available_money.toString()
        holder.money_spent.text = currentUser.money_spent.toString()
        holder.money_added.text = currentUser.money_added.toString()
        holder.salary.text = currentUser.salary.toString()
        holder.overdraft.text = currentUser.overdraft.toString()

        holder.transaction_number.text = currentUser.transaction_number.toString()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}