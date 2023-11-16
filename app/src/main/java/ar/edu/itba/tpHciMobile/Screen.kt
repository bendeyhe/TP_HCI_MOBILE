package ar.edu.itba.tpHciMobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val title: String, val icon: ImageVector, val route: String){
    object FirstScreen: Screen("First", Icons.Filled.Home, "first_screen")
    object SecondScreen: Screen("Second", Icons.Filled.Favorite, "second_screen")
    object ThirdScreen: Screen("Third", Icons.Filled.Face, "third_screen")
}