package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.UserViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Favorites(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel,
    userViewModel: UserViewModel
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
                Row() {
                    Text(
                        text = stringResource(R.string.favorites),
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                val list = routines
                if (list.isNotEmpty()) {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = routinesViewModel.uiState.isFetchingRoutine),
                        onRefresh = { routinesViewModel.getFavoriteRoutines() }
                    ) {
                        LazyVerticalGrid(
                            state = rememberLazyGridState(),
                            columns = GridCells.Adaptive(minSize = 300.dp)
                        ) {
                            items(items = list) { routine ->
                                Routine(
                                    routine = routine,
                                    routinesViewModel = routinesViewModel,
                                    userViewModel = userViewModel,
                                    onItemClick = {
                                        if (!routinesViewModel.uiState.isFetchingRoutine) {
                                            routinesViewModel.getRoutine(routine.id)
                                            navController.navigate(Screen.RoutineDetails.route + "/${routine.id}")
                                        }
                                    },
                                    likeFunc = {
                                        routinesViewModel.toggleLike(routine)
                                    },
                                )
                            }
                        }
                    }
                } else {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = routinesViewModel.uiState.isFetchingRoutine),
                        onRefresh = { routinesViewModel.getFavoriteRoutines() }
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.91f)
                                .padding(bottom = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            item {
                                Text(
                                    text = stringResource(R.string.no_favorites),
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
