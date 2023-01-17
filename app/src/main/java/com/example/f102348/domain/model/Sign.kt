package com.example.f102348.domain.model

import com.example.f102348.network.SerializableSign

data class Sign(
    val sign: Int,
    val dateRange: String,
    val currentDate: String,
    val prediction: String,
    val compatibility: String,
    val mood: String,
    val color: String,
    val luckyNumber: Int,
    val luckyTime: String
) {
    companion object {
        fun serializableSignToSign(serializableSign: SerializableSign, signRes: Int): Sign {
            return serializableSign.let {
                Sign(
                    sign = signRes,
                    dateRange = it.date_range,
                    currentDate = it.current_date,
                    prediction = it.description,
                    compatibility = it.compatibility,
                    mood = it.mood,
                    color = it.color,
                    luckyNumber = it.lucky_number.toInt(),
                    luckyTime = it.lucky_time
                )
            }
        }
    }
}
