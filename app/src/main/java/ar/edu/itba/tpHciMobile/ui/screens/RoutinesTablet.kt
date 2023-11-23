package ar.edu.itba.tpHciMobile.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.data.model.Routine
import ar.edu.itba.tpHciMobile.ui.main.MyApplication
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.UserViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RoutinesTablet(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel,
    userViewModel: UserViewModel
) {
    var selectedRoutine by remember { mutableStateOf<Routine?>(null) }
    if (routinesViewModel.uiState.isFetchingRoutine) {
        Loading()
    } else {
        if (routinesViewModel.uiState.routines == null)
            routinesViewModel.getRoutinesOrderBy()

        var routines = emptyList<Routine>()
        if (!routinesViewModel.uiState.isFetchingRoutine)
            routines = routinesViewModel.uiState.routines.orEmpty()
        Row {
            Surface(
                color = Color(0xFFAEB0B2),
                modifier = modifier.fillMaxWidth(0.3f).fillMaxHeight()
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = routinesViewModel.uiState.isFetchingRoutine),
                    onRefresh = { routinesViewModel.getRoutinesOrderBy() }
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row() {
                            Text(
                                text = stringResource(R.string.routines),
                                textAlign = TextAlign.Left,
                                fontSize = 30.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            OrderByBtn(
                                routinesViewModel = routinesViewModel,
                                modifier = modifier.padding(end = 8.dp)
                            )
                        }
                        val list = routines
                        if (list.isNotEmpty()) {
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
                                                val isLiked =
                                                    routinesViewModel.uiState.routines?.find { it.id == routine.id }?.liked
                                                routinesViewModel.getRoutine(
                                                    routine.id,
                                                    isLiked ?: false
                                                )
                                                selectedRoutine = routine
                                            }
                                        },
                                        likeFunc = {
                                            routinesViewModel.toggleLike(routine)
                                        },
                                    )
                                }
                            }
                        } else {
                            Text(text = stringResource(R.string.no_routines))
                        }
                    }
                }
            }
            Surface(
                color = Color(0xFFAEB0B2),
                modifier = modifier.fillMaxWidth().fillMaxHeight()
            ){
                if(selectedRoutine != null){
                    RoutineDetails(
                        routineId = selectedRoutine!!.id,
                        routinesViewModel = routinesViewModel,
                        navController = navController,
                        userViewModel = userViewModel,
                    )
                }
                else{
                    Text(
                        text = stringResource(R.string.no_routine_selected),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(50.dp)
                    )
                }
            }
        }
    }
}
