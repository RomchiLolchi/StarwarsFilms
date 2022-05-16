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
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.romanpopov.starwarsfilms.models.FilmWithCharacters
import me.romanpopov.starwarsfilms.ui.CentralInfiniteProgressbar
import me.romanpopov.starwarsfilms.ui.activities.ui.theme.StarwarsFilmsTheme
import me.romanpopov.starwarsfilms.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FilmsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel by viewModels<MainViewModel>()

        setContent {
            StarwarsFilmsTheme {
                if (mainViewModel.allFilms.observeAsState().value.isNullOrEmpty()) {
                    CentralInfiniteProgressbar()
                } else if (mainViewModel.allFilms.observeAsState().value?.isNotEmpty() == true) {
                    FilmsList(
                        filmsToDisplay = mainViewModel.allFilms.observeAsState().value!!.apply { sort() },
                        onFilmCardClick = {
                            startActivity(
                                Intent(this, CharacterActivity::class.java).putExtra(
                                    CharacterActivity.FILM_INTENT_TAG,
                                    it
                                )
                            )
                        }
                    )
                }
            }
        }

        mainViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    mainViewModel.allFilms.postValue(mainViewModel.getAllFilms())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}

@Composable
private fun FilmsList(
    modifier: Modifier = Modifier,
    filmsToDisplay: List<FilmWithCharacters>,
    onFilmCardClick: (FilmWithCharacters) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(filmsToDisplay) { index, film ->
            FilmCard(
                film = film,
                onCardClick = { onFilmCardClick(film) }
            )

            if (index != filmsToDisplay.lastIndex) {
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun FilmCard(
    modifier: Modifier = Modifier,
    film: FilmWithCharacters,
    onCardClick: (FilmWithCharacters) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 5.dp,
        onClick = { onCardClick(film) },
    ) {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = film.film.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides 0.7F)) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "Director: ${film.film.director};\nProducer(s): ${film.film.producer}",
                    fontSize = 16.sp,
                )
            }
            Text(
                text = SimpleDateFormat("yyyy", Locale.getDefault()).format(film.film.releaseDate)
                    .toString(),
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Red,
            )
        }
    }
}