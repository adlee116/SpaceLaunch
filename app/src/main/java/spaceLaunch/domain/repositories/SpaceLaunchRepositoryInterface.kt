package spaceLaunch.domain.repositories

import spaceLaunch.domain.models.SpaceListResponseOrError

interface SpaceLaunchRepositoryInterface {

    suspend fun getSpaceLaunchItems(): SpaceListResponseOrError
}