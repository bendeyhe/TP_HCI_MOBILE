package ar.edu.itba.tpHciMobile.data.repository

import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.data.network.datasources.RemoteDataSource
import ar.edu.itba.tpHciMobile.data.network.datasources.RoutinesRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutinesRepository(
    private val routinesRemoteDataSource: RoutinesRemoteDataSource
) : RemoteDataSource() {
    private val routinesMutex = Mutex()

    suspend fun getRoutines(refresh: Boolean = false): List<Routine> {
        var page = 0
        var routines = emptyList<Routine>()
        if (refresh) {
            do {
                val result = routinesRemoteDataSource.getRoutines(page)
                routinesMutex.withLock {
                    routines = routines.plus(result.content.map { it.asModel() })
                }
                page++
            } while (!result.isLastPage)
        }
        return routinesMutex.withLock { routines }
    }

    suspend fun getFavoriteRoutines(refresh: Boolean = false): List<Routine> {
        var page = 0
        var favRoutines = emptyList<Routine>()
        if (refresh) {
            favRoutines = emptyList()
            do {
                val result = routinesRemoteDataSource.getFavRoutines(page)
                routinesMutex.withLock {
                    favRoutines = favRoutines.plus(result.content.map { it.asModel() })
                }
                page++
            } while (!result.isLastPage)
        }
        return routinesMutex.withLock { favRoutines }
    }

    suspend fun addFavoriteRoutine(routineId: Int) {
        routinesRemoteDataSource.addFavRoutine(routineId)
    }

    suspend fun removeFavoriteRoutine(routineId: Int) {
        routinesRemoteDataSource.removeFavRoutine(routineId)
    }

    suspend fun getRoutinesOrderBy(orderBy: String, direction: String): List<Routine> {
        var page = 0
        var routines = emptyList<Routine>()
        do {
            val result = routinesRemoteDataSource.getRoutinesOrderBy(page, orderBy, direction)
            routinesMutex.withLock {
                routines = routines.plus(result.content.map { it.asModel() })
            }
            page++
        } while (!result.isLastPage)

        return routinesMutex.withLock { routines }
    }

    suspend fun getRoutine(routineId: Int): Routine {
        return routinesMutex.withLock { routinesRemoteDataSource.getRoutine(routineId).asModel() }
    }

    suspend fun updateRoutine(routineId: Int, routine: Routine) {
        routinesRemoteDataSource.updateRoutine(routineId, routine.asNetworkUpdateModel())
    }

    suspend fun setReview(routineId: Int, review: Review) {
        routinesRemoteDataSource.setReview(routineId, review)
    }
}
