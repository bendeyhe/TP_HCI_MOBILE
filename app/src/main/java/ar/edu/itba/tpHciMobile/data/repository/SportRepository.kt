package ar.edu.itba.tpHciMobile.data.repository

//import SportRemoteDataSource
import ar.edu.itba.tpHciMobile.data.model.Sport
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/*
class SportRepository(
    private val remoteDataSource: SportRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe
    private val sportsMutex = Mutex()
    // Cache of the latest sports got from the network
    private var sports: List<Sport> = emptyList()

    suspend fun getSports(refresh: Boolean = false): List<Sport> {
        if (refresh || sports.isEmpty()) {
            val result = remoteDataSource.getSports()
        }
        return sports
    }

    suspend fun getSport(sportId: Int): Sport {
        return remoteDataSource.getSport(sportId).asModel()
    }

    suspend fun addSport(sport: Sport): Sport {
        val newSport = remoteDataSource.addSport(sport.asNetworkModel()).asModel()
        sportsMutex.withLock {
            this.sports = emptyList()
        }
        return newSport
    }

    suspend fun modifySport(sport: Sport): Sport {
        val updateSport = remoteDataSource.modifySport(sport.asNetworkModel()).asModel()
        sportsMutex.withLock {
            this.sports = emptyList()
        }
        return updateSport
    }
}
 */