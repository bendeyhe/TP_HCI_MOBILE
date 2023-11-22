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
fun Routines(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel,
    userViewModel: UserViewModel,
) {
    if (routinesViewModel.uiState.isFetchingRoutine) {
        Loading()
    } else {
        if (routinesViewModel.uiState.routines == null)
            routinesViewModel.getRoutinesOrderBy()

        var routines = emptyList<Routine>()
        if (!routinesViewModel.uiState.isFetchingRoutine)
            routines = routinesViewModel.uiState.routines.orEmpty()

        Surface(
            color = Color(0xFFAEB0B2),
            modifier = modifier.fillMaxHeight()
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
                                            val isLiked = routinesViewModel.uiState.routines?.find { it.id == routine.id }?.liked
                                            routinesViewModel.getRoutine(routine.id, isLiked?: false)
                                            navController.navigate(Screen.RoutineDetails.route + "/${routine.id}")
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

@Composable
fun Routine(
    routine: Routine,
    routinesViewModel: RoutinesViewModel,
    userViewModel: UserViewModel,
    onItemClick: () -> Unit,
    likeFunc: () -> Unit,
    color: Color = Color.White
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

    val notLoggedIn = stringResource(R.string.not_logged_in)

    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = {
                if (userViewModel.uiState.isAuthenticated) {
                    onItemClick()
                } else {
                    Toast
                        .makeText(
                            MyApplication.instance,
                            notLoggedIn,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            }),
        shape = MaterialTheme.shapes.medium,
        color = color
    ) {
        Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding),
            ) { //de esta manera se le da peso a la columna
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (color == Color.White) {
                        Text(
                            text = routine.name + " ",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = routine.name + " ",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column() {
                        if (userViewModel.uiState.isAuthenticated) {
                            IconToggleButton(
                                checked = routine.liked,
                                onCheckedChange = { likeFunc(); },
                            ) {
                                Icon(
                                    imageVector = if (routine.liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    "Like",
                                    modifier = Modifier.size(36.dp),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
                Row() {
                    ShowRatingBar(
                        rating = routine.score,
                        stars = 5,
                        starsColor = Color.Yellow
                    )
                    Text(
                        text = " (" + ((routine.score.toDouble()/2)) + ")",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                    )
                }
                Row {
                    if (color == Color.White) {
                        Text(
                            text = routine.detail ?: "",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )
                    } else {
                        Text(
                            text = routine.detail ?: "",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                        )
                    }
                }
                Row {
                    if (color == Color.White) {
                        Text(
                            text = stringResource(R.string.category) + ": " + routine.category.name,
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.category) + ": " + routine.category.name,
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = Color.LightGray
                    ) {
                        Text(
                            text = routine.difficulty.toString(),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderByBtn(
    modifier: Modifier = Modifier,
    routinesViewModel: RoutinesViewModel
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
                text = routinesViewModel.uiState.labelOrderBy,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            if (selected) {
                Icon(Icons.Filled.KeyboardArrowUp, "Share", tint = Color(0xFF8EFE00))
            } else {
                Icon(Icons.Filled.KeyboardArrowDown, "Share", tint = Color.Black)
            }
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
                                routinesViewModel.getRoutinesOrderBy(index, selectionOption)
                                selected = false
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

@Composable
fun ShowRatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = Math.floor((rating / 2).toDouble()).toInt()
    var unfilledStars = (stars - Math.ceil((rating / 2).toDouble())).toInt()
    val halfStar = (rating.toDouble()/2 - rating/2) > 0
    if(halfStar)
        unfilledStars -=  1
    Row(
        modifier = modifier,
        Arrangement.Center,
        Alignment.CenterVertically,
    ) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Outlined.Star, contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
        if (halfStar) {
            Icon(
                painterResource(R.drawable.star_half),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
        repeat(unfilledStars) {
            Icon(
                painterResource(R.drawable.star_border),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
fun ShowDifficulty(
    diff: String,
) {
    when (diff) {
        "rookie" -> {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color(0xFF8EFE00),
                modifier = Modifier.size(30.dp)
            )
        }

        "beginner" -> {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color(0xFF00FF00),
                modifier = Modifier.size(30.dp)
            )
        }

        "intermediate" -> {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color(0xFFFFFF00),
                modifier = Modifier.size(30.dp)
            )
        }

        "advanced" -> {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color(0xFFFF0000),
                modifier = Modifier.size(30.dp)
            )
        }

        "expert" -> {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color(0xFF8E0000),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
