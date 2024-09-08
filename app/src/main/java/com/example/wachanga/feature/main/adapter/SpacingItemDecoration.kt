package com.example.wachanga.feature.main.adapter

import android.graphics.Rect
import android.util.LayoutDirection
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    betweenItems: Int,
    private val paddingLeft: Int = 0,
    private val paddingTop: Int = 0,
    private val paddingRight: Int = 0,
    private val paddingBottom: Int = 0,
    @RecyclerView.Orientation private val orientation: Int,
    private val spacingPredicate: (item: Any?) -> Boolean = { true },
) : RecyclerView.ItemDecoration() {

    constructor(
        betweenItems: Int,
        padding: Int = 0,
        orientation: Int = RecyclerView.HORIZONTAL,
    ) : this(
        betweenItems = betweenItems,
        paddingLeft = padding,
        paddingTop = padding,
        paddingRight = padding,
        paddingBottom = padding,
        orientation = orientation,
    )

    constructor(
        betweenItems: Int,
        paddingHorizontal: Int = 0,
        paddingVertical: Int = 0,
        orientation: Int,
    ) : this(
        betweenItems = betweenItems,
        paddingLeft = paddingHorizontal,
        paddingTop = paddingVertical,
        paddingRight = paddingHorizontal,
        paddingBottom = paddingVertical,
        orientation = orientation,
    )

    private val spacingBeforeItem = betweenItems / 2
    private val spacingAfterItem = betweenItems - spacingBeforeItem

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        var position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            position = parent.getChildLayoutPosition(view)
        }
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        if (spacingPredicate(parent.getItemByChild(view))) {
            when (orientation) {
                RecyclerView.HORIZONTAL -> addHorizontalSpacing(
                    position = position,
                    state = state,
                    outRect = outRect,
                    rtl = parent.layoutDirection == LayoutDirection.RTL,
                )

                RecyclerView.VERTICAL -> addVerticalSpacing(
                    position = position,
                    outRect = outRect,
                )
            }
        }
    }

    private fun addHorizontalSpacing(
        position: Int,
        state: RecyclerView.State,
        outRect: Rect,
        rtl: Boolean,
    ) {
        outRect.left = spacingBeforeItem
        outRect.top = paddingTop
        outRect.right = spacingAfterItem
        outRect.bottom = paddingBottom
        if (position == FIRST_POSITION) {
            if (rtl) {
                outRect.right = paddingLeft
            } else {
                outRect.left = paddingLeft
            }
        }
        if (position == state.itemCount - 1) {
            if (rtl) {
                outRect.left = paddingRight
            } else {
                outRect.right = paddingRight
            }
        }
    }

    private fun addVerticalSpacing(position: Int, outRect: Rect) {
        outRect.left = paddingLeft
        outRect.top = spacingBeforeItem
        outRect.right = paddingRight
        outRect.bottom = spacingAfterItem
        when (position) {
            FIRST_POSITION -> {
                outRect.top = paddingTop
            }
        }
    }

    private fun RecyclerView.getItemByChild(child: View): Any? {
        return when (val holder = getChildViewHolder(child)) {
            is RecyclerView.ViewHolder -> holder.itemView
            else -> null
        }
    }

    companion object {
        private const val FIRST_POSITION = 0
    }
}
