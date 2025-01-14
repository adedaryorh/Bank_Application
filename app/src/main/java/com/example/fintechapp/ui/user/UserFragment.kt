package com.example.fintechapp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechapp.databinding.FragmentUsersBinding

class UserFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAccountViewModel: UserViewModel
    private val userAdapter: UserAdapter by lazy { UserAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //userAccountViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupRecyclerView()
        //observeUserAccounts()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewAccounts.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun ViewModelProvider.get(java: Class<UserViewModel>) {}
