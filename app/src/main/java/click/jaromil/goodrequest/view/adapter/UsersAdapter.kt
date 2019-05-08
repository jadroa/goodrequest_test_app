package click.jaromil.goodrequest.view.adapter

import android.view.ViewGroup
import click.jaromil.goodrequest.R
import click.jaromil.goodrequest.databinding.ItemUserBinding
import click.jaromil.goodrequest.model.User

class UsersAdapter(private var itemClickListener: ((User) -> Unit)? = null) : BaseAdapter<User>() {
    private val users = mutableListOf<User>()
    private var usersListener: (() -> Unit)? = null
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<User> {
        val holder = super.onCreateViewHolder(parent, viewType)
        (binding as ItemUserBinding).adapter = this
        return holder
    }
    
    override fun getObjForPosition(position: Int): User = users[position]
    
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_user
    
    override fun onItemClick(obj: User) {
        itemClickListener?.let {
            it(obj)
        }
    }
    
    override fun onEmptyData(visible: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun setData(items: MutableList<User>) {
        val startPos = users.size
        users.addAll(items)
        notifyItemRangeInserted(startPos, items.size)
    }

    fun clearData() {
        users.clear()
        notifyDataSetChanged()
    }
    
    override fun setOnItemClickListener(listener: (User) -> Unit) {
        itemClickListener = listener
    }
    
    override fun getItemCount(): Int = users.size
    
    fun setUsersListener(listener: () -> Unit) {
        usersListener = listener
    }
    
    fun getUsers() {
        usersListener?.let {
            it()
        }
    }
}