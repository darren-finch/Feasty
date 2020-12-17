package com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MarginItemDecoration(private val margin: Int) : ItemDecoration()
{
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        if (parent.getChildAdapterPosition(view) == 0) outRect.top = margin
        outRect.left = margin
        outRect.right = margin
        outRect.bottom = margin
    }
}
