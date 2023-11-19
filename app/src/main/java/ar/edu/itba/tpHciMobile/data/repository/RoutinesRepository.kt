package ar.edu.itba.tpHciMobile.data.repository

import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.data.network.datasources.RoutinesRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutinesRepository(
    private val routinesRemoteDataSource: RoutinesRemoteDataSource
) {
    private val routinesMutex = Mutex()
    private var routines: List<Routine> = emptyList()

    suspend fun getRoutines(refresh: Boolean = false): List<Routine> {
        var page = 0
        if (refresh || routines.isEmpty()) {
            this.routines = emptyList()
            do {
                val result = routinesRemoteDataSource.getRoutines(page)
                routinesMutex.withLock {
                    this.routines = this.routines.plus(result.content.map { it.asModel() })
                }
                page++
            } while (!result.isLastPage)
        }
        return routinesMutex.withLock { this.routines }
    }

    suspend fun getRoutinesOrderBy(orderBy: String, direction: String): List<Routine> {
        var page = 0
        this.routines = emptyList()
        do {
            val result = routinesRemoteDataSource.getRoutinesOrderBy(page, orderBy, direction)
            routinesMutex.withLock {
                this.routines = this.routines.plus(result.content.map { it.asModel() })
            }
            page++
        } while (!result.isLastPage)

        return routinesMutex.withLock { this.routines }
    }

    suspend fun getRoutine(routineId: Int): Routine {
        return routinesMutex.withLock { routinesRemoteDataSource.getRoutine(routineId).asModel() }
    }

}
