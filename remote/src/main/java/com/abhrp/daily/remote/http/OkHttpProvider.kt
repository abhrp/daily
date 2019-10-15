package com.abhrp.daily.remote.http

import com.abhrp.daily.common.util.APIConfigProvider
import com.abhrp.daily.common.util.BuildTypeProvider
import com.abhrp.daily.remote.constants.RemoteConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpProvider @Inject constructor(private val buildTypeProvider: BuildTypeProvider, private val apiConfigProvider: APIConfigProvider) {

    val okhttp: OkHttpClient
        get() {
            return makeOkHttpClient(makeHttpLoggingInterceptor(buildTypeProvider.isDebug), apiKeyInterceptor(apiConfigProvider.apiKey))
        }


    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, apiKeyInterceptor: Interceptor): OkHttpClient {
       return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(RemoteConstants.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(RemoteConstants.TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    private fun apiKeyInterceptor(apiKey: String): Interceptor {
        return Interceptor { chain ->
            val oldRequest = chain.request()
            val oldUrl = oldRequest.url()

            val newUrl = oldUrl.newBuilder()
                .addQueryParameter(RemoteConstants.API_KEY_PARAM, apiKey).build()

            val requestBuilder = oldRequest.newBuilder().url(newUrl)
            val newRequest = requestBuilder.build()

            chain.proceed(newRequest)
        }
    }

    private fun makeHttpLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }
}