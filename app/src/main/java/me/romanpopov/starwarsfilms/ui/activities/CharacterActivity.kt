@file:OptIn(ExperimentalMaterialApi::class)

package me.romanpopov.starwarsfilms.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.romanpopov.starwarsfilms.models.FilmWithCharacters
import me.romanpopov.starwarsfilms.models.room.Character
import me.romanpopov.starwarsfilms.ui.activities.WorldActivity.Companion.WORLD_INTENT_TAG
import me.romanpopov.starwarsfilms.ui.activities.ui.theme.StarwarsFilmsTheme
import me.romanpopov.starwarsfilms.viewmodels.MainViewModel

class CharacterActivity : ComponentActivity() {

    companion object {
        const val FILM_INTENT_TAG = "film"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel by viewModels<MainViewModel>()

        setContent {
            val film = intent.getSerializableExtra(FILM_INTENT_TAG) as FilmWithCharacters

            StarwarsFilmsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = film.film.title)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onBackPressed()
                                }) {
                                    Icon(Icons.Filled.ArrowBack, "Back")
                                }
                            },
                            elevation = 12.dp
                        )
                    }
                ) {
                    CharactersList(
                        charactersToShow = film.characters,
                        onCharacterCardClick = {
                            startActivity(
                                Intent(this, WorldActivity::class.java).putExtra(
                                    WORLD_INTENT_TAG,
                                    mainViewModel.getPlanetById(it.homeworldId)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CharactersList(
    modifier: Modifier = Modifier,
    charactersToShow: List<Character>,
    onCharacterCardClick: (Character) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(charactersToShow) { index, character ->
            CharacterCard(
                character = character,
                onCharacterCardClick = { onCharacterCardClick(character) })

            if (index != charactersToShow.lastIndex) {
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

@Composable
private fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onCharacterCardClick: (Character) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 5.dp,
        onClick = { onCharacterCardClick(character) },
    ) {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = character.characterName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "Gender: ${character.gender}",
                fontSize = 16.sp,
            )
            Text(
                text = "Birth: ${character.dateOfBirth}",
                fontSize = 16.sp,
            )
        }
    }
}