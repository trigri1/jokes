package com.test.jokes.di.module


import android.content.Context
import com.test.data.jokes.client.ApiClient
import com.test.data.rx.AppSchedulerProvider
import com.test.data.rx.SchedulerProvider
import com.test.jokes.JokesApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://api.icndb.com/"

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(context: JokesApp): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppApiClient(client: OkHttpClient): ApiClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(60, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(60, TimeUnit.SECONDS)// Write timeout
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideSchedulers(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}