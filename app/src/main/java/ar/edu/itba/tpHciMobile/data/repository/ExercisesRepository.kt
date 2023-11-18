package ar.edu.itba.tpHciMobile.data.repository

import ar.edu.itba.tpHciMobile.data.model.Exercises
import ar.edu.itba.tpHciMobile.data.network.datasources.ExercisesRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ExercisesRepository (
    private val exercisesRemoteDataSource: ExercisesRemoteDataSource
){
    private val exercisesMutex = Mutex()
    private var exercises: List<Exercises> = emptyList()

    suspend fun getExercises(refresh: Boolean = false): List<Exercises> {
        var page = 0
        if (refresh || exercises.isEmpty()) {
            this.exercises = emptyList()
            do {
                val result = exercisesRemoteDataSource.getExercises(page)
                exercisesMutex.withLock {
                    this.exercises = this.exercises.plus(result.content.map { it.asModel() })
                }
                page++
            } while (!result.isLastPage)
        }
        return exercisesMutex.withLock { this.exercises }
    }

    suspend fun getExercise(exerciseId: Int): Exercises {
        return exercisesMutex.withLock { exercisesRemoteDataSource.getExercise(exerciseId).asModel() }
    }
}