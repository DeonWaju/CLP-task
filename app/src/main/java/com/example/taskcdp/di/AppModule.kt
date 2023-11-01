package com.example.taskcdp.di

import android.content.Context
import androidx.room.Room
import com.example.taskcdp.data.repo.AuthImpl
import com.example.taskcdp.BuildConfig
import com.example.taskcdp.util.SessionManager
import com.example.taskcdp.data.remote.ApiService
import com.example.taskcdp.data.local.AppDatabase
import com.example.taskcdp.domain.usecases.AuthRepository
import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.data.repo.SaveUserProfileImpl
import com.example.taskcdp.data.repo.UpdateProfileImageImpl
import com.example.taskcdp.data.repo.UserProfileImpl
import com.example.taskcdp.domain.usecases.SaveUserProfileRepository
import com.example.taskcdp.domain.usecases.UpdateProfileImageRepository
import com.example.taskcdp.domain.usecases.UserProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }


    @Provides
    fun provideUserDao(database: AppDatabase): UserProfileDao {
        return database.userProfileDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthUsecaseImpl(
        apiService: ApiService,
        sessionManager: SessionManager,
        ioDispatcher: CoroutineDispatcher
    ): AuthRepository = AuthImpl(apiService, sessionManager, ioDispatcher)

    @Provides
    @Singleton
    fun provideSaveUserProfileUsecaseImpl(
        userProfileDao: UserProfileDao,
        ioDispatcher: CoroutineDispatcher
    ): SaveUserProfileRepository = SaveUserProfileImpl( userProfileDao, ioDispatcher)

    @Provides
    @Singleton
    fun provideUserProfileUsecaseImpl(
        userProfileDao: UserProfileDao,
        ioDispatcher: CoroutineDispatcher
    ): UserProfileRepository = UserProfileImpl(userProfileDao, ioDispatcher)

    @Provides
    @Singleton
    fun provideUpdateProfileImageRepositoryImpl(
        userProfileDao: UserProfileDao,
        ioDispatcher: CoroutineDispatcher
    ): UpdateProfileImageRepository = UpdateProfileImageImpl(userProfileDao, ioDispatcher)

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)
}