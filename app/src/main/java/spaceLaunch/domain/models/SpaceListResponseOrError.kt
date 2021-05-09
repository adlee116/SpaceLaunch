package spaceLaunch.domain.models

import spaceLaunch.data.entity.SpaceListResponse

data class SpaceListResponseOrError (
    val launches: List<SpaceListResponse>? = null,
    val error: String? = null
)