package com.example.profnotes.presentation.ui.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.profnotes.R


class NoPaddingTextView : View {
    
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.NoPaddingTextView,
            0,
            0
        )
        try {
            setAttrs(a)
        } finally {
            a.recycle()
        }
    }

    var text = ""
    var typefaceId = 0

    private fun setAttrs(a: TypedArray) {
        text = a.getString(R.styleable.NoPaddingTextView_text) ?: ""
        typefaceId = a.getResourceId(R.styleable.NoPaddingTextView_android_fontFamily, 0)
    }

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                200.toFloat(),
                resources.displayMetrics
            )
            if (typefaceId > 0) typeface = ResourcesCompat.getFont(context, typefaceId)
            alpha = 26

        }
    }
    private val bounds = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        paint.getTextBounds("4", 0, 1, bounds)
        val minWidth: Int = paddingLeft + paddingRight + bounds.width() * text.length
        val width: Int = resolveSizeAndState(minWidth, widthMeasureSpec, 0)
        paint.getTextBounds("8", 0, 1, bounds)
        val minHeight: Int = paddingBottom + paddingTop + bounds.height()
        val height: Int = resolveSizeAndState(minHeight, heightMeasureSpec, 0)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, 0F, bounds.height().toFloat(), paint)

    }

}

