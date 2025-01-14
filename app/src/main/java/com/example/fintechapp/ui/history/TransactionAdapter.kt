package com.example.fintechapp.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fintechapp.data.model.Transaction
import com.example.fintechapp.databinding.ItemTransactionBinding


class TransactionAdapter :
    ListAdapter<Transaction, TransactionAdapter.MyViewHolder>(TransactionDiffUtil()) {

    class MyViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.apply {
                transactionAmount.text = transaction.transferAmount.toString()
                toAccount.text = transaction.destinationAccountId
                fromAccount.text = transaction.sourceAccountId
                transactionDate.text = transaction.timestamp.toString()
                //transactionStatus.setText(transaction.status)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class TransactionDiffUtil : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem == newItem
    }


}

