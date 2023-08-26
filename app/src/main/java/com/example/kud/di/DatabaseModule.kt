package com.example.kud.di

import android.content.Context
import androidx.room.Room
import com.example.kud.data.db.MyDatabase
import com.example.kud.data.network.*
import com.example.kud.utils.Constants
import com.example.kud.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
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
    fun providesProfileAPI(retrofitBuilder: Retrofit.Builder): ProfileApi {
        return retrofitBuilder.build()
            .create(ProfileApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthAPI(retrofitBuilder: Retrofit.Builder): AuthApi {
        return retrofitBuilder.build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesTransactionAPI(retrofitBuilder: Retrofit.Builder): TransactionApi {
        return retrofitBuilder.build()
            .create(TransactionApi::class.java)
    }

    @Singleton
    @Provides
    fun homeApi(retrofitBuilder: Retrofit.Builder): HomeApi {
        return retrofitBuilder.build()
            .create(HomeApi::class.java)
    }
}