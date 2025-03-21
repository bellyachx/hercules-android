package me.maxhub.hercules.api.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.maxhub.hercules.api.HerculesService
import me.maxhub.hercules.api.model.ExerciseModel
import javax.inject.Inject

interface ExerciseDataSource {
    suspend fun getExercise(exerciseId: String): ExerciseModel
    suspend fun getExercises(): Collection<ExerciseModel>
}

class ExerciseDataSourceImpl @Inject constructor(private val service: HerculesService) : ExerciseDataSource {
    override suspend fun getExercise(exerciseId: String): ExerciseModel {
        return withContext(Dispatchers.IO) { service.getExercise(exerciseId)}
    }

    override suspend fun getExercises(): Collection<ExerciseModel> {
        return withContext(Dispatchers.IO) { service.getExercises() }
    }
}