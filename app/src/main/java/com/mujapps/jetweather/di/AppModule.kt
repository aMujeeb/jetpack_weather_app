package com.mujapps.jetweather.di

import android.content.Context
import androidx.room.Room
import com.mujapps.jetweather.data.MeasurementUnitDao
import com.mujapps.jetweather.data.WeatherDao
import com.mujapps.jetweather.data.WeatherDataBase
import com.mujapps.jetweather.network.WeatherApi
import com.mujapps.jetweather.utils.WeatherConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDb: WeatherDataBase): WeatherDao = weatherDb.weatherDao()

    @Singleton
    @Provides
    fun provideMeasurementUnitDao(weatherDb: WeatherDataBase): MeasurementUnitDao =
        weatherDb.measurementUnitsDao()

    @Singleton
    @Provides
    fun provideAppDatBase(@ApplicationContext context: Context): WeatherDataBase =
        Room.databaseBuilder(context, WeatherDataBase::class.java, "weather_db")
            .fallbackToDestructiveMigration().build()
}