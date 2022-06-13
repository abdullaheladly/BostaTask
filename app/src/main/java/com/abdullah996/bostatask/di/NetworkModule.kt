package com.abdullah996.bostatask.di

import android.content.Context
import com.abdullah996.bostatask.network.Apis
import com.abdullah996.bostatask.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @Singleton
    @Provides
    fun provideInterceptor():HttpLoggingInterceptor{
        val interceptor=HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun headersInterceptor(@ApplicationContext context: Context):Interceptor= Interceptor { chain->
        var request=chain.request()
       try {

        request= request.newBuilder()
            //.header("Accept", "application/json")
            .header("Accept", "application/json")
            .build()
       }catch (ex:Exception){
           ex.printStackTrace()
       }
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        interceptor: HttpLoggingInterceptor,
        hInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(hInterceptor)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50,TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Apis {
        return retrofit.create(
            Apis::class.java
        )
    }

}