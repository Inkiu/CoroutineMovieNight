package com.example.data.di

import com.example.data.api.MovieApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideMovieApi(
        @Named("base_url") baseUrl: String,
        @Named("api_key") apiKey: String
    ): MovieApi {
        val interceptors = createInterceptors(apiKey)
        val retrofit = createRetrofit(interceptors, baseUrl)
        return retrofit.create(MovieApi::class.java)
    }

    private fun createRetrofit(
        interceptors: List<Interceptor>,
        baseUrl: String
    ): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }
        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    private fun createInterceptors(apiKey: String): List<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            return@Interceptor chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        return interceptors
    }
}