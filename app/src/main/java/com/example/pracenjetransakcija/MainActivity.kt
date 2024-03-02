package com.example.pracenjetransakcija

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pracenjetransakcija.databinding.ActivityMainBinding
import com.example.pracenjetransakcija.fragments.AddTransactionFragment
import com.example.pracenjetransakcija.fragments.TransactionListFragment
import com.example.pracenjetransakcija.fragments.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(TransactionListFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile-> replaceFragment(UserFragment())
                R.id.home -> replaceFragment(TransactionListFragment())
                else ->{
                }
            }
            true
        }

        binding.addTransaction.setOnClickListener{
            replaceFragment(AddTransactionFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}