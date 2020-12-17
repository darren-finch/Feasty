package com.darrenfinch.mymealplanner.common.lists

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

abstract class BaseViewHolder<ItemType>(itemView: View) : RecyclerView.ViewHolder(itemView), Serializable {
    abstract fun bind(item: ItemType)
}