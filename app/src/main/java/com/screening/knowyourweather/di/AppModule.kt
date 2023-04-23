package com.screening.knowyourweather.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.screening.knowyourweather.AppController
import com.screening.knowyourweather.BuildConfig
import com.screening.knowyourweather.network.ApiServiceGenerator
import com.screening.knowyourweather.network.AuthInterceptor
import com.screening.knowyourweather.weather.AppApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    internal fun providesGson(): Gson {
        return GsonBuilder()/*.serializeNulls()*/.setLenient().create()
    }

    @Provides
    @Singleton
    internal fun providesContext(): Context {
        return AppController.getInstance().applicationContext
    }

    @Provides
    @Singleton
    internal fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    internal fun provideApi(
        gson: Gson,
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): AppApi {
        return ApiServiceGenerator.generate(
            BuildConfig.BASE_URL,
            AppApi::class.java,
            gson,
            loggingInterceptor,
            authInterceptor
        )

    }

}