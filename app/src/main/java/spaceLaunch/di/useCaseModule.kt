package spaceLaunch.di

import org.koin.dsl.module
import spaceLaunch.domain.useCase.GetSpaceLaunchItemsUseCase

val useCaseModule = module {

    single { GetSpaceLaunchItemsUseCase(get()) }

}