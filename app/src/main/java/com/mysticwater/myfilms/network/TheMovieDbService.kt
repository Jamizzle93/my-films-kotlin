package com.mysticwater.myfilms.network

import com.mysticwater.myfilms.BuildConfig
import com.mysticwater.myfilms.data.FilmResults
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    fun getUpcomingReleases(@Query("region") region: String,
                            @Query("release_date.gte") startDate: String,
                            @Query("release_date.lte") endDate: String,
                            @Query("with_release_type") releaseType: Int): Observable<FilmResults>

    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"

        fun getTmdbService(): TheMovieDbService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(TheMovieDbService::class.java)
        }
    }
}