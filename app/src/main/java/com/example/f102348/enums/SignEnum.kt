package com.example.f102348.enums

import androidx.annotation.StringRes
import com.example.f102348.R

enum class SignEnum(@param:StringRes val stringResource: Int) {
    ARIES(R.string.aries),
    TAURUS(R.string.taurus),
    GEMINI(R.string.gemini),
    CANCER(R.string.cancer),
    LEO(R.string.leo),
    VIRGO(R.string.virgo),
    LIBRA(R.string.libra),
    SCORPIO(R.string.scorpius),
    SAGITTARIUS(R.string.sagittarius),
    CAPRICORN(R.string.capricorn),
    AQUARIUS(R.string.aquarius),
    Pisces(R.string.pisces);
}