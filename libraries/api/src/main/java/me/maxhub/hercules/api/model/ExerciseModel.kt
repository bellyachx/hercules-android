package me.maxhub.hercules.api.model

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class ExerciseModel(
    val id: String,
    val name: String,
    val description: String? = null,
    val setsCount: Int? = null,
    val repsCount: Int? = null,
    val duration: Duration? = null,
    val muscleGroups: Collection<String>? = null,
    val difficulty: String? = null,
    val exerciseType: String? = null,
)