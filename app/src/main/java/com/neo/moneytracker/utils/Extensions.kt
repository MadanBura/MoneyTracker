package com.neo.moneytracker.utils


fun String.toSentenceCase(): String {
    if (this.isEmpty()) return this
    return this[0].uppercaseChar() + this.substring(1).lowercase()
}