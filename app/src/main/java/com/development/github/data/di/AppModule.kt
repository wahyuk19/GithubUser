package com.development.github.data.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.development.github.BuildConfig
import com.development.github.data.api.GithubApi
import com.development.github.data.db.GithubDao
import com.development.github.data.db.GithubRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGithubApi(@ApplicationContext context: Context): GithubApi{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()

        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .createShortcut(true)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient.build())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGithubDao(githubRoomDatabase: GithubRoomDatabase): GithubDao = githubRoomDatabase.githubDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context):GithubRoomDatabase = Room.databaseBuilder(
        context,GithubRoomDatabase::class.java,"github_db").fallbackToDestructiveMigration().build()

}