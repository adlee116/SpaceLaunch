package com.example.spacelaunch

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import spaceLaunch.data.entity.Links
import spaceLaunch.data.entity.Patch
import spaceLaunch.data.entity.SpaceListResponse
import spaceLaunch.data.mapper.SpaceResponseMapper
import spaceLaunch.domain.models.SpaceListResponseOrError

class SpaceResponseMapperTest {

    private lateinit var spaceResponseMapper: SpaceResponseMapper
    private lateinit var response: Response<List<SpaceListResponse>>

    private val unsuccessfulResponse = SpaceListResponseOrError(
        error = "Response was unsuccessful"
    )
    private val emptyBodyResponse = SpaceListResponseOrError(
        error = "Response body was empty"
    )

    private val patch1 = Patch(small = "small1", large = "large1")
    private val patch2 = Patch(small = "small2", large = "large2")
    private val link1 = Links(patch = patch1)
    private val link2 = Links(patch = patch2)
    private val launch1 = SpaceListResponse(links = link1, success = true, name = "Launch 1", date_local = "1")
    private val launch2 = SpaceListResponse(links = link2, success = false, name = "Launch 2", date_local = "2")
    private val spaceListResponse = mutableListOf(launch1, launch2)

    @Before
    fun setUp() {
        spaceResponseMapper = SpaceResponseMapper()
        response = mock()
    }

    @Test
    fun map_of_unsuccessful_response_returns_error() {
        whenever(response.isSuccessful) doReturn false
        Assert.assertEquals(spaceResponseMapper.map(response), unsuccessfulResponse)
    }

    @Test
    fun success_with_body_returns_with_launch() {
        whenever(response.isSuccessful) doReturn true
        whenever(response.body()) doReturn spaceListResponse
        Assert.assertEquals(
            SpaceListResponseOrError(
                launches = spaceListResponse,
                error = null
            ), spaceResponseMapper.map(response)
        )
    }

    @Test
    fun success_with_no_body_returns_with_error() {
        whenever(response.isSuccessful) doReturn true
        whenever(response.body()) doReturn null
        Assert.assertEquals(emptyBodyResponse, spaceResponseMapper.map(response))
    }

}