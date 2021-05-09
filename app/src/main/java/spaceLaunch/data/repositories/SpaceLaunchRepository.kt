package spaceLaunch.data.repositories

import spaceLaunch.data.network.SpaceApi
import spaceLaunch.domain.repositories.SpaceLaunchRepositoryInterface
import spaceLaunch.domain.models.SpaceListResponseOrError
import spaceLaunch.data.mapper.SpaceResponseMapper

class SpaceLaunchRepository(
    private val spaceApi: SpaceApi,
    private val spaceResponseMapper: SpaceResponseMapper
): SpaceLaunchRepositoryInterface {

    override suspend fun getSpaceLaunchItems(): SpaceListResponseOrError {
        return spaceResponseMapper.map(
            spaceApi.getSpaceLaunches()
        )
    }

}