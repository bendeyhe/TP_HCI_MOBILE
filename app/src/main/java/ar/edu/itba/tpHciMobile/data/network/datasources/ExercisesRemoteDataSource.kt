package ar.edu.itba.tpHciMobile.data.network.datasources

import ar.edu.itba.tpHciMobile.data.network.api.ApiExercisesService
import ar.edu.itba.tpHciMobile.data.network.model.exercises.NetworkExercise
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent

class ExercisesRemoteDataSource (
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
}