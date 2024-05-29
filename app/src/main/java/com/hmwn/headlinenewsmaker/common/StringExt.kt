package com.hmwn.headlinenewsmaker.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDateTime(): String {
    val calendar = Calendar.getInstance()
    val date = calendar.time
    val format = SimpleDateFormat("EE, dd MMM yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}