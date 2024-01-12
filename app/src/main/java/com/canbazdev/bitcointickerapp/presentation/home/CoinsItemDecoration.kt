package com.canbazdev.bitcointickerapp.presentation.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.bitcointickerapp.R


class CoinsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val spacing = view.resources.getDimensionPixelSize(R.dimen.mediumMarginSize)
        val isFirstItem = parent.getChildAdapterPosition(view) == 0


        with(outRect) {
            if (isFirstItem) top = spacing
            bottom = spacing
            right = spacing / 2
            left = spacing / 2

        }
    }

}