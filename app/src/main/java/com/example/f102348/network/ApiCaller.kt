package com.example.f102348.network

import com.example.f102348.domain.model.Sign
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val API_KEY = "ac41497478mshaeb14a7b9f3bd59p1b5c6ejsn25b3ed7bd52b"
const val API_HOST = "sameer-kumar-aztro-v1.p.rapidapi.com"

object ApiCaller {

    fun getSignWithPredictions(signString: String, signRes: Int): Sign {
        val result =
            callAztroAPI("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$signString&day=today")
        return getResult(result!!, signRes)
    }

    private fun callAztroAPI(apiUrl: String): String? {
        var result: String? = ""
        val url: URL
        var connection: HttpURLConnection? = null
        try {
            url = URL(apiUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("x-rapidapi-host", API_HOST)

            connection.setRequestProperty("x-rapidapi-key", API_KEY)
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded")

            connection.requestMethod = "POST"
            val inputStream = connection.inputStream
            val reader = InputStreamReader(inputStream)

            var data: Int = reader.read()
            while (data != -1) {
                val current = data.toChar()
                result += current
                data = reader.read()
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getResult(result: String, signRes: Int): Sign {
        return Sign.serializableSignToSign(
            Json.decodeFromString(result),
            signRes
        )
    }
}