package com.mysticwater.myfilms.network

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.FilmResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TheMovieDbService {

    @GET("movie/now_playing")
    fun getNowPlaying(): Observable<FilmResults>

    @GET("discover/movie")
    fun getUpcomingReleases(@Query("release_date.gte") startDate: String,
                            @Query("release_date.lte") endDate: String,
                            @Query("with_release_type") releaseType: Int): Observable<FilmResults>

    @GET("movie/{id}")
    fun getFilm(@Path("id") id: Long): Observable<Film>
}