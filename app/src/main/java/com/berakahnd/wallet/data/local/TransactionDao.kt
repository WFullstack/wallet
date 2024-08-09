package com.berakahnd.wallet.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun depositTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAllTransaction() : Flow<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transaction_table")
    suspend fun getTotalAmount(): Float

    @Query("SELECT * FROM transaction_table WHERE amount > 0 ORDER BY id DESC")
    fun getPositiveTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE amount < 0 ORDER BY id DESC")
    fun getNegativeTransactions(): Flow<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE amount < 0")
    suspend fun getTotalNegativeAmount(): Float

    @Query("SELECT SUM(amount) FROM transaction_table WHERE amount > 0")
    suspend fun getTotalPositiveAmount(): Float

}