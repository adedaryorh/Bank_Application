package com.example.fintechapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val customerName: String,
    val email: String,
    val phoneNumber: String,
    val accountNumber: String,
    val sortCode: String,
    val currentBalance: Double
):Parcelable
