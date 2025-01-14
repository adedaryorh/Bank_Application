
package com.example.fintechapp.data.repository

import com.example.fintechapp.data.local.AccountDao
import com.example.fintechapp.data.model.Account
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val accountDao: AccountDao) {

    fun getAccountsForUser(customerId: String): Flow<List<Account>> {
        return accountDao.getAccountsForUser(customerId)
    }

    suspend fun getAccountById(accountId: String): Account? {
        return accountDao.getAccountById(accountId)
    }

    suspend fun insertAccount(account: Account): Long {
        return accountDao.insertAccount(account)
    }

    suspend fun updateAccount(account: Account): Int {
        return accountDao.updateAccount(account)
    }

    suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    fun getAccountsAboveBalance(minBalance: Double): Flow<List<Account>> {
        return accountDao.getAccountsAboveBalance(minBalance)
    }

    fun performTransfer(sourceId: String, destinationId: String, amount: Double): Result<Transaction>? {

    }
}
