package com.rn1.publisher

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("timeLong")
fun showTimeFormat(textView: TextView, long: Long?){
    long?.let {
        val date = Date(it)
        val df2 = SimpleDateFormat("yyyy.dd.MM HH:mm")
        val dateText: String = df2.format(date)
        textView.text = dateText
    }
}