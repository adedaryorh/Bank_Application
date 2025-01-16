import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fintechapp.data.model.User
import com.example.fintechapp.databinding.ItemUserBinding

class UserAdapter(private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var userAccounts: List<User> = listOf()

    fun setData(accounts: List<User>) {
        userAccounts = accounts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userAccounts[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }

    override fun getItemCount(): Int = userAccounts.size

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvAccountHolder.text = user.customerName
            binding.tvAccountNumber.text = "Account: ${user.accountNumber}"
            binding.tvBankCode.text = "Sort Code: ${user.sortCode}"
            binding.tvAccountBalance.text = "Balance: NGN${user.currentBalance}"
        }
    }
}
