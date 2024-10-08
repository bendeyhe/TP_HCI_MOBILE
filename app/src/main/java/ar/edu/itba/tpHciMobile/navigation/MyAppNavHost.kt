package ar.edu.itba.tpHciMobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import ar.edu.itba.tpHciMobile.ui.screens.Login
import ar.edu.itba.tpHciMobile.ui.screens.Routines
import ar.edu.itba.tpHciMobile.ui.screens.Favorites
import ar.edu.itba.tpHciMobile.ui.screens.RoutineDetails
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.MainViewModel
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.UserViewModel
import ar.edu.itba.tpHciMobile.ui.screens.ExecuteRoutine
import ar.edu.itba.tpHciMobile.util.getViewModelFactory


@Composable
fun MyAppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    routinesViewModel: RoutinesViewModel,
    mainViewModel: MainViewModel,
    modifier: Modifier
) {
    val uri = "http://toobig.com"
    val secureUri = "https://toobig.com"
    NavHost(
        navController = navController,
        startDestination = Screen.Routines.route,
        modifier = modifier,

        ) {
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
        composable(Screen.Routines.route) {
            Routines(
                navController = navController,
                routinesViewModel = routinesViewModel,
                userViewModel = userViewModel
            )
        }
        composable(Screen.LoginScreen.route) {
            Login(navController = navController, userViewModel = userViewModel)
        }
        composable(Screen.Favorites.route) {
            Favorites(
                navController = navController,
                routinesViewModel = routinesViewModel,
                userViewModel = userViewModel
            )
        }
        composable(
            route = Screen.ExecuteRoutine.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            ExecuteRoutine(
                navController = navController,
                routinesViewModel = routinesViewModel,
                routineId = it.arguments?.getInt("id")!!
            )
        }
        composable(
            route = Screen.RoutineDetails.route + "/{id}",
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/routine/{id}" },
                navDeepLink { uriPattern = "$secureUri/routine/{id}" }
            ),
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            RoutineDetails(
                navController = navController,
                routinesViewModel = routinesViewModel,
                userViewModel = userViewModel,
                routineId = it.arguments?.getInt("id")!!
            )
        }
    }
}