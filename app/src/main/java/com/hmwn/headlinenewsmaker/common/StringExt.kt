package com.hmwn.headlinenewsmaker.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDateTime(pattern: String = "EE, dd MMM yyyy HH:mm"): String {
    val calendar = Calendar.getInstance()
    val date = calendar.time
    val format = SimpleDateFormat( pattern, Locale.getDefault())
    return format.format(date)
}