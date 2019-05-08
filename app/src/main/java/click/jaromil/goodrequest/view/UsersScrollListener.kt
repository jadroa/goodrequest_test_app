package click.jaromil.goodrequest.view

import android.support.v7.widget.RecyclerView
import click.jaromil.goodrequest.view.adapter.UsersAdapter

class UsersScrollListener : RecyclerView.OnScrollListener() {
    
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(1)) {
            getUsers(recyclerView)
        }
    }
    
    private fun getUsers(recycler: RecyclerView) {
        (recycler.adapter as UsersAdapter).getUsers()
    }
}