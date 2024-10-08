package ar.edu.itba.tpHciMobile.data.repository

import ar.edu.itba.tpHciMobile.data.model.CompleteCycle
import ar.edu.itba.tpHciMobile.data.network.datasources.RoutinesRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutinesCycleRepository(
    private val routinesRemoteDataSource: RoutinesRemoteDataSource
) {
    private var routinesCyclesMutex = Mutex()
    private var routineCycles: List<CompleteCycle> = emptyList()

    suspend fun getRoutineCycles(routineId: Int, refresh: Boolean = false): List<CompleteCycle> {

        return routinesRemoteDataSource.getCycles(routineId).content.map { it.asModel() }
    }

    suspend fun getRoutineCycle(routineId: Int, cycleId: Int): CompleteCycle {
        return routinesCyclesMutex.withLock {
            routinesRemoteDataSource.getCycle(routineId, cycleId).asModel()
        }
    }
}