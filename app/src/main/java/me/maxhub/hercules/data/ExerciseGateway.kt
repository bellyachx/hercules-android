package me.maxhub.hercules.data

import me.maxhub.hercules.api.data.ExerciseDataSource
import me.maxhub.hercules.api.model.ExerciseModel
import me.maxhub.hercules.domain.Exercise
import java.util.stream.Collectors
import javax.inject.Inject

interface ExerciseGateway {
    suspend fun getExercise(exerciseId: String): Exercise
    suspend fun getExercises(): Collection<Exercise>
}

class ExerciseGatewayImpl @Inject constructor(
    private val exerciseDataSource: ExerciseDataSource,
) : ExerciseGateway {
    override suspend fun getExercise(exerciseId: String): Exercise {
        return exerciseDataSource.getExercise(exerciseId).toDomainModel()
    }

    override suspend fun getExercises(): Collection<Exercise> {
        return exerciseDataSource.getExercises().stream()
            .map { it.toDomainModel() }
            .collect(Collectors.toList())
    }
}

private fun ExerciseModel.toDomainModel(): Exercise {
    return Exercise(
        id,
        name,
        description,
        setsCount,
        repsCount,
        duration,
        muscleGroups,
        difficulty,
        exerciseType
    );
}