package spaceLaunch.data.mapper

import spaceLaunch.data.entity.SpaceListResponse
import retrofit2.Response
import spaceLaunch.domain.models.SpaceListResponseOrError

class SpaceResponseMapper() {

    // I would actually build a response handler for the else and the ?: in these cases.
    fun map(response: Response<List<SpaceListResponse>>): SpaceListResponseOrError {
        return when {
            response.isSuccessful -> mapResponseBody(response)
            else -> SpaceListResponseOrError(error = "Response was unsuccessful")
        }
    }

    private fun mapResponseBody(response: Response<List<SpaceListResponse>>) : SpaceListResponseOrError {
        response.body()?.let {
            return SpaceListResponseOrError(launches = it)
        } ?: run {
            return SpaceListResponseOrError(error = "Response body was empty")
        }
    }
}