package com.berakahnd.wallet.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berakahnd.wallet.util.TRANSACTION
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String = "Other",
    val ref : String = "",
    val amount :  Float = 0f,
    val date : Date = Date(),
    val type : TRANSACTION = TRANSACTION.DEPOSIT
)

