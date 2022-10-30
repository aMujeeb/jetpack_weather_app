package com.mujapps.jetweather.di

import com.mujapps.jetweather.network.WeatherApi
import com.mujapps.jetweather.utils.WeatherConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLoginInterceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(builder: OkHttpClient): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder)
            .build()
            .create(WeatherApi::class.java)
    }
}