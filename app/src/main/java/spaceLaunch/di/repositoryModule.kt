package spaceLaunch.di

import org.koin.dsl.module
import spaceLaunch.data.repositories.SpaceLaunchRepository
import spaceLaunch.domain.repositories.SpaceLaunchRepositoryInterface

val repositoryModule = module {

    single <SpaceLaunchRepositoryInterface>{ SpaceLaunchRepository(get(), get()) }

}