package me.romanpopov.starwarsfilms.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.romanpopov.starwarsfilms.models.room.Planet
import me.romanpopov.starwarsfilms.ui.activities.ui.theme.StarwarsFilmsTheme

class WorldActivity : ComponentActivity() {

    companion object {
        const val WORLD_INTENT_TAG = "world"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarwarsFilmsTheme {
                WorldCard(worldToDisplay = intent.getSerializableExtra(WORLD_INTENT_TAG) as Planet)
            }
        }
    }
}

@Composable
private fun WorldCard(
    modifier: Modifier = Modifier,
    worldToDisplay: Planet,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 5.dp,
    ) {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = worldToDisplay.planetName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "Diameter: ${worldToDisplay.diameter}",
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "Climate: ${worldToDisplay.climate}",
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "Gravity: ${worldToDisplay.gravity}",
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "Terrain: ${worldToDisplay.terrain}",
                fontSize = 16.sp,
            )
            Text(
                text = "Population: ${worldToDisplay.population}",
                fontSize = 16.sp,
            )
        }
    }
}