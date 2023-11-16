import java.io.IOException
import java.nio.channels.NetworkChannel
import javax.sql.DataSource

abstract class RemoteDataSource {
    suspend fun <T :Any> handleApiResponse(execute: suspend () -> Response<T>): T {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                return body
            }
            response.errorBody()?.let {
                val gson = Gson()
                val error = gson.fromJson<NetworkError>(
                    it.string(),
                    object : TypeToken<NetworkError>() {}.type
                )
                throw DataSourceException(error.code, error.description, error.details)
            }
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error", null)
        } catch (e: DataSourceException) {
            throw e
        } catch (e: IOException) {
            throw DataSourceException(CONNECTION_ERROR_CODE, "Connection error", getDetailsFromException(e))
        } catch (e: Exception) {
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Unexpected error", getDetailsFromException(e))
        }
    }

    private fun getDetailsFromException(e: Exception): List<String> {
        return if(e.message != null) {
            listOf(e.message!!)
        } else {
            emptyList()
        }
    }

    companion object {
        const val UNEXPECTED_ERROR_CODE = 98
        const val CONNECTION_ERROR_CODE = 99
    }
}