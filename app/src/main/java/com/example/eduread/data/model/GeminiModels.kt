package com.example.eduread.data.model

// --- GEMINI REQUEST ---
data class GeminiPart(
    val text: String
)

data class GeminiContent(
    val role: String,
    val parts: List<GeminiPart>
)

data class GeminiRequest(
    val contents: List<GeminiContent>
)

// --- GEMINI RESPONSE ---
data class GeminiResponse(
    val candidates: List<GeminiCandidate>?
)

data class GeminiCandidate(
    val content: GeminiContent?
)
