package com.example.profnotes.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.cardview.widget.CardView

/**
 * Позволяет перехватывать клики по карточке
 * при нажатии на ресайкл внутри нее
 */
class CardViewWithIntercept : CardView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

}