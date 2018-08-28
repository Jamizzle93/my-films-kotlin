package com.mysticwater.myfilms.network

import com.mysticwater.myfilms.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

private const val QUERY_API_KEY = "api_key"
private const val QUERY_LANGUAGE = "language"
private const val QUERY_REGION = "region"

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request
                .url()
                .newBuilder()
                .addQueryParameter(QUERY_API_KEY, BuildConfig.ApiKey)
                .addQueryParameter(QUERY_LANGUAGE, Locale.getDefault().language)
                .addQueryParameter(QUERY_REGION, Locale.getDefault().country)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}