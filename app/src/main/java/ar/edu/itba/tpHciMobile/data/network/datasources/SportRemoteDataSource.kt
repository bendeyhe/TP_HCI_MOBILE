package ar.edu.itba.tpHciMobile.data.network.datasources

import ar.edu.itba.tpHciMobile.data.network.api.ApiSportService
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkPagedContent
import ar.edu.itba.tpHciMobile.data.network.model.util.NetworkSport


class SportRemoteDataSource(
    private val apiSportService: ApiSportService
) : RemoteDataSource() {

    suspend fun getSports(): NetworkPagedContent<NetworkSport> {
        return handleApiResponse {
            apiSportService.getSports()
        }
    }

    suspend fun getSport(sportId: Int): NetworkSport {
        return handleApiResponse {
            apiSportService.getSport(sportId)
        }
    }

    suspend fun addSport(sport: NetworkSport): NetworkSport {
        return handleApiResponse {
            apiSportService.addSport(sport)
        }
    }

    suspend fun modifySport(sport: NetworkSport): NetworkSport {
        return handleApiResponse {
            apiSportService.modifySport(sport.id!!, sport)
        }
    }

    suspend fun deleteSport(sportId: Int) {
        handleApiResponse {
            apiSportService.deleteSport(sportId)
        }
    }
}