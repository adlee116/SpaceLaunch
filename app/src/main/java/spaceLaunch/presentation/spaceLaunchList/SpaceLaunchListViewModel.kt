package spaceLaunch.presentation.spaceLaunchList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import spaceLaunch.domain.useCase.GetSpaceLaunchItemsUseCase
import spaceLaunch.domain.models.Launches
import spaceLaunch.domain.models.SpaceListResponseOrError

class SpaceLaunchListViewModel(private val getSpaceLaunchItemsUseCase: GetSpaceLaunchItemsUseCase) : ViewModel() {

    private val _spaceLaunchItems = MutableLiveData<List<Launches>>()
    val spaceLaunchItems: LiveData<List<Launches>> get() = _spaceLaunchItems

    private val _failedRequest = MutableLiveData<String>()
    val failedRequest: LiveData<String> get() = _failedRequest

    fun getSpaceLaunchItems() {
        getSpaceLaunchItemsUseCase.invoke(viewModelScope) { result ->
            result.result(
                onSuccess = { response ->
                    response.error?.let {
                        _failedRequest.value = it
                    } ?: run {
                        _spaceLaunchItems.value = spaceResponseToLaunches(response)
                    }
                },
                onFailure = {
                    _failedRequest.value = it.message
                }
            )
        }
    }

    @VisibleForTesting
    fun spaceResponseToLaunches(spaceListResponse: SpaceListResponseOrError): List<Launches> {
        val launchList = mutableListOf<Launches>()
        spaceListResponse.launches?.forEach {
            launchList.add(
                Launches(
                    it.links.patch.small,
                    it.name,
                    it.date_local,
                    it.success
                )
            )
        }
        return launchList
    }

}