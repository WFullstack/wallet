package com.berakahnd.wallet.data.local

import androidx.room.TypeConverter
import com.berakahnd.wallet.util.TRANSACTION

class TransactionConverter {
    @TypeConverter
    fun fromTransactionType(value: TRANSACTION?): String? {
        return value?.name
    }

    @TypeConverter
    fun toTransactionType(value: String?): TRANSACTION? {
        return value?.let { TRANSACTION.valueOf(it) }
    }
}