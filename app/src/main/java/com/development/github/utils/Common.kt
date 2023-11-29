package com.development.github.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertDate(dateTime: String): String {
    val instant = Instant.parse(dateTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
    return localDateTime.format(formatter)
}

fun convertDateRepos(dateTime: String): String {
    val instant = Instant.parse(dateTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return localDateTime.format(formatter)
}