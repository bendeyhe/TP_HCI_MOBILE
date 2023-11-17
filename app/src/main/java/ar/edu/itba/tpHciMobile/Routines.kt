package ar.edu.itba.tpHciMobile

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Routines(modifier: Modifier = Modifier) {
    val names = listOf("Rutina 1", "Rutina 2", "Rutina 3", "Rutina 4", "Rutina 5", "Rutina 6", "Rutina 7", "Rutina 8", "Rutina 9", "Rutina 10")
    val description = "Esta es una rutina de prueba"
    Surface(
        color = Color(0xFFAEB0B2)
    ){
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = names){name ->
                Routine(name, description)
            }
        }
    }
}
@Composable
fun Routine(name: String, description: String, modifier: Modifier = Modifier) {
    var expanded = rememberSaveable { mutableStateOf(false) }
    var fav = rememberSaveable { mutableStateOf(false) }
    //luego para usar lo que hay en expanded se usa expanded.value
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Surface(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) { //de esta manera se le da peso a la columna
                Text(
                    text = "$name",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = "$description!",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
            }
            Column() {
                FavButton( fav = fav.value, onClick = { fav.value = !fav.value })
                    /*
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more))
            }

             */
            }
        }
    }
}

@Composable
fun FavButton(fav: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        if (fav) {
            Icon(Icons.Filled.Favorite, "FavIcon")
        }else {
            Icon(Icons.Outlined.FavoriteBorder, "FavIcon")
        }
    }
}


