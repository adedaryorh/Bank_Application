package com.example.fintechapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID


@Parcelize
@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val customerId: String,
    val accountHolder: String,
    val accountType: String,
    var accountBalance: Double,
) : Parcelable
