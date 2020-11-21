package com.farzonestudios.spanning.custom

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * A [MetricAffectingSpan] that sets the [Typeface] that is available for API < 28
 * @see <a href=https://www.youtube.com/watch?v=x-FcOX6ErdI&feature=youtu.be&t=507>Best practices in Android for text - YouTube Video</a>
 */
internal class CompatTypefaceSpan(private val font: Typeface?) : MetricAffectingSpan() {
    override fun updateMeasureState(paint: TextPaint) {
        update(paint)
    }

    override fun updateDrawState(paint: TextPaint?) {
        update(paint)
    }

    fun update(textPaint: TextPaint?) {
        textPaint?.apply {
            val oldStyle = typeface?.style ?: 0

            // keep style set before
            val font = Typeface.create(font, oldStyle)
            typeface = font
        }
    }
}
