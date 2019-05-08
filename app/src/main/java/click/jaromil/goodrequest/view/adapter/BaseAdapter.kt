package click.jaromil.goodrequest.view.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    protected lateinit var binding: ViewDataBinding
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return BaseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val obj = getObjForPosition(position)
        holder.bind(obj)
    }
    
    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }
    
    protected abstract fun getObjForPosition(position: Int): T
    
    protected abstract fun getLayoutIdForPosition(position: Int): Int
    
    abstract fun onItemClick(obj: T)
    
    abstract fun onEmptyData(visible: Boolean)
    
    abstract fun setData(items: MutableList<T>)
    
    abstract fun setOnItemClickListener(listener: (T) -> Unit)
}