package ar.edu.itba.tpHciMobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import ar.edu.itba.tpHciMobile.screens.HomeScreen
import ar.edu.itba.tpHciMobile.screens.OtherScreen
import ar.edu.itba.tpHciMobile.screens.ThirdScreen
import ar.edu.itba.tpHciMobile.ui.main.Login
import ar.edu.itba.tpHciMobile.ui.main.Routines
import ar.edu.itba.tpHciMobile.ui.main.Favorites
import ar.edu.itba.tpHciMobile.ui.main.Screen


@Composable
fun MyAppNavHost(navController: NavHostController, modifier: Modifier) {
    val uri = "http://www.example.com"
    val secureUri="https://www.example.com"
    NavHost(
        navController = navController,
        startDestination = Screen.Routines.route,
        modifier = modifier
    ){
      /*  composable("home"){
            HomeScreen(onNavigateToOtherScreen = {id -> navController.navigate("other/$id") })
        }
        composable(
            "other/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/other?id={id}"},
            navDeepLink{uriPattern = "$secureUri/other?id={id}" }
            )
        ){
            route -> OtherScreen(route.arguments?.getInt("id"))
        }

       */
        composable(Screen.Routines.route){
            Routines(navController = navController)
        }
        composable(Screen.ThirdScreen.route){
            ThirdScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route){
            Login(navController = navController)
        }
        composable(Screen.Favorites.route){
            Favorites(navController = navController)
        }
    }
}