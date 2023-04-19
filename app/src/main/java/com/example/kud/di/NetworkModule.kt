package com.example.kud.di

import com.example.kud.data.network.AuthInterceptor
import com.example.kud.data.network.HomeApi
import com.example.kud.data.network.UserApi
import com.example.kud.utils.Constans
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object NetworkModule {



//    @Singleton
//    @Provides
//    fun fakeApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): FakeApi {
//        return retrofitBuilder.client(okHttpClient).build().create(FakeApi::class.java)
//    }

}