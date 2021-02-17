package com.darrenfinch.mymealplanner.common.lists

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*


abstract class DragAndDropRecyclerViewAdapter<ItemType, ViewHolderType : BaseViewHolder<ItemType>>
    : BaseRecyclerViewAdapter<ItemType, ViewHolderType>(), ItemTouchHelperAdapter {
    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}

class SimpleItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = true

    override fun isItemViewSwipeEnabled() = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        return adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }
}