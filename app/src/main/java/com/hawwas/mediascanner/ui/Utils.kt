package com.hawwas.ulibrary.ui

import android.content.*
import com.hawwas.ulibrary.*
import java.util.*

fun getMIMEType(extension: String): String {
    return when (extension.lowercase(Locale.getDefault())) {

        "pdf" -> "application/pdf"
        "txt" -> "text/plain"

        "mp4" -> "video/mp4"

        "mp3" -> "audio/mpeg"
        "ogg" -> "audio/ogg"
        "wav" -> "audio/vnd.wave"

        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"

        "doc", "docx" -> "application/msword"
        "xls", "xlsx" -> "application/vnd.ms-excel"
        "ppt", "pptx" -> "application/vnd.ms-powerpoint"

        "zip" -> "application/zip"
        "rar" -> "application/x-rar-compressed"

        else -> "*/*"
    }
}
//
//fun getLastWatched(lastTime: Long, context: Context): String {
//    val currentTime = System.currentTimeMillis()
//    val diff = currentTime - lastTime
//    val seconds = diff / 1000
//    val minutes = seconds / 60
//    val hours = minutes / 60
//    val days = hours / 24
//    val months = days / 30
//    val years = months / 12
//    return when {
//        years > 0 -> y
//        months > 0 -> context.getString(R.string.months_ago, months)
//        days > 0 -> context.getString(R.string.days_ago, days)
//        hours > 0 -> context.getString(R.string.hours_ago, hours)
//        minutes > 0 -> context.getString(R.string.minutes_ago, minutes)
//        else -> context.getString(R.string.seconds_ago, seconds)
//    }
//}

fun getSize(size: Long): String {
val kilo= size/1024f
val mega= kilo/1024f
val giga= mega/1024f
    return when{
        giga>1 -> String.format("%.2f GB",giga)
        mega>1 -> String.format("%.2f MB",mega)
        else -> String.format("%.2f KB",kilo)
    }

}
fun validFileName(newFileName: String): Boolean {
    val invalidChars = setOf('\\', '/', ':', '*', '?', '"','<', '>', '|')
    return newFileName.isNotEmpty() && newFileName.none { it in invalidChars }
}
