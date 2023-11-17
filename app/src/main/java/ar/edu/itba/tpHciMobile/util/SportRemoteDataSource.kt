package ar.edu.itba.tpHciMobile.util

import ar.edu.itba.tpHciMobile.data.RemoteDataSource
import ar.edu.itba.tpHciMobile.data.api.ApiSportService

class SportRemoteDataSource(
    private val ApiSportService: ApiSportService
) : RemoteDataSource() {

    /*
    suspend fun getSports(): NetworkPagedContent:<NetworkSport> {
        return handleApiResponse {
            ApiSportService.getSports()
        }
    }

    suspend fun getSport(sportId: Int): NetwirkSport {
        return handleApiResponse {
            ApiSportService.getSport(sportId)
        }
    }

    suspend fun addSport(sport: NetworkSport): NetworkSport {
        return handleApiResponse {
            ApiSportService.addSport(sport)
        }
    }
     */
}