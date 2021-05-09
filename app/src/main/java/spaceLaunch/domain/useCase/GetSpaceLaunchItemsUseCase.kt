package spaceLaunch.domain.useCase

import spaceLaunch.domain.models.SpaceListResponseOrError
import spaceLaunch.core.EmptyParamsUseCase
import spaceLaunch.core.Result
import spaceLaunch.domain.repositories.SpaceLaunchRepositoryInterface

class GetSpaceLaunchItemsUseCase(
    private val spaceLaunchRepository: SpaceLaunchRepositoryInterface
) : EmptyParamsUseCase<SpaceListResponseOrError>() {
    override suspend fun run(): Result<SpaceListResponseOrError, Exception> {
        return try {
            Result.Success(spaceLaunchRepository.getSpaceLaunchItems())
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }
}