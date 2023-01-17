package com.example.f102348.network

import kotlinx.serialization.Serializable

@Serializable
data class SerializableSign(
    val date_range: String,
    val current_date: String,
    val description: String,
    val compatibility: String,
    val mood: String,
    val color: String,
    val lucky_number: String,
    val lucky_time: String
)
