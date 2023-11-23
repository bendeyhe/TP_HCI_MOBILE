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
fun RoutinesTablet(
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
            .clickable(onClick =
            {
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
    )
    {
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
                        text = " (" + ((routine.score.toDouble() / 2)) + ")",
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
                            text = ShowDifficulty(routine.difficulty ?: "").toString(),
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
