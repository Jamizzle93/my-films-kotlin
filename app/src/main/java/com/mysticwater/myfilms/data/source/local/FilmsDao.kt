package com.mysticwater.myfilms.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mysticwater.myfilms.data.Film

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films WHERE is_now_showing = 1")
    fun getNowShowingFilms(): List<Film>

    @Query("SELECT * FROM Films WHERE is_upcoming = 1")
    fun getUpcomingFilms(): List<Film>

    @Query("SELECT * FROM Films WHERE is_favourite = 1")
    fun getFavouriteFilms(): List<Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film)

    @Query("DELETE FROM Films WHERE is_now_showing = 1")
    fun deleteAllNowShowingFilms()

    @Query("DELETE FROM Films WHERE is_upcoming = 1")
    fun deleteAllNowUpcomingFilms()

    @Query("DELETE FROM Films WHERE is_favourite = 1 AND id = :filmId")
    fun deleteFavouriteFilm(filmId: Int): Int
}