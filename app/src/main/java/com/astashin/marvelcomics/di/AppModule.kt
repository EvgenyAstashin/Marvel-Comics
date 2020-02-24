package com.astashin.marvelcomics.di

import com.astashin.marvelcomics.BuildConfig
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.utils.md5
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", BuildConfig.API_KEY)
                .addQueryParameter("ts", "1")
                .addQueryParameter("hash", md5("1${BuildConfig.API_PRIVATE_KEY}${BuildConfig.API_KEY}"))
                .build()

            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.API).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(Api::class.java)
    }
}