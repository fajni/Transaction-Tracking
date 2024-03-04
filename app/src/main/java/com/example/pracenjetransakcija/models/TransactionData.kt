package com.example.pracenjetransakcija.models

import java.util.Calendar

data class TransactionData(
    //all variables fetch from firebase:
    var key: String? = null,
    var title: String? = null,
    var info: String? = null,
    var amount: Double? = null,
    var date: String? = Calendar.getInstance().time.toString(),
    var userId: String? = null,
    var img: String? = null
)
    //this.date = Calendar.getInstance().time