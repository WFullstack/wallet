package com.berakahnd.wallet.data.repository

import com.berakahnd.wallet.data.local.Transaction
import com.berakahnd.wallet.data.local.TransactionDao
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val dao: TransactionDao
) {
    suspend fun depositTransaction(transaction: Transaction) = dao.depositTransaction(transaction)
    fun getAllTransaction() = dao.getAllTransaction()
    fun getNegativeTransactions() = dao.getNegativeTransactions()
    fun getPositiveTransactions() = dao.getPositiveTransactions()
    suspend fun getTotalNegativeAmount() = dao.getTotalNegativeAmount()
    suspend fun getTotalPositiveAmount() = dao.getTotalPositiveAmount()
    suspend fun getTotalAmount() = dao.getTotalAmount()
    suspend fun deleteTransaction(transaction: Transaction) = dao.deleteTransaction(transaction)
}