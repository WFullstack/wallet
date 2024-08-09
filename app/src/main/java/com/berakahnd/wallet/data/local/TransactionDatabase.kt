package com.berakahnd.wallet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Transaction::class], version = 1)
@TypeConverters(DateConverter::class, TransactionConverter::class)
abstract class TransactionDatabase : RoomDatabase(){
    abstract fun getTransactionDao() : TransactionDao
}
