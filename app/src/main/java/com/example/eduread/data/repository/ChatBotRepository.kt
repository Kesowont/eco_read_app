package com.example.eduread.data.repository

import android.util.Log
import com.example.eduread.data.model.GeminiContent
import com.example.eduread.data.model.GeminiPart
import com.example.eduread.data.model.GeminiRequest
import com.example.eduread.data.model.GeminiResponse
import com.example.eduread.network.GeminiClient
import com.example.eduread.utils.GeminiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotRepository {

    private val TAG = "ChatBotRepo"

    fun enviarMensaje(mensaje: String, callback: (String?) -> Unit) {

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    role = "user",
                    parts = listOf(GeminiPart(text = mensaje))
                )
            )
        )

        GeminiClient.api.generateContent(
            model = GeminiConfig.MODEL,
            apiKey = GeminiConfig.API_KEY,
            request = request
        ).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(
                call: Call<GeminiResponse>,
                response: Response<GeminiResponse>
            ) {
                Log.d(TAG, "HTTP code: ${response.code()}")

                if (!response.isSuccessful) {
                    val errorText = try {
                        response.errorBody()?.string()
                    } catch (e: Exception) {
                        e.message
                    }
                    Log.e(TAG, "Error body: $errorText")
                    callback("HTTP ${response.code()}: $errorText")
                    return
                }

                val body = response.body()
                Log.d(TAG, "Body: $body")

                val texto = body
                    ?.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text

                if (texto == null) {
                    Log.e(TAG, "Respuesta sin texto")
                    callback("Respuesta vacía de Gemini")
                } else {
                    callback(texto)
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                Log.e(TAG, "Fallo en petición", t)
                callback("Fallo de red: ${t.message}")
            }
        })
    }
}