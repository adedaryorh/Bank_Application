package com.example.fintechapp.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.fintechapp.R
import com.example.fintechapp.data.model.Account
import com.example.fintechapp.databinding.DialogTransferConfirmationBinding
import com.example.fintechapp.ui.accounts.AccountsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat

class TransferReviewDialogFragment : DialogFragment() {
    private lateinit var binding: DialogTransferConfirmationBinding

    private val accountsViewModel: AccountsViewModel by viewModel()
    private var onTransferConfirmed: (() -> Unit)? = null

    private lateinit var fromAccount: Account
    private lateinit var toAccount: Account
    private var amount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            com.google.android.material.R.style.Theme_Material3_Light_Dialog_Alert
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTransferConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get transfer details from arguments
        arguments?.let { args ->
            val fromAccountId = args.getString(ARG_FROM_ACCOUNT_ID) ?: return
            val toAccountId = args.getString(ARG_TO_ACCOUNT_ID) ?: return
            amount = args.getDouble(ARG_AMOUNT)

            loadAccountDetails(fromAccountId, toAccountId)
        }

        setupButtons()
    }

    private fun loadAccountDetails(fromAccountId: String, toAccountId: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountsViewModel.accounts.collect { accounts ->
                    fromAccount = accounts.find { it.id == fromAccountId } ?: return@collect
                    toAccount = accounts.find { it.id == toAccountId } ?: return@collect
                    updateUI()
                }
            }
        }
    }

    private fun updateUI() {
        binding.apply {
            fromAccountName.text = fromAccount.accountHolder
            toAccountName.text = toAccount.accountHolder
            transferAmount.text = formatAmount(amount)
        }
    }

    private fun formatAmount(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }

    private fun setupButtons() {
        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.confirm.setOnClickListener {
            performTransfer()
        }
    }

    private fun performTransfer() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                binding.confirm.isEnabled = false
                binding.cancel.isEnabled = false

                accountsViewModel.performTransfer(
                    sourceId = fromAccount.id,
                    destinationId = toAccount.id,
                    amount = amount
                )

                showSuccessAndDismiss()
            } catch (e: Exception) {
                showError(e.message ?: "Transfer failed")
                binding.confirm.isEnabled = true
                binding.cancel.isEnabled = true
            }
        }
    }

    private fun showSuccessAndDismiss() {
        onTransferConfirmed?.invoke()
        dismiss()

        // Show success message in the parent fragment
        (parentFragment as? TransferFragment)?.let { fragment ->
            Snackbar.make(
                fragment.requireView(),
                "Transfer completed successfully",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun showError(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(
            ContextCompat.getColor(requireContext(), R.color.error)
        ).show()
    }

    fun setOnTransferConfirmedListener(listener: () -> Unit) {
        onTransferConfirmed = listener
    }


    companion object {
        private const val ARG_FROM_ACCOUNT_ID = "from_account_id"
        private const val ARG_TO_ACCOUNT_ID = "to_account_id"
        private const val ARG_AMOUNT = "amount"

        fun newInstance(
            fromAccountId: String,
            toAccountId: String,
            amount: Double
        ): TransferReviewDialogFragment {
            return TransferReviewDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FROM_ACCOUNT_ID, fromAccountId)
                    putString(ARG_TO_ACCOUNT_ID, toAccountId)
                    putDouble(ARG_AMOUNT, amount)
                }
            }
        }
    }
}