package com.example.spacelaunch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import spaceLaunch.data.entity.Links
import spaceLaunch.data.entity.Patch
import spaceLaunch.data.entity.SpaceListResponse
import spaceLaunch.domain.models.Launches
import spaceLaunch.domain.models.SpaceListResponseOrError
import spaceLaunch.domain.repositories.SpaceLaunchRepositoryInterface
import spaceLaunch.domain.useCase.GetSpaceLaunchItemsUseCase
import spaceLaunch.presentation.spaceLaunchList.SpaceLaunchListViewModel

@ExperimentalCoroutinesApi
class SpaceLaunchListViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = TestCoroutineRule()

    private lateinit var viewModel: SpaceLaunchListViewModel
    private val mockRepo = mock<SpaceLaunchRepositoryInterface>()
    private val useCase = GetSpaceLaunchItemsUseCase(mockRepo)
    private val failingResponse = SpaceListResponseOrError(error = "")
    private val patch1 = Patch(small = "small1", large = "large1")
    private val patch2 = Patch(small = "small2", large = "large2")
    private val link1 = Links(patch = patch1)
    private val link2 = Links(patch = patch2)
    private val launch1 = SpaceListResponse(links = link1, success = true, name = "Launch 1", date_local = "1")
    private val launch2 = SpaceListResponse(links = link2, success = false, name = "Launch 2", date_local = "2")
    private val twoLaunchList = SpaceListResponseOrError(launches = mutableListOf(launch1, launch2))
    private val launch1AsLaunchItem = Launches(launch1.links.patch.small, launch1.name, launch1.date_local, launch1.success)
    private val launch2AsLaunchItem = Launches(launch2.links.patch.small, launch2.name, launch2.date_local, launch2.success)
    private val spaceListLaunches: List<Launches> = mutableListOf(launch1AsLaunchItem, launch2AsLaunchItem)


    @Before
    fun setup() {
        viewModel = SpaceLaunchListViewModel(useCase)
        useCase.enableTesting = true
    }

    @Test
    fun `convert response to launches`() {
        Assert.assertEquals(viewModel.spaceResponseToLaunches(twoLaunchList), spaceListLaunches)
    }

    @Test
    fun `successful items returned`() {
        runBlocking {
            whenever(mockRepo.getSpaceLaunchItems()) doReturn twoLaunchList
            viewModel.getSpaceLaunchItems()
            viewModel.spaceLaunchItems.observeForever {
                Assert.assertEquals(it, spaceListLaunches)
            }
        }
    }

    @Test
    fun `return when error`() {
        runBlocking {
            whenever(mockRepo.getSpaceLaunchItems()) doReturn failingResponse
            viewModel.getSpaceLaunchItems()
            viewModel.failedRequest.observeForever {
                Assert.assertEquals(it, failingResponse.error)
            }
        }
    }

}