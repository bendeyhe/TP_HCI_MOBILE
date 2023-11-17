package ar.edu.itba.tpHciMobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

@Composable
fun MyAppNavHost(navController: NavHostController, modifier: Modifier) {
    val uri = "http://www.example.com"
    val secureUri="https://www.example.com"
    NavHost(
        navController = navController,
        startDestination = Screen.Routines.route,
        modifier = modifier
    ){
        composable("home"){
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
        composable(Screen.Routines.route){
            Routines()
        }
        composable(Screen.ThirdScreen.route){
            ThirdScreen()
        }
    }
}