package ar.edu.itba.tpHciMobile.ui.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
@Composable
fun Favorites(modifier: Modifier = Modifier, navController: NavController){
    val names = listOf(
        "Rutina 1 -Fav",
        "Rutina 2 -Fav",
        "Rutina 3 -Fav",
        "Rutina 4 -Fav",
        "Rutina 5 -Fav",
        "Rutina 6 -Fav",
        "Rutina 7 -Fav",
        "Rutina 8 -Fav",
        "Rutina 9 - Fav ",
        "Rutina 10 -Fav"
    )
    val description = "Estas son las rutinas favoritas"
    val rating = "4.5"
    val difficulty = "Hard"

    Surface(
        color = Color(0xFFAEB0B2)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            OrderByBtn(modifier.padding(end = 8.dp))
            LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                items(items = names) { name ->
                    Routine(name, description, difficulty, rating, onItemClick = {
                        navController.navigate(Screen.RoutineDetails.route)
                    })
                }
            }
        }
    }
}




