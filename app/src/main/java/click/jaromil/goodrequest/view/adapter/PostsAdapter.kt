package click.jaromil.goodrequest.view.adapter

import click.jaromil.goodrequest.R
import click.jaromil.goodrequest.model.Post

class PostsAdapter : BaseAdapter<Post>() {
    private val posts = mutableListOf<Post>()
    
    override fun getObjForPosition(position: Int): Post = posts[position]
    
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_post
    
    override fun onItemClick(obj: Post) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun onEmptyData(visible: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun setData(items: MutableList<Post>) {
        posts.clear()
        posts.addAll(items)
        notifyDataSetChanged()
    }
    
    override fun setOnItemClickListener(listener: (Post) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun getItemCount(): Int = posts.size
}