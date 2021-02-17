package com.darrenfinch.mymealplanner.common.lists

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<ItemType, ViewHolderType : BaseViewHolder<ItemType>> : RecyclerView
.Adapter<ViewHolderType>() {

    protected val items = mutableListOf<ItemType>()

    fun updateItems(newItems: List<ItemType>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolderType, position: Int) {
        holder.bind(items[position])
    }
}