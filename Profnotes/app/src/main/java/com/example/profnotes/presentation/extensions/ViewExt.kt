package com.example.profnotes.presentation.extensions

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.*

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {

    val initialPadding = recordInitialPaddingForView(this)

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

/**
 * Метод выставляет у вью паддинг, равный высоте нав бара.
 * При этом помечает, что обработал bottom inset
 */
fun View.applyBottomInsets() {
    Log.d("apply", "bottom inset")
    this.doOnApplyWindowInsets { view, insets, paddings ->
        val windowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        Log.d("get insets", windowInsets.bottom.toString())
        view.updatePadding(
            bottom = windowInsets.bottom + paddings.bottom,
        )
        WindowInsetsCompat.Builder().setInsets(
            WindowInsetsCompat.Type.systemBars(),
            Insets.of(
                windowInsets.left,
                windowInsets.top,
                windowInsets.right,
                0
            )
        ).build()
    }
}

/**
 * Метод выставляет у вью паддинг, равный высоте статус бара.
 * При этом помечает, что обработал top inset
 */
fun View.applyTopInsets() {
    this.doOnApplyWindowInsets { view, insets, paddings ->
        val windowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(
            top = windowInsets.top + paddings.top,
        )
        WindowInsetsCompat.Builder().setInsets(
            WindowInsetsCompat.Type.systemBars(),
            Insets.of(
                windowInsets.left,
                0,
                windowInsets.right,
                windowInsets.bottom
            )
        ).build()
    }
}

/**
 * Метод выставляет у вью паддинг сверху, равный высоте статус бара (верхнему системному инсету),
 * и паддинг снизу, равный высоте клавиатуры.
 * При этом помечает, что обработал top и bottom insets
 */
fun View.applyKeyboardInsets() {
    this.doOnApplyWindowInsets { view, insets, paddings ->
        val windowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        view.updatePadding(
            top = windowInsets.top + paddings.top,
            bottom = windowInsets.bottom + paddings.bottom
        )
        WindowInsetsCompat.Builder().setInsets(
            WindowInsetsCompat.Type.systemBars(),
            Insets.of(
                windowInsets.left,
                0,
                windowInsets.right,
                0
            )
        ).build()
    }
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}


