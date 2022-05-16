package me.romanpopov.starwarsfilms.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import me.romanpopov.starwarsfilms.models.CharacterAndHomeworld
import me.romanpopov.starwarsfilms.models.FilmWithCharacters
import me.romanpopov.starwarsfilms.models.room.Planet
import me.romanpopov.starwarsfilms.utils.GsonCharacterAndHomeworldParser
import me.romanpopov.starwarsfilms.utils.GsonFilmAndCharactersParser
import me.romanpopov.starwarsfilms.utils.GsonPlanetParser
import me.romanpopov.starwarsfilms.utils.ProjectApplication.Companion.filmsDatabase
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val allFilms = MutableLiveData(mutableStateListOf<FilmWithCharacters>())
    private val filmsDao = filmsDatabase.filmsDao()
    private val charactersDao = filmsDatabase.charactersDao()
    private val planetsDao = filmsDatabase.planetsDao()

    private var gsonInstance: Gson

    init {
        gsonInstance = GsonBuilder()
            .registerTypeAdapter(
                FilmWithCharacters::class.java,
                GsonFilmAndCharactersParser {
                    return@GsonFilmAndCharactersParser runBlocking { getCharacter(it).character }
                }
            )
            .registerTypeAdapter(
                CharacterAndHomeworld::class.java,
                GsonCharacterAndHomeworldParser(
                    getId = {
                        return@GsonCharacterAndHomeworldParser it.split("people/")[1].removeSuffix("/")
                            .toInt()
                    },
                    getPlanetId = {
                        return@GsonCharacterAndHomeworldParser it.split("planets/")[1].removeSuffix(
                            "/"
                        ).toInt()
                    },
                    getPlanet = {
                        return@GsonCharacterAndHomeworldParser runBlocking { getPlanet(it) }
                    }
                )
            )
            .registerTypeAdapter(Planet::class.java, GsonPlanetParser {
                return@GsonPlanetParser it.split("planets/")[1].removeSuffix("/").toInt()
            })
            .create()
    }

    suspend fun getAllFilms(): SnapshotStateList<FilmWithCharacters> {
        if (filmsDao.getAll().isEmpty()) {
            val filmsResponse = HttpClient(CIO.create {
                requestTimeout = 0
            }).use {
                it.get("https://swapi.dev/api/films/")
            }
            val responseObject = JSONObject(filmsResponse.body<String>())
            val list = gsonInstance.fromJson<SnapshotStateList<FilmWithCharacters>>(
                responseObject["results"].toString(),
                object : TypeToken<SnapshotStateList<FilmWithCharacters>>() {}.type
            )
            list.forEach {
                filmsDao.insertAll(it.film)
            }
            return list
        } else {
            return filmsDao.getAll().toMutableStateList()
        }
    }

    private suspend fun getCharacter(link: String): CharacterAndHomeworld {
        val id = link.split("people/")[1].removeSuffix("/").toInt()
        if (charactersDao.checkContains(id) == 1) {
            return charactersDao.getById(id)
        } else {
            val characterResponse = HttpClient(CIO.create {
                requestTimeout = 0
            }).use {
                it.get(link)
            }
            val character = gsonInstance.fromJson(
                characterResponse.body<String>(),
                CharacterAndHomeworld::class.java
            )
            charactersDao.insertAll(character.character)
            return character
        }
    }

    private suspend fun getPlanet(link: String): Planet {
        val id = link.split("planets/")[1].removeSuffix("/").toInt()
        if (planetsDao.checkContains(id) == 1) {
            return planetsDao.getById(id)
        } else {
            val planetResponse = HttpClient(CIO.create {
                requestTimeout = 0
            }).use {
                it.get(link)
            }
            val planet = gsonInstance.fromJson(planetResponse.body<String>(), Planet::class.java)
            planetsDao.insertAll(planet)
            return planet
        }
    }

    fun getPlanetById(id: Int): Planet {
        return planetsDao.getById(id)
    }
}