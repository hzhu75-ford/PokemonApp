package com.example.pokemon.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(    private val spacing: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State) {
        outRect.bottom = spacing

        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spacing
        }
    }
}