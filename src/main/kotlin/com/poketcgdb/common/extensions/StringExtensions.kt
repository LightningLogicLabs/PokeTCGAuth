package com.poketcgdb.common.extensions

fun String.isValidEmail(): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return matches(emailPattern)
}

fun String.hasUpperCaseLetters(): Boolean {
    var hasUpperCaseLetter = false
    val charArray = this.toCharArray()
    for (char in charArray) {
        if (char.isUpperCase()) {
            hasUpperCaseLetter = true
            break
        }
    }
    return hasUpperCaseLetter
}

fun String.hasLowerCaseLetters(): Boolean {
    var hasLowerCaseLetter = false
    val charArray = this.toCharArray()
    for (char in charArray) {
        if (char.isLowerCase()) {
            hasLowerCaseLetter = true
            break
        }
    }
    return hasLowerCaseLetter
}

fun String.hasNumbers(): Boolean {
    var hasNumber = false
    val charArray = this.toCharArray()
    for (char in charArray) {
        if (char.isDigit()) {
            hasNumber = true
            break
        }
    }
    return hasNumber
}

fun String.isCharacterCountWithinLimits(minCount: Int, maxCount: Int): Boolean {
    return this.count() in minCount..maxCount
}