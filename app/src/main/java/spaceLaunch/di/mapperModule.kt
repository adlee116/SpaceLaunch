package spaceLaunch.di

import org.koin.dsl.module
import spaceLaunch.data.mapper.SpaceResponseMapper

val mapperModule = module {

    single { SpaceResponseMapper() }

}