package me.romanpopov.starwarsfilms.utils

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import me.romanpopov.starwarsfilms.models.CharacterAndHomeworld
import me.romanpopov.starwarsfilms.models.FilmWithCharacters
import me.romanpopov.starwarsfilms.models.room.Character
import me.romanpopov.starwarsfilms.models.room.Film
import me.romanpopov.starwarsfilms.models.room.Planet
import java.lang.reflect.Type
import java.text.SimpleDateFormat

class GsonCharactersArrayListParser(private val getCharacter: (link: String) -> CharacterAndHomeworld) :
    JsonDeserializer<List<CharacterAndHomeworld>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<CharacterAndHomeworld> {
        val characters = ArrayList<CharacterAndHomeworld>()
        json!!.asJsonArray.forEach {
            characters.add(getCharacter(it.asString))
        }
        return characters
    }
}

class GsonCharacterAndHomeworldParser(
    private val getId: (link: String) -> Int,
    private val getPlanetId: (link: String) -> Int,
    private val getPlanet: (link: String) -> Planet
) : JsonDeserializer<CharacterAndHomeworld> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CharacterAndHomeworld {
        val characterJsonObject = json!!.asJsonObject
        return CharacterAndHomeworld(
            Character(
                characterId = getId(characterJsonObject.get("url").asString),
                characterName = characterJsonObject.get("name").asString,
                gender = characterJsonObject.get("gender").asString,
                dateOfBirth = characterJsonObject.get("birth_year").asString,
                homeworldId = getPlanetId(characterJsonObject.get("homeworld").asString)
            ),
            homeworld = getPlanet(characterJsonObject.get("homeworld").asString)
        )
    }
}

class GsonPlanetParser(private val getId: (link: String) -> Int) : JsonDeserializer<Planet> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Planet {
        val planetJsonObject = json!!.asJsonObject
        return Planet(
            planetId = getId(planetJsonObject.get("url").asString),
            planetName = planetJsonObject.get("name").asString,
            diameter = planetJsonObject.get("diameter").asString,
            climate = planetJsonObject.get("climate").asString,
            gravity = planetJsonObject.get("gravity").asString,
            terrain = planetJsonObject.get("terrain").asString,
            population = planetJsonObject.get("population").asString,
        )
    }
}

class GsonFilmAndCharactersParser(private val getCharacter: (link: String) -> Character) :
    JsonDeserializer<FilmWithCharacters> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FilmWithCharacters {
        val filmJsonObject = json!!.asJsonObject
        return FilmWithCharacters(
            Film(
                episodeId = filmJsonObject.get("episode_id").asInt,
                title = filmJsonObject.get("title").asString,
                openingText = filmJsonObject.get("opening_crawl").asString,
                director = filmJsonObject.get("director").asString,
                producer = filmJsonObject.get("producer").asString,
                releaseDate = SimpleDateFormat("yyyy-MM-dd").parse(filmJsonObject.get("release_date").asString)!!
            ),
            (filmJsonObject["characters"] as JsonArray).map {
                getCharacter(it.asString)
            }
        )
    }
}