package me.maxhub.hercules.api

import me.maxhub.hercules.api.model.ExerciseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface HerculesService {
    @GET("api/v1/exercises/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: String): ExerciseModel

    @GET("api/v1/exercises")
    suspend fun getExercises(): Collection<ExerciseModel>
}