package ar.edu.itba.tpHciMobile.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.FavoriteBorder
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
fun Routines(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory()),
    userViewModel: UserViewModel = viewModel(factory = getViewModelFactory()),
) {
    if (routinesViewModel.uiState.isFetchingRoutine) {
        Loading()
    } else {
        if (routinesViewModel.uiState.routines == null) {
            routinesViewModel.getRoutinesOrderBy()
            if (routinesViewModel.uiState.isFetchingRoutine) {
                Loading()
            }
        }
        val routines = routinesViewModel.uiState.routines

        if (!routines.isNullOrEmpty()) {
            Surface(
                color = Color(0xFFAEB0B2)
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = routinesViewModel.uiState.isFetchingRoutine),
                    onRefresh = { routinesViewModel.getRoutinesOrderBy() }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.routines),
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        OrderByBtn(routinesViewModel = routinesViewModel)
                        val list = routines.orEmpty()
                        if (list.isNotEmpty()) {
                            LazyVerticalGrid(
                                state = rememberLazyGridState(),
                                columns = GridCells.Adaptive(minSize = 250.dp)
                            ) {
                                items(items = list) { routine ->
                                    Routine(
                                        routine = routine,
                                        routinesViewModel = routinesViewModel,
                                        onItemClick = {
                                            if (!routinesViewModel.uiState.isFetchingRoutine) {
                                                routinesViewModel.getRoutine(routine.id)
                                                navController.navigate(Screen.RoutineDetails.route)
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
        }
    }
}


@Composable
fun Routine(
    routine: Routine,
    routinesViewModel: RoutinesViewModel,
    onItemClick: () -> Unit,
    likeFunc: () -> Unit
) {
    var expanded = rememberSaveable { mutableStateOf(false) }
    var fav = rememberSaveable { mutableStateOf(false) }
    //luego para usar lo que hay en expanded se usa expanded.value
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onItemClick)
    ) {
        Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding),
            ) { //de esta manera se le da peso a la columna
                Text(
                    text = routine.name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = routine.detail ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
                )
                Text(
                    text = stringResource(R.string.difficulty) + " " + routine.difficulty,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
                Text(
                    text = stringResource(R.string.rating) + " " + routine.score,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
            }
            Column() {
                IconToggleButton(
                    checked = routine.liked,
                    onCheckedChange = { likeFunc(); },
                    modifier = Modifier.padding(end = 10.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        "Like",
                        modifier = Modifier.size(36.dp),
                        tint = animateColorAsState(
                            targetValue = if (routine.liked) Color.Red else Color.DarkGray,
                            label = ""
                        ).value,
                    )
                }
            }
        }
    }
}


@Composable
fun FavButton(routine: Routine, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        if (!routine.liked) {
            Icon(
                Icons.Filled.Favorite,
                "FavIcon",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Icon(
                Icons.Outlined.FavoriteBorder,
                "FavIcon",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderByBtn(
    modifier: Modifier = Modifier,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory())
) {
    val options = listOf(
        stringResource(R.string.order_by_date_desc),
        stringResource(R.string.order_by_date_asc),
        stringResource(R.string.order_by_rating_desc),
        stringResource(R.string.order_by_rating_asc),
        stringResource(R.string.order_by_diff_desc),
        stringResource(R.string.order_by_diff_asc),
        stringResource(R.string.order_by_cat_desc),
        stringResource(R.string.order_by_cat_asc),
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val msg = stringResource(R.string.order_by)
    var label by remember { mutableStateOf(msg) }
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        modifier = modifier
            .wrapContentSize()
            .padding(top = 8.dp),
        onClick = { selected = !selected },
        colors = FilterChipDefaults.filterChipColors(
            labelColor = Color.Black,
            containerColor = Color(0xFF8EFE00),
            selectedLabelColor = Color(0xFF8EFE00),
            selectedLeadingIconColor = Color.Black,
            selectedContainerColor = Color.Black
        ),
        border = null,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Icon(Icons.Filled.KeyboardArrowDown, "Share", tint = Color.Black)
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                DropdownMenu(
                    expanded = selected,
                    onDismissRequest = {
                        selected = false
                    }
                ) {
                    options.forEachIndexed { index, selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                routinesViewModel.getRoutinesOrderBy(index)
                                selectedOptionText = selectionOption
                                selected = false
                                label = selectedOptionText
                            }
                        )
                    }
                }
            }
        } else {
            null
        },
    )


}
