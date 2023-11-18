package ar.edu.itba.tpHciMobile.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ar.edu.itba.tpHciMobile.R

sealed class Screen (val title: String, val icon: ImageVector, val route: String){
    object FirstScreen: Screen(MyApplication.instance.getString(R.string.favorites), Icons.Filled.Favorite, "first_screen")
    object Routines: Screen(MyApplication.instance.getString(R.string.routines), Icons.Filled.Home, "second_screen")
    object ThirdScreen: Screen(MyApplication.instance.getString(R.string.profile), Icons.Filled.Person, "third_screen")

    object LoginScreen: Screen(MyApplication.instance.getString(R.string.login), Icons.Filled.Person, "login_screen")
}