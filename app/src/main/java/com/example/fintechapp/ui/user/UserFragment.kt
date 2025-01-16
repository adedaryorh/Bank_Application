package com.example.fintechapp.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechapp.data.model.User
import com.example.fintechapp.databinding.FragmentUsersBinding

class UserFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private val userAdapter: UserAdapter by lazy { UserAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setupRecyclerView()

        //observeUserAccounts()
        // Load dummy data (replace with actual data fetching logic later

        // Handle item clicks
        userAdapter.onItemClick = { user ->
            Toast.makeText(requireContext(), "Clicked: ${user.customerName}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupRecyclerView() {
        var layoutManager: LinearLayoutManager? = null
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        Log.d("UserFragment", "Setting up RecyclerView")
        val dummyUsers = listOf(
            User(1, "John Doe", "john.doe@example.com", "1234567890", "12345678", "001122", 5000.00),
            User(2, "Jane Smith", "jane.smith@example.com", "0987654321", "87654321", "223344", 10000.00)
        )
        // Log data to check
        Log.d("UserFragment", "Loaded users: $dummyUsers")
        // Set data to adapter
        userAdapter.setData(dummyUsers)
        binding.recyclerViewAccounts.apply {
            adapter = userAdapter
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

