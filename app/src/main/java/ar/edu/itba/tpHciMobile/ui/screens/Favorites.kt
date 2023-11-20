package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory

@Composable
fun Favorites(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory())
) {
    routinesViewModel.getRoutines()
    val routines = routinesViewModel.uiState.routines

    Surface(
        color = Color(0xFFAEB0B2)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            OrderByBtn(modifier.padding(end = 8.dp))
            LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                items(items = routines!!) { routine ->
                    Routine(routine, onItemClick = {
                        navController.navigate(Screen.RoutineDetails.route)
                    })
                }
            }
        }
    }
}




