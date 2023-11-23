package ar.edu.itba.tpHciMobile.data.network.datasources

import ar.edu.itba.tpHciMobile.data.network.api.ApiExercisesService
import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkCycleExercise
import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent

class ExercisesRemoteDataSource(
    private val exercisesService: ApiExercisesService
) : RemoteDataSource() {
    suspend fun getExercises(page: Int): NetworkPagedContent<NetworkExercise> {
        return handleApiResponse {
            exercisesService.getExercises(page)
        }
    }

    suspend fun getExercise(exerciseId: Int): NetworkExercise {
        return handleApiResponse {
            exercisesService.getExercise(exerciseId)
        }
    }

    suspend fun getExercisesByCycle(
        cycleId: Int,
        page: Int
    ): NetworkPagedContent<NetworkCycleExercise> {
        return handleApiResponse {
            exercisesService.getExercisesByCycle(cycleId, page)
        }
    }

    suspend fun getExerciseByCycle(cycleId: Int, exerciseId: Int): NetworkExercise {
        return handleApiResponse {
            exercisesService.getExerciseByCycle(cycleId, exerciseId)
        }
    }
}