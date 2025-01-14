package com.example.fintechapp.db

import androidx.room.Room
import com.example.fintechapp.data.local.BankDatabase
import com.example.fintechapp.util.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


// DatabaseModule.kt
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), BankDatabase::class.java,  DATABASE_NAME
        ).build()
    }

    single { get<BankDatabase>().accountsDao() }
    single { get<BankDatabase>().transactionsDao() }
}



