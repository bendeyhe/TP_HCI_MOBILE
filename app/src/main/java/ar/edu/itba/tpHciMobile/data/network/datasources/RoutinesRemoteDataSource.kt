package ar.edu.itba.tpHciMobile.data.network.datasources

import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.data.network.api.ApiRoutinesService
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkCompleteCycle
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkReview
import ar.edu.itba.tpHciMobile.data.network.model.routines.NetworkRoutine

class RoutinesRemoteDataSource(
    private val routinesService: ApiRoutinesService
) : RemoteDataSource() {
    suspend fun getRoutines(page: Int): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            routinesService.getRoutines(page)
        }
    }

    suspend fun getFavRoutines(page: Int): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            routinesService.getFavRoutines(page)
        }
    }

    suspend fun addFavRoutine(routineId: Int) {
        handleApiResponse {
            routinesService.addFavRoutine(routineId)
        }
    }

    suspend fun removeFavRoutine(routineId: Int) {
        handleApiResponse {
            routinesService.removeFavRoutine(routineId)
        }
    }

    suspend fun getRoutinesOrderBy(
        page: Int,
        orderBy: String,
        direction: String
    ): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            routinesService.getRoutinesOrderBy(page, orderBy, direction)
        }
    }

    suspend fun getRoutine(routineId: Int): NetworkRoutine {
        return handleApiResponse {
            routinesService.getRoutine(routineId)
        }
    }

    suspend fun getCycles(routineId: Int): NetworkPagedContent<NetworkCompleteCycle> {
        return handleApiResponse {
            routinesService.getCycles(routineId)
        }
    }

    suspend fun getCycle(routineId: Int, cycleId: Int): NetworkCompleteCycle {
        return handleApiResponse {
            routinesService.getCycle(routineId, cycleId)
        }
    }

    suspend fun setReview(routineId: Int, review: Review) {
        return handleApiResponse {
            routinesService.setReview(routineId, review)
        }
    }
}
