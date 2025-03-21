package me.maxhub.hercules.api

import me.maxhub.hercules.api.model.ExerciseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface HerculesService {
    @GET("api/v1/exercises/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: String): ExerciseModel

    @GET("api/v1/exercises")
    suspend fun getExercises(): Collection<ExerciseModel>

    @DELETE("api/v1/exercises/{exerciseId}")
    suspend fun deleteExercise(@Path("exerciseId") exerciseId: String)

    @PUT("api/v1/exercises/{exerciseId}")
    suspend fun updateExercise(@Path("exerciseId") exerciseId: String, @Body exercise: ExerciseModel): ExerciseModel
}