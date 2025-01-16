package com.example.fintechapp.ui.user

import UserAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.fintechapp.data.model.User
import com.example.fintechapp.databinding.FragmentUsersBinding

class UserFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private val userAdapter: UserAdapter by lazy {
        UserAdapter { selectedUser ->
            onUserSelected(selectedUser)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupRecyclerView()

        // Load dummy data (replace with actual data fetching logic later)
        loadDummyUsers()
    }

    private fun setupRecyclerView() {
        Log.d("UserFragment", "Setting up RecyclerView")
        binding.recyclerViewAccounts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
    }

    private fun loadDummyUsers() {
        val dummyUsers = listOf(
            User(1, "John Doe", "john.doe@example.com", "1234567890", "12345678", "001122", 5000.00),
            User(2, "Jane Smith", "jane.smith@example.com", "0987654321", "87654321", "223344", 10000.00)
        )
        Log.d("UserFragment", "Loaded users: $dummyUsers")
        userAdapter.setData(dummyUsers)
    }

    private fun onUserSelected(user: User) {
        Toast.makeText(requireContext(), "Clicked: ${user.customerName}", Toast.LENGTH_SHORT).show()

        // Navigate to TransferFragment using SafeArgs
//        val action: NavDirections = UserFragmentDirections.actionUsersFragmentToTransferFragment(user)
//        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
