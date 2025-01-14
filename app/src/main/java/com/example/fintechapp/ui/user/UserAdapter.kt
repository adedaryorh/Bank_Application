package com.example.fintechapp.ui.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fintechapp.data.model.User
import com.example.fintechapp.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var userAccounts: List<User> = listOf()
    var onItemClick: ((User) -> Unit)? = null

    fun setData(@SuppressLint("RestrictedApi") accounts: List<com.firebase.ui.auth.data.model.User>) {
        userAccounts = userAccounts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = userAccounts[position]
        holder.bind(account)
    }

    override fun getItemCount(): Int = userAccounts.size

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: User) {
            binding.tvAccountHolder.text = account.customerName
            binding.tvAccountBalance.text = "Balance: ${account.currentBalance}"
        }
    }
}