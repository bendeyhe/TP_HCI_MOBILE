package ar.edu.itba.tpHciMobile.data.network

import ar.edu.itba.tpHciMobile.data.network.api.ApiRoutinesService
import ar.edu.itba.tpHciMobile.data.network.model.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.Routines.NetworkCompleteCycle
import ar.edu.itba.tpHciMobile.data.network.model.Routines.NetworkRoutine

class RoutinesRemoteDataSource (
    private val routinesService: ApiRoutinesService
) : RemoteDataSource() {
    suspend fun getRoutines(page: Int) : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            routinesService.getRoutines(page)
        }
    }

    suspend fun getRoutinesOrderBy(page: Int, orderBy: String, direction: String) : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            routinesService.getRoutinesOrderBy(page, orderBy, direction)
        }
    }

    suspend fun getRoutine(routineId: Int): NetworkRoutine {
        return handleApiResponse {
            routinesService.getRoutine(routineId)
        }
    }
    suspend fun getCycles(routineId: Int, page: Int): NetworkPagedContent<NetworkCompleteCycle> {
        return handleApiResponse {
            routinesService.getCycles(routineId, page)
        }
    }
    suspend fun getCycle(routineId: Int, cycleId: Int): NetworkCompleteCycle {
        return handleApiResponse {
            routinesService.getCycle(routineId, cycleId)
        }
    }
}
