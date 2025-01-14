package com.example.fintechapp.data.model

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun fromTransactionStatus(value: TransactionStatus): String {
        return value.name // Convert enum to String
    }

    @TypeConverter
    fun toTransactionStatus(value: String): TransactionStatus {
        return TransactionStatus.valueOf(value) // Convert String to enum
    }
}
