package com.example.zenmind_ai.data.model

data class SessionConfig(
    val theme: String = "Deep Relaxation",
    val duration: Int = 5,
    val voiceStyle: String = "Calm",
    val relaxationType: String = "Guided Visualization",
    val backgroundAmbience: String = "Nature Sounds"
)
