package com.farzonestudios.spanningdemo

import android.app.Application
import android.text.SpannableString
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.FontRes
import androidx.annotation.StyleRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.farzonestudios.spanning.*

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val data = MutableLiveData<List<SpannableString>>()

    init {
        val listOfExamples = mutableListOf<SpannableString>()
        with(listOfExamples) {
            addIncreasedTextSize()
            addTextColorAndBackgroundColor(
                text = getColorCompat(R.color.white),
                bg = getColorCompat(R.color.teal_700)
            )
            addUnderlineAndStrikethrough()
            addStyleSpans()
            addCustomFont(R.font.raleway_medium)
            addClickable {
                Toast.makeText(getApplication(), "I was clicked!", Toast.LENGTH_SHORT)
                    .show()
            }
            addTextAppearance(R.style.CustomTextAppearance)
        }

        data.postValue(listOfExamples)
    }

    private fun MutableList<SpannableString>.addTextAppearance(@StyleRes id: Int) {
        add(
            spannable("An actual case, would be to have a custom text appearance load instead") appendSpace
                    spannable("like this") {
                        setTextAppearance(getApplication(), id)
                    }
        )
    }

    private fun MutableList<SpannableString>.addIncreasedTextSize() {
        add(
            spannable("Many things are supported, such as increased size in an absolute manner with") appendSpace
                    spannable("24dp") {
                        setTextSizeDp(24)
                    } appendSpace
                    spannable("or relatively by 1.3") {
                        setScale(1.3f)
                    }
        )
    }

    private fun MutableList<SpannableString>.addTextColorAndBackgroundColor(
        @ColorInt text: Int,
        @ColorInt bg: Int
    ) {
        add(
            spannable("also different") appendSpace spannable("text colors and background colors.") {
                setTextColor(text)
                setBackgroundColor(bg)
            }
        )
    }

    private fun MutableList<SpannableString>.addUnderlineAndStrikethrough() {
        add(
            spannable("you might need to") appendSpace spannable("underline") {
                setUnderline()
            } appendSpace spannable("or") appendSpace spannable("strikethrough") {
                setStrikethrough()
            }
        )
    }

    private fun MutableList<SpannableString>.addStyleSpans() {
        add(
            spannable("you can style fonts") appendSpace spannable("italic") {
                setItalic()
            } appendSpace spannable("or") appendSpace spannable("bold") {
                setBold()
            }
        )
    }

    private fun MutableList<SpannableString>.addCustomFont(@FontRes id: Int) {
        add(
            spannable("or simply use your") appendSpace spannable("custom font") {
                setFont(getFont(id))
            }
        )
    }

    private fun MutableList<SpannableString>.addClickable(action: () -> Unit) {
        add(
            spannable("perhaps clickable to have an action on it. Don't forget to set movement method for the text view then. Click me!") {
                setClickable("Click me!") {
                    action()
                }
            }
        )
    }
}