package me.romanpopov.starwarsfilms.utils

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import me.romanpopov.starwarsfilms.database.FilmsDatabase

@HiltAndroidApp
class ProjectApplication : Application() {

    companion object {
        private const val FILMS_DATABASE_NAME = "films"
        lateinit var filmsDatabase: FilmsDatabase
    }

    override fun onCreate() {
        super.onCreate()
        filmsDatabase =
            Room.databaseBuilder(applicationContext, FilmsDatabase::class.java, FILMS_DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
    }
}