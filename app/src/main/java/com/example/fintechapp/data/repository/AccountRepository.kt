
package com.example.fintechapp.data.repository

import com.example.fintechapp.data.local.AccountDao
import com.example.fintechapp.data.local.TransactionDao
import com.example.fintechapp.data.model.Transaction


class AccountRepository(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) {

    fun getAccountsForUser(userId: String) = accountDao.getAccountsForUser(userId)

    suspend fun performTransfer(
        sourceId: String,
        destinationId: String,
        amount: Double
    ): Result<Transaction> {
        return try {
            val sourceAccount = accountDao.getAccountById(sourceId) ?: return Result.failure(
                Exception("Source account not found")
            )
            val destAccount = accountDao.getAccountById(destinationId) ?: return Result.failure(
                Exception("Destination account not found")
            )

            if (sourceAccount.accountBalance < amount) {
                return Result.failure(Exception("Insufficient funds"))
            }

            sourceAccount.accountBalance -= amount
            destAccount.accountBalance += amount

            accountDao.updateAccount(sourceAccount)
            accountDao.updateAccount(destAccount)

            val transaction = Transaction(
                sourceAccountId = sourceId,
                destinationAccountId = destinationId,
                transferAmount = amount
            )
            transactionDao.insertTransaction(transaction)

            Result.success(transaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}