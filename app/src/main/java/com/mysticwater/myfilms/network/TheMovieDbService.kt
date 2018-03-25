package com.mysticwater.myfilms.network

import com.mysticwater.myfilms.BuildConfig
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.FilmResults
import com.mysticwater.myfilms.data.source.converters.CalendarAdapter
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("movie/now_playing?api_key=" + BuildConfig.API_KEY)
    fun getNowPlaying(@Query("language") language: String,
                      @Query("region") region:String): Observable<FilmResults>

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    fun getUpcomingReleases(@Query("region") region: String,
                            @Query("release_date.gte") startDate: String,
                            @Query("release_date.lte") endDate: String,
                            @Query("with_release_type") releaseType: Int): Observable<FilmResults>

    @GET("movie/{id}?api_key=" + BuildConfig.API_KEY)
    fun getFilm(@Path("id") id: Int): Observable<Film>

    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"

        val moshi = Moshi.Builder().add(CalendarAdapter()).build()

        fun getTmdbService(): TheMovieDbService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(TheMovieDbService::class.java)
        }
    }
}