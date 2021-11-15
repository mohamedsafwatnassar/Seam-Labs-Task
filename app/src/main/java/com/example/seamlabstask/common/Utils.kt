package com.example.seamlabstask.common

import android.os.Build
import java.text.SimpleDateFormat

object Utils {

    fun formatDate(date: String): String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val output = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("EEEE,dd MMMM YYYY - hh:mm")
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        return output.format(input.parse(date))
    }

}