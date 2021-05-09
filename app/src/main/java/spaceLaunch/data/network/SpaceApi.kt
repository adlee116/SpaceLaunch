package spaceLaunch.data.network

import spaceLaunch.data.entity.SpaceListResponse
import retrofit2.Response
import retrofit2.http.GET

interface SpaceApi {

    @GET("v4/launches")
    suspend fun getSpaceLaunches(
    ): Response<List<SpaceListResponse>>
}