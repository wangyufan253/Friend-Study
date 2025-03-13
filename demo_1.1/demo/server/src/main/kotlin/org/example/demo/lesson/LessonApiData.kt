package org.example.demo.lesson

import kotlinx.serialization.Serializable

@Serializable
data class LessonResponse(
    val id: Int,
    val name: String,
    val description: String,
    val teacher: String
)

@Serializable
data class LessonRequest(
    val name: String,
    val description: String,
    val teacher: String
)

@Serializable
data class LessonsRequest(
    val name: String,
)

@Serializable
data class JoinLessonRequest(
    val student: String,
    val lesson: Int
)