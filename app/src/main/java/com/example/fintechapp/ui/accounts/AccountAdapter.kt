package com.example.fintechapp.ui.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fintechapp.data.model.Account
import com.example.fintechapp.databinding.ItemAccountBinding


class AccountAdapter :
    ListAdapter<Account, AccountAdapter.MyViewHolder>(AccountDiffUtil()) {

    class MyViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {
            binding.apply {
                accountName.text = account.accountHolder
                accountBalance.text = account.accountBalance.toString()
                accountType.text = account.accountType

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class AccountDiffUtil : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean =
            oldItem == newItem
    }


}

