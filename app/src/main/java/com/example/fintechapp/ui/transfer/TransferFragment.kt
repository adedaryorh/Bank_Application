package com.example.fintechapp.ui.transfer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fintechapp.R
import com.example.fintechapp.data.model.User
import com.example.fintechapp.databinding.FragmentTransferBinding
import kotlinx.coroutines.launch

class TransferFragment : Fragment() {
    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedUser: User
    private val accounts = mutableListOf<User>() // Mock or actual data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the selected user from arguments
        selectedUser = arguments?.getParcelable("selectedUser") ?: User(0, "", "", "", "", "", 0.0)

        // Mock Data (replace this with actual data if needed)
        setupMockData()

        // Setup dropdowns and actions
        setupAccountDropdowns()
        setupAmountValidation()
        setupReviewButton()
    }

    private fun setupMockData() {
        accounts.addAll(
            listOf(
                User(1, "John Doe", "john.doe@example.com", "1234567890", "12345678", "001122", 5000.00),
                User(2, "Jane Smith", "jane.smith@example.com", "0987654321", "87654321", "223344", 10000.00)
            )
        )
    }

    private fun setupAccountDropdowns() {
        val fromAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_account,
            accounts.map { it.customerName }
        )
        binding.fromAccount.setAdapter(fromAdapter)

        val toAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_account,
            accounts.map { it.customerName }
        )
        binding.toAccount.setAdapter(toAdapter)
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

        val selectedFromAccount = accounts.find { it.customerName == binding.fromAccount.text.toString() }
        if (selectedFromAccount != null && amount > selectedFromAccount.currentBalance) {
            binding.amountLayout.error = "Insufficient funds"
            return false
        }

        binding.amountLayout.error = null
        return true
    }

    private fun setupReviewButton() {
        binding.review.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(requireContext(), "Transfer reviewed successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        val fromAccount = binding.fromAccount.text.toString()
        val toAccount = binding.toAccount.text.toString()

        if (fromAccount.isEmpty()) {
            binding.fromAccountLayout.error = "Please select a source account"
            return false
        }

        if (toAccount.isEmpty()) {
            binding.toAccountLayout.error = "Please select a destination account"
            return false
        }

        if (fromAccount == toAccount) {
            binding.toAccountLayout.error = "Cannot transfer to the same account"
            return false
        }

        binding.fromAccountLayout.error = null
        binding.toAccountLayout.error = null

        return validateAmount()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
