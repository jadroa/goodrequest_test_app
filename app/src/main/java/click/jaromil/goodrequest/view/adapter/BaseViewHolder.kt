package click.jaromil.goodrequest.view.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.android.databinding.library.baseAdapters.BR


open class BaseViewHolder<in T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(obj: T) {
        binding.setVariable(BR.obj, obj)
        binding.executePendingBindings()
    }
}