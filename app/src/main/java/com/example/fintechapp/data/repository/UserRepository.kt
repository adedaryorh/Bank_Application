
package com.example.fintechapp.data.repository

import com.example.fintechapp.data.local.UserDao
import com.example.fintechapp.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun deleteUser(userId: String) {
        userDao.deleteUser(userId)
    }
}
