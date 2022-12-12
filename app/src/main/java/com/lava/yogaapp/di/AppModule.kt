package com.lava.yogaapp.di

import android.content.Context
import com.lava.yogaapp.BuildConfig
import com.lava.yogaapp.data.local.SharedPrefs
import com.lava.yogaapp.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.lava.yogaapp.data.remote.moshiFactories.MyStandardJsonAdapters
import com.lava.yogaapp.data.remote.service.UserApiService
import com.lava.yogaapp.util.NetworkConnectivity
import com.lava.yogaapp.util.NetworkManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return NetworkManager(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefs = SharedPrefs

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
            /** Uncomment below interceptor if you need to send headers in all requests like Authorization or API keys */
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "value")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            builder.addInterceptor(loggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL).client(builder.build())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(MyKotlinJsonAdapterFactory())
                        .add(MyStandardJsonAdapters.FACTORY)
                        .build()
                )
            ).build()
    }

    @Provides
    @Singleton
    fun providesPlayersService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

}