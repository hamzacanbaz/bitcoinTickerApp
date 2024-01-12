package com.canbazdev.bitcointickerapp.presentation.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.bitcointickerapp.R


class CoinsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val spacing = view.resources.getDimensionPixelSize(R.dimen.margin_16dp)
        val isFirstItem = parent.getChildAdapterPosition(view) == 0
        //   val isSecondItem = parent.getChildAdapterPosition(view) == 1

        with(outRect) {
            if (isFirstItem) top = spacing
            bottom = spacing
            right = spacing / 2
            left = spacing / 2

        }
    }

}