package com.hmwn.headlinenewsmaker.common

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.visible() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE) visibility = View.GONE
}

fun RecyclerView.setupRecyclerView(
    context: Context,
    orientation: Int,
    nestedScrollingEnabled: Boolean = false
) {
    this.apply {
        layoutManager = LinearLayoutManager(context, orientation, false)
        isNestedScrollingEnabled = nestedScrollingEnabled
    }
}

fun RecyclerView.setupRecyclerViewVertical(
    context: Context,
    nestedScrollingEnabled: Boolean = false
) {
    this.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        isNestedScrollingEnabled = nestedScrollingEnabled
    }
}

fun RecyclerView.setupRecyclerViewHorizontal(
    context: Context,
    nestedScrollingEnabled: Boolean = false
) {
    this.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        isNestedScrollingEnabled = nestedScrollingEnabled
    }
}