package me.maxhub.hercules.domain

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Exercise(
    val id: String,
    val name: String,
    val description: String?,
    val setsCount: Int?,
    val repsCount: Int?,
    val duration: Duration?,
    val muscleGroups: Collection<String>?,
    val difficulty: String?,
    val exerciseType: String?,
)
