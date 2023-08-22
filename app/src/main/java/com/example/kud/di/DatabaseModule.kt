package com.example.kud.di

import android.content.Context
import androidx.room.Room
import com.example.kud.data.db.MyDatabase
import com.example.kud.data.network.AuthInterceptor
import com.example.kud.data.network.HomeApi
import com.example.kud.data.network.UserApi
import com.example.kud.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "person_database"
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideDao(database: MyDatabase) = database.myDao()

    @Singleton
    @Provides
    fun checkOutDao(database: MyDatabase) = database.myCheckOut()


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build()
            .create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun homeApi(retrofitBuilder: Retrofit.Builder): HomeApi {
        return retrofitBuilder.build()
            .create(HomeApi::class.java)
    }
}