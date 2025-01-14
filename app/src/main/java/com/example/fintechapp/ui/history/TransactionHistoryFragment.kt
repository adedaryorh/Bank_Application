package com.example.fintechapp.ui.history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fintechapp.databinding.FragmentTransactionHistoryBinding
import com.example.fintechapp.ui.accounts.AccountsViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class TransactionHistoryFragment : Fragment() {
    private lateinit var binding: FragmentTransactionHistoryBinding
    private val transactionAdapter: TransactionAdapter by lazy { TransactionAdapter() }
    private val accountsViewModel: AccountsViewModel by activityViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        binding.transactions.adapter = transactionAdapter
        transactionAdapter.submitList(listOf())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

}