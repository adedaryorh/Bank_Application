package com.example.fintechapp.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.fintechapp.R
import com.example.fintechapp.databinding.FragmentAccountsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AccountsFragment : Fragment() {
    private lateinit var binding: FragmentAccountsBinding
    private val accountAdapter = AccountAdapter()
    private val accountsViewModel: AccountsViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupTransferButton()
        observeAccounts()

        binding.transfer.setOnClickListener {
            findNavController().navigate(R.id.action_accountsFragment_to_transferFragment)
        }
        binding.accounts.adapter = accountAdapter
    }

    private fun setupRecyclerView() {
        binding.accounts.adapter = accountAdapter

    }

    private fun setupTransferButton() {
        binding.transfer.setOnClickListener {
            findNavController().navigate(R.id.action_accountsFragment_to_transferFragment)
        }
    }

    private fun observeAccounts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountsViewModel.accounts.collect { accounts ->
                    accountAdapter.submitList(accounts)
                }
            }
        }
    }


    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.purple_500))
            .show()
    }

    private fun showSuccess(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background_color
                )
            )
            .show()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsBinding.inflate(layoutInflater)
        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}