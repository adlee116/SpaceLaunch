package spaceLaunch.di

import org.koin.dsl.module
import spaceLaunch.presentation.spaceLaunchList.SpaceLaunchListViewModel

val viewModelModule = module {
    single { SpaceLaunchListViewModel(get()) }
}