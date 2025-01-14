package com.example.fintechapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID


@Parcelize
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val status: TransactionStatus = TransactionStatus.COMPLETED,
    val sourceAccountId: String,
    val destinationAccountId: String,
    val transferAmount: Double,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable

enum class TransactionStatus {
    COMPLETED, FAILED, PENDING
}


