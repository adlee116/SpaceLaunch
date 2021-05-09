package spaceLaunch.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import spaceLaunch.data.network.OkHttpClientBuilder
import spaceLaunch.data.network.SpaceApi

val networkModule = module {

    single<Gson> { GsonBuilder().create() }
    single { provideOkHttpClient(androidContext()) }
    single { provideRetrofit(get(), get()) }
    single<SpaceApi> { get<Retrofit>().create() }

}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    // Would usually create a network environment here to get string.
    return Retrofit.Builder().baseUrl("https://api.spacexdata.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

fun provideOkHttpClient(androidContext: Context): OkHttpClient {
    return OkHttpClientBuilder.createOkHttp(androidContext)
}