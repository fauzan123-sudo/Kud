package com.example.kud.data.adapter

//import android.view.LayoutInflater
//import android.view.View
//import java.util.concurrent.Executors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.kud.ui.fragment.RecyclerViewClickListener

//sederhana
abstract class BaseAdapter<T, VH : BaseAdapter.BaseViewHolder<T, VB>, VB : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = inflater(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size


    abstract fun createViewHolder(binding: VB): VH

    abstract class BaseViewHolder<T, VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
    }
}





//skala besar
//abstract class BaseAdapter<T : Any, VH : BaseAdapter.ViewHolder<T>> :
//    RecyclerView.Adapter<VH>() {
//
//    var listener: RecyclerViewClickListener<T>? = null
//    private var items: MutableList<T> = mutableListOf()
//
//    fun setItems(newItems: List<T>) {
//        items.clear()
//        items.addAll(newItems)
//        notifyDataSetChanged()
//    }
//
//    fun addItem(newItem: T) {
//        items.add(newItem)
//        notifyItemInserted(items.lastIndex)
//    }
//
//    fun addItems(newItems: List<T>) {
//        val lastIndex = items.lastIndex
//        items.addAll(newItems)
//        notifyItemRangeInserted(lastIndex + 1, newItems.size)
//    }
//
//    fun clearItems() {
//        items.clear()
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.bind(items[position])
//        holder.itemView.setOnClickListener {
//            listener?.onItemClicked(it, items[position])
//        }
//    }
//
//    abstract class ViewHolder<T : Any>(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        abstract fun bind(item: T)
//    }
//}




//abstract class BaseAdapter<T, VB : RecyclerView.ViewHolder>(
//    private val diffCallback: DiffUtil.ItemCallback<T>,
//    private val layoutInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
//) : RecyclerView.Adapter<VB>() {
//
//    private var _differ: AsyncListDiffer<T>? = null
//
//    val differ: AsyncListDiffer<T>
//        get() = _differ!!
//
//    var data: List<T>
//        get() = differ.currentList
//        set(value) = differ.submitList(value)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VB {
//        val inflater = LayoutInflater.from(parent.context)
//        return layoutInflater(inflater, parent, false)
//    }
//
//    override fun onBindViewHolder(holder: VB, position: Int) {
//        val item = data[position]
//        setDataToView(holder, item)
//    }
//
//    override fun getItemCount(): Int = data.size
//
//    abstract fun setDataToView(binding: VB, item: T)
//
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        _differ = AsyncListDiffer(this, diffCallback)
//    }
//
//    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
//        super.onDetachedFromRecyclerView(recyclerView)
//        _differ = null
//    }
//}
//
//
//
//
//
//
//
//abstract class BaseAdapter<T>(
//    private val data:List<T>,
//    private val layoutId:Int
//) : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {
//    inner class BaseViewHolder(val view:View):RecyclerView.ViewHolder(view) {
//        fun bind(item:T){
//            setDataToView(view, item)
//        }
//
//    }
//
//    private fun setDataToView(view: View, item: T) {
//
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//      return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent,false))
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        holder.bind(data[position])
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//}