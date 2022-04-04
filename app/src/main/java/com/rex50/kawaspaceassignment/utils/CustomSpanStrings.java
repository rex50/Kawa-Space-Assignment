package com.rex50.kawaspaceassignment.utils;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;

public class CustomSpanStrings {

    private String completeString;
    private SpannableStringBuilder spannableStringBuilder;
    private TextView textView;

    private CustomSpanStrings() {}

    public static CustomSpanStrings withTextView(TextView textView) {
        if(textView == null)
            throw new IllegalArgumentException("TextView should not be null");

        CustomSpanStrings spanStrings = new CustomSpanStrings();
        spanStrings.textView = textView;
        spanStrings.completeString = textView.getText().toString();
        spanStrings.spannableStringBuilder = new SpannableStringBuilder(spanStrings.completeString);
        return spanStrings;
    }

    public CustomSpanStrings applyColor(@ColorInt int color, String... texts) {
        for (String text : texts) {
            int startIndex = completeString.indexOf(text);
            if(startIndex != -1) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color), startIndex,
                        startIndex + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return this;
    }

    public CustomSpanStrings applyUnderline(String... texts) {
        for (String text : texts) {
            int startIndex = completeString.indexOf(text);
            if(startIndex != -1) {
                spannableStringBuilder.setSpan(new UnderlineSpan() {}, completeString.indexOf(text),
                        completeString.indexOf(text) + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return this;
    }

    public CustomSpanStrings applyBold(String... texts) {
        for (String text : texts) {
            int startIndex = completeString.indexOf(text);
            if(startIndex != -1) {
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), completeString.indexOf(text),
                        completeString.indexOf(text) + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return this;
    }

    public void commit() {
        if(textView != null)
            textView.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
    }

}
