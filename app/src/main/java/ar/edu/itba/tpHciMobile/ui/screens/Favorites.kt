package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Favorites(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory())
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = routinesViewModel.uiState.isFetchingRoutine),
        onRefresh = { routinesViewModel.getFavoriteRoutines() }
    ) {
        if (routinesViewModel.uiState.isFetchingRoutine) {
            Loading()
        } else {
            if (routinesViewModel.uiState.favouriteRoutines == null || routinesViewModel.uiState.updatedFavs)
                routinesViewModel.getFavsRoutines()
            var routines = emptyList<Routine>()
            if (!routinesViewModel.uiState.isFetchingRoutine)
                routines = routinesViewModel.uiState.favouriteRoutines.orEmpty()

            Surface(
                color = Color(0xFFAEB0B2),
                modifier = modifier.fillMaxHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = stringResource(R.string.favorites),
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                    val list = routines
                    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                        items(items = list) { routine ->
                            Routine(
                                routine = routine,
                                routinesViewModel = routinesViewModel,
                                onItemClick = {
                                    println("1. ${routine.id}")
                                    navController.navigate(Screen.RoutineDetails.route + "/${routine.id}")
                                },
                                likeFunc = {
                                    routinesViewModel.toggleLike(routine)
                                },
                            )
                        }
                    }
                }
            }
        }
    }

}
