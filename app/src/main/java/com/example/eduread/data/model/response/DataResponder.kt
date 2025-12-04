package com.example.eduread.data.model.response

data class DataResponder(
    val total: Int,
    val correct: Int,
    val estrella: Int,
    val detalle: Map<String, Boolean>
)
