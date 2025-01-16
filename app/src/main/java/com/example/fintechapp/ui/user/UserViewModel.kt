package com.example.fintechapp.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fintechapp.data.model.User

class UserViewModel : ViewModel() {

    // LiveData to observe user data
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        // Add sample data to the list
        loadSampleData()
    }

    private fun loadSampleData() {
        val sampleUsers = listOf(
            User(
                id = 1,
                customerName = "John Doe",
                email = "john.doe@example.com",
                phoneNumber = "123-456-7890",
                accountNumber = "9876543210",
                sortCode = "001122",
                currentBalance = 5000.00
            ),
            User(
                id = 2,
                customerName = "Jane Smith",
                email = "jane.smith@example.com",
                phoneNumber = "987-654-3210",
                accountNumber = "1234567890",
                sortCode = "112233",
                currentBalance = 12000.00
            ),
            User(
                id = 3,
                customerName = "Alice Johnson",
                email = "alice.johnson@example.com",
                phoneNumber = "555-123-4567",
                accountNumber = "4561237890",
                sortCode = "445566",
                currentBalance = 7000.50
            )
        )
        _users.value = sampleUsers
    }
}