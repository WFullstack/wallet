package com.berakahnd.wallet.util

object Tools {
    fun formatNumber(number: Float): String {
        val absValue = kotlin.math.abs(number)
        val sign = if (number < 0) "-" else ""
        return when {
            absValue >= 1_000_000 -> "${sign}${(absValue / 1_000_000).toInt()}M"
            absValue >= 1_000 -> "${sign}${(absValue / 1_000).toInt()}K"
            else -> "${sign}${absValue.toInt()}"
        }
    }
}