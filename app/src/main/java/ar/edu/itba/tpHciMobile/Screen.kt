package ar.edu.itba.tpHciMobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val title: String, val icon: ImageVector, val route: String){
    object FirstScreen: Screen("Favourites", Icons.Filled.Favorite, "first_screen")
    object Routines: Screen("Explore", Icons.Filled.Home, "second_screen")
    object ThirdScreen: Screen("Profile", Icons.Filled.Person, "third_screen")
}