
package com.example.fintechapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintechapp.data.model.Account
import com.example.fintechapp.data.model.Converters
import com.example.fintechapp.data.model.Transaction
import com.example.fintechapp.data.model.User

@Database(
    entities = [
        Transaction::class,
        Account::class,
        User::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BankDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
}
