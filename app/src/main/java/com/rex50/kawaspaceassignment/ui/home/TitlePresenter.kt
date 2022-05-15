package com.rex50.kawaspaceassignment.ui.home

import android.graphics.Color
import android.widget.TextView
import com.rex50.kawaspaceassignment.utils.CustomSpanStrings

class TitlePresenter(textView: TextView) {
    init {
        val greeting = "Hello"
        val name = "SpannableString"

        // Set final text in textView
        val title = "$greeting, $name"
        textView.text = title

        CustomSpanStrings.withTextView(textView)
            .addClickListener(name) {
                // Do something when $name is clicked
            }
            .applyBold(name) // To apply bold to $name
            .applyUnderline(name) // To apply underline to $name
            .applyColor(Color.GRAY, name) // To change color of $name
            .applyColor(Color.LTGRAY, greeting) // To change color of $greeting
            .commit() // Finally commit all changes
    }
}