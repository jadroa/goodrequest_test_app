package click.jaromil.goodrequest.view.adapter

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import click.jaromil.goodrequest.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("items")
fun <T>setItems(recyclerView: RecyclerView, items: MutableList<T>?) {
    items?.let {
        if (recyclerView.adapter != null) {
            (recyclerView.adapter as BaseAdapter<T>).setData(items)
        }
    }
}