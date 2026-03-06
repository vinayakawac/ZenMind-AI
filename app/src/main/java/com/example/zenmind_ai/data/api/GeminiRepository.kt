package com.example.zenmind_ai.data.api

import com.example.zenmind_ai.BuildConfig
import com.example.zenmind_ai.data.model.GeminiContent
import com.example.zenmind_ai.data.model.GeminiPart
import com.example.zenmind_ai.data.model.GeminiRequest
import com.example.zenmind_ai.data.model.SessionConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiRepository @Inject constructor(
    private val apiService: GeminiApiService
) {

    suspend fun generateMeditation(config: SessionConfig): Result<String> {
        return try {
            val prompt = buildPrompt(config)
            val request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(GeminiPart(prompt))
                    )
                )
            )

            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank()) {
                return Result.failure(Exception("Gemini API key is not configured. Add GEMINI_API_KEY to local.properties."))
            }

            val response = apiService.generateContent(
                apiKey = apiKey,
                request = request
            )

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                return Result.failure(Exception("API error ${response.code()}: $errorBody"))
            }

            val body = response.body()
            val text = body?.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text

            if (text != null) {
                Result.success(text)
            } else {
                Result.failure(Exception("Empty response from Gemini API"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun buildPrompt(config: SessionConfig): String {
        return """
            Create a calming guided meditation script.

            Theme: ${config.theme}
            Duration: ${config.duration} minutes
            Tone: ${config.voiceStyle}
            Relaxation type: ${config.relaxationType}

            Structure:
            - Introduction and settling in
            - Breathing guidance
            - Visualization related to the theme
            - Body relaxation
            - Closing affirmation

            Write in second person, speaking directly to the listener.
            Use a warm, gentle, and reassuring tone throughout.
            Do not include stage directions or section labels — write it as a continuous flowing script.
        """.trimIndent()
    }
}
