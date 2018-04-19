package com.mysticwater.myfilms.data.source.local

import android.arch.persistence.room.*
import com.mysticwater.myfilms.data.Film

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films WHERE release_date <= :date")
    fun getNowShowingFilms(date: Long): List<Film>

    @Query("SELECT * FROM Films WHERE release_date > :date")
    fun getUpcomingFilms(date: Long): List<Film>

    @Query("SELECT * FROM Films WHERE release_date >= :fromDate AND release_date <= :toDate")
    fun getFilms(fromDate: Long, toDate: Long): List<Film>

    @Query("SELECT * FROM Films WHERE favourite = 1")
    fun getFavouriteFilms(): List<Film>

    @Query("SELECT * FROM Films WHERE id = :filmId")
    fun getFilmById(filmId: Int): Film?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film)

    @Update()
    fun updateFilm(film: Film)

    @Query("DELETE FROM Films WHERE release_date < :date")
    fun deleteOldFilms(date: Long)

    @Query("DELETE FROM Films WHERE release_date >= :fromDate AND release_date <= :toDate AND favourite = 0")
    fun deleteFilms(fromDate: Long, toDate: Long)

    @Query("DELETE FROM Films WHERE favourite = 1 AND id = :filmId")
    fun deleteFavouriteFilm(filmId: Int): Int
}