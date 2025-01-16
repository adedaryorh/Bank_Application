package com.example.fintechapp.ui.transfer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.fintechapp.R
import com.example.fintechapp.data.model.Account
import com.example.fintechapp.databinding.FragmentTransferBinding
import com.example.fintechapp.ui.accounts.AccountsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class TransferFragment : Fragment() {
    private lateinit var binding: FragmentTransferBinding
    private val accountsViewModel: AccountsViewModel by activityViewModel()
    private var accounts: List<Account> = emptyList()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupAccountDropdowns()
//        setupAmountValidation()
//        setupReviewButton()
//        observeAccounts()
    }

    private fun setupAccountDropdowns() {
        // Create adapters for the dropdown menus
        val fromAdapter = ArrayAdapter(
            requireContext(), R.layout.item_dropdown_account, mutableListOf<Account>()
        )

        val toAdapter = ArrayAdapter(
            requireContext(), R.layout.item_dropdown_account, mutableListOf<Account>()
        )

        binding.fromAccount.setAdapter(fromAdapter)
        binding.toAccount.setAdapter(toAdapter)

        // Handle account selection changes
        binding.fromAccount.setOnItemClickListener { _, _, position, _ ->
            val selectedAccount = accounts[position]
            updateToAccountDropdown(selectedAccount)
        }

        binding.toAccount.setOnItemClickListener { _, _, _, _ ->
            validateForm()
        }
    }

    private fun setupAmountValidation() {
        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateAmount()
            }
        })
    }

    private fun validateAmount(): Boolean {
        val amountText = binding.amount.text.toString()
        if (amountText.isEmpty()) {
            binding.amountLayout.error = "Amount is required"
            return false
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            binding.amountLayout.error = "Please enter a valid amount"
            return false
        }

        val selectedFromAccount = getSelectedFromAccount()
        if (selectedFromAccount != null && amount > selectedFromAccount.accountBalance) {
            binding.amountLayout.error = "Insufficient funds"
            return false
        }

        binding.amountLayout.error = null
        return true
    }

    private fun validateForm(): Boolean {
        val fromAccount = getSelectedFromAccount()
        val toAccount = getSelectedToAccount()

        if (fromAccount == null) {
            binding.fromAccountLayout.error = "Please select source account"
            return false
        }

        if (toAccount == null) {
            binding.toAccountLayout.error = "Please select destination account"
            return false
        }

        if (fromAccount.id == toAccount.id) {
            binding.toAccountLayout.error = "Cannot transfer to same account"
            return false
        }

        binding.fromAccountLayout.error = null
        binding.toAccountLayout.error = null

        return validateAmount()
    }

    private fun setupReviewButton() {
        binding.review.setOnClickListener {
            if (validateForm()) {
                showTransferReviewDialog()
            }
        }
    }

    private fun showTransferReviewDialog() {
        val fromAccount = getSelectedFromAccount()
        val toAccount = getSelectedToAccount()
        val amount = binding.amount.text.toString().toDouble()

        TransferReviewDialogFragment.newInstance(
            fromAccount!!.id, toAccount!!.id, amount
        ).show(parentFragmentManager, "transfer_review")
    }

    private fun observeAccounts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountsViewModel.accounts.collect { accountsList ->
                    accounts = accountsList
                    updateAccountDropdowns()
                }
            }
        }
    }

    private fun updateAccountDropdowns() {
        (binding.fromAccount.adapter as ArrayAdapter<Account>).apply {
            clear()
            addAll(accounts)
            notifyDataSetChanged()
        }

        // Clear selected accounts
        binding.fromAccount.text = null
        binding.toAccount.text = null
    }

    private fun updateToAccountDropdown(selectedFromAccount: Account) {
        val availableAccounts = accounts.filter { it.id != selectedFromAccount.id }
        (binding.toAccount.adapter as ArrayAdapter<Account>).apply {
            clear()
            addAll(availableAccounts)
            notifyDataSetChanged()
        }
        binding.toAccount.text = null
    }

    private fun getSelectedFromAccount(): Account? {
        val selectedText = binding.fromAccount.text.toString()
        return accounts.find { it.toString() == selectedText }
    }

    private fun getSelectedToAccount(): Account? {
        val selectedText = binding.toAccount.text.toString()
        return accounts.find { it.toString() == selectedText }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }
}