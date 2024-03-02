package com.example.pracenjetransakcija.models

data class UserData (

    var email: String? = null,
    var id: String? = null,

    var available_money: Double? = null,
    var money_spent: Double? = null,
    var money_added: Double? = null,
    var salary: Double? = null,
    var overdraft: Double? = null,

    var transaction_number: Int? = null
)