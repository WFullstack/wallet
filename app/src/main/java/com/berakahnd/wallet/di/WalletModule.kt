package com.berakahnd.wallet.di

import android.content.Context
import androidx.room.Room
import com.berakahnd.wallet.data.local.TransactionDao
import com.berakahnd.wallet.data.local.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {

    @Singleton
    @Provides
    fun provideTransactionDatabase(@ApplicationContext context : Context) : TransactionDatabase {
        return  Room.databaseBuilder(
            context,
            TransactionDatabase::class.java,
            "transaction.db"
        ).build()
    }
    @Singleton
    @Provides
    fun provideTransactionDao(db : TransactionDatabase) : TransactionDao {
        return  db.getTransactionDao()
    }

}