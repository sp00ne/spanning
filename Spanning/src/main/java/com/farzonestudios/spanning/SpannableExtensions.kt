package com.farzonestudios.spanning

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.ITALIC
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.*
import android.view.View
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow
import com.farzonestudios.spanning.custom.CompatTypefaceSpan

/**
 * Simplify the creation and manipulation of [SpannableString]
 */
fun spannable(
    chars: CharSequence,
    builder: (SpannableString.() -> SpannableString)? = null
): SpannableString {
    return if (builder != null) SpannableString(chars).builder()
    else SpannableString(chars)
}

/**
 *
 * Re-sizes the text it's attached to to an absolute value of [px].
 * @see [setTextSizeDp]
 */
fun SpannableString.setTextSize(@Px px: Int): SpannableString = applySpan(AbsoluteSizeSpan(px))

/**
 * Re-sizes the text it's attached to to an absolute value of [dp].
 * @see [setTextSize]
 */
fun SpannableString.setTextSizeDp(dp: Int): SpannableString =
    applySpan(AbsoluteSizeSpan(dp, true))

/**
 * Applies a text color to the text it's attached to
 */
fun SpannableString.setTextColor(@ColorInt color: Int): SpannableString =
    applySpan(ForegroundColorSpan(color))

/**
 * Applies a span that underlines the text it's attached to
 */
fun SpannableString.setUnderline(): SpannableString =
    applySpan(UnderlineSpan())

/**
 * Applies a span that strikes through the text it's attached to.
 */
fun SpannableString.setStrikethrough(): SpannableString =
    applySpan(StrikethroughSpan())

/**
 * Re-sizes the text it's attached to relatively by [scale].
 */
fun SpannableString.setScale(scale: Float): SpannableString =
    applySpan(RelativeSizeSpan(scale))

/**
 * Applies [Typeface.BOLD] style to the text it's attached to.
 */
fun SpannableString.setBold(): SpannableString =
    applySpan(StyleSpan(BOLD))

/**
 * Applies [Typeface.ITALIC] style to the text it's attached to.
 */
fun SpannableString.setItalic(): SpannableString =
    applySpan(StyleSpan(ITALIC))

/**
 * Applies a background color to the text it's attached to.
 */
fun SpannableString.setBackgroundColor(@ColorInt color: Int): SpannableString =
    applySpan(BackgroundColorSpan(color))

/**
 * Apply a [Typeface] to the [SpannableString].
 * @see [ResourcesCompat.getFont]
 */
fun SpannableString.setFont(typeface: Typeface?): SpannableString =
    applySpan(CompatTypefaceSpan(typeface))

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun SpannableString.setTextAppearance(
    context: Context,
    @StyleRes textAppearance: Int
): SpannableString {
    applySpan(TextAppearanceSpan(context, textAppearance))
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        findFontFromStyle(context, textAppearance) { typeface: Typeface ->
            applySpan(CompatTypefaceSpan(typeface))
        }
    }

    return this
}

private fun findFontFromStyle(context: Context, textAppearance: Int, action: (Typeface) -> Unit) {
    val typedArray =
        context.obtainStyledAttributes(textAppearance, intArrayOf(android.R.attr.fontFamily))

    typedArray.findFontOrNull(context)?.let { typeface ->
        action(typeface)
    }
    typedArray.recycle()
}

private fun TypedArray.findFontOrNull(context: Context): Typeface? {
    for (i in 0 until length()) {
        try {
            val potentialFontId = getResourceIdOrThrow(i)
            return ResourcesCompat.getFont(context, potentialFontId)
        } catch (err: RuntimeException) {
            // Swallow error since we are returning null in case nothing is found
        }
    }
    return null
}

/**
 * Makes the spannable clickable for a [TextView].
 *
 * **IMPORTANT** TextView has to have a movement method of LinkMovementMethod,
 * ```
 * textView.movementMethod = LinkMovementMethod.getInstance()
 * ```
 * on the `TextView`.
 *
 * Assumes the entire string should be clickable unless [what] is specified where it makes that subset of the string clickable instead.
 */
fun SpannableString.setClickable(what: String? = null, onClick: () -> Unit): SpannableString {
    val start: Int
    val end: Int

    if (what != null) {
        start = indexOf(what)
        end = start + what.length
    } else {
        start = 0
        end = length
    }

    return applySpan(object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            // override in order for ClickableSpan not to add its own styling (underlining links etc.)
        }

        override fun onClick(view: View) {
            onClick()
        }
    }, start, end)
}

private fun SpannableString.applySpan(
    what: Any,
    start: Int = 0,
    end: Int = length
): SpannableString = apply {
    setSpan(what, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

/**
 * Operator that appends another [SpannableString] after this one.
 * @see [append]
 */
operator fun SpannableString.plus(next: SpannableString): SpannableString =
    SpannableString(TextUtils.concat(this, next))

/**
 * Infix function that appends another [SpannableString] after this one.
 * @see [plus]
 */
infix fun SpannableString.append(next: SpannableString): SpannableString =
    this + next

/**
 * Similar to [append] but adds a linebreak between this [SpannableString] and the next one.
 * I.e:
 * ```
 * "first string" + "\n" + "second string"
 * ```
 */
infix fun SpannableString.appendLine(next: SpannableString): SpannableString =
    this + spannable("\n") + next

/**
 * Similar to [append] but adds a space between this [SpannableString] and the next one.
 * I.e:
 * ```
 * "first string" + " " + "second string"
 * ```
 */
infix fun SpannableString.appendSpace(next: SpannableString): SpannableString =
    this + spannable(" ") + next