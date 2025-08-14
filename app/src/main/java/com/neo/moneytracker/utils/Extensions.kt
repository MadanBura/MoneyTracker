package com.neo.moneytracker.utils


fun String.toSentenceCase(): String {
    val trimmed = trim()
    if (trimmed.isEmpty()) return ""
    return trimmed[0].uppercaseChar() + trimmed.substring(1).lowercase()
}