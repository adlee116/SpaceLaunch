package com.example.spacelaunch

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import spaceLaunch.data.entity.Links
import spaceLaunch.data.entity.Patch
import spaceLaunch.data.entity.SpaceListResponse
import spaceLaunch.domain.models.SpaceListResponseOrError
import spaceLaunch.domain.repositories.SpaceLaunchRepositoryInterface
import spaceLaunch.domain.useCase.GetSpaceLaunchItemsUseCase

class GetSpaceLaunchItemsUseCaseTest{

    private lateinit var getSpaceLaunchItemsUseCase: GetSpaceLaunchItemsUseCase
    private val spaceLaunchRepository = mock<SpaceLaunchRepositoryInterface>()

    private val failingResponse = SpaceListResponseOrError(error = "")
    private val emptyLaunchList = SpaceListResponseOrError(launches = mutableListOf())
    private val patch1 = Patch(small = "small1", large = "large1")
    private val patch2 = Patch(small = "small2", large = "large2")
    private val link1 = Links(patch = patch1)
    private val link2 = Links(patch = patch2)
    private val launch1 = SpaceListResponse(links = link1, success = true, name = "Launch 1", date_local = "1")
    private val launch2 = SpaceListResponse(links = link2, success = false, name = "Launch 2", date_local = "2")
    private val twoLaunchList = SpaceListResponseOrError(launches = mutableListOf(launch1, launch2))

    @Before
    fun setup() {
        getSpaceLaunchItemsUseCase = GetSpaceLaunchItemsUseCase(spaceLaunchRepository)
    }

    @Test
    fun should_return_empty_launch_items() {
        runBlocking {
            whenever(spaceLaunchRepository.getSpaceLaunchItems()) doReturn emptyLaunchList
            getSpaceLaunchItemsUseCase.invoke(this) {
                it.result(
                    onSuccess = { response ->
                        Assert.assertEquals(response, emptyLaunchList)
                    }
                )
            }
        }
    }

    @Test
    fun should_return_two_launch_items() {
        runBlocking {
            whenever(spaceLaunchRepository.getSpaceLaunchItems()) doReturn twoLaunchList
            getSpaceLaunchItemsUseCase.invoke(this) {
                it.result(
                    onSuccess = { response ->
                        Assert.assertEquals(response, twoLaunchList)
                    }
                )
            }
        }
    }

    @Test
    fun error_would_return_failure() {
        runBlocking {
            whenever(spaceLaunchRepository.getSpaceLaunchItems()) doReturn failingResponse
            getSpaceLaunchItemsUseCase.invoke(this) {
                it.result(
                    onFailure = { response ->
                        Assert.assertEquals(response, failingResponse)
                    }
                )
            }
        }
    }
}