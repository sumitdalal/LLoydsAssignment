package com.assignment.assignmentlloyds.di

import com.assignment.assignmentlloyds.BuildConfig
import com.assignment.assignmentlloyds.data.network.IApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun getRetroService(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun getService(retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }

}