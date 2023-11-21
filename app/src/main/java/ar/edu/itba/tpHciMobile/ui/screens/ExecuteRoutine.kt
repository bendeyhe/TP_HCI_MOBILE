package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory
import java.lang.Math.ceil
import java.lang.Math.floor

@Composable
fun ExecuteRoutine(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory()),
    cycleNumber: Int = 0,
    exerciseNumber: Int = 0,
    routineId: Int
) {
    if (!routinesViewModel.uiState.isFetchingR) {
        if (!routinesViewModel.uiState.cycleDetailList.isEmpty()) {
            var timeLeft by remember { mutableStateOf(routinesViewModel.uiState.cycleDetailList[cycleNumber].exercises[exerciseNumber].duration) }
            var isPaused by remember { mutableStateOf(false) }


            if (timeLeft == null || timeLeft == 0)
                timeLeft = 5
            /*if (exerciseNumber == routinesViewModel.uiState.cycleDetailList[cycleNumber].exercises.size - 1) {
            if (cycleNumber == routinesViewModel.uiState.cycleDetailList.size - 1) {*/
            Surface(
                modifier = modifier.fillMaxSize()
            )
            //if la rutina es con tiempo:
            {
                LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
                    while (timeLeft!! > 0 && !isPaused) {
                        delay(1000L)
                        timeLeft = timeLeft!! - 1
                    }
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = routinesViewModel.uiState.cycleDetailList[cycleNumber].exercises[exerciseNumber].exercise.name,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(R.string.time_left) + ":",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "$timeLeft",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                    Button(
                        onClick = { isPaused = !isPaused },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
                    )
                    {
                        /*Text(
                        text = if (isPaused) "Resume " else "Pause ",
                        color = Color.Black
                    )*/
                        if (isPaused) {
                            Icon(Icons.Filled.PlayArrow, "Play", tint = Color.Black)
                        } else {
                            Icon(painterResource(R.drawable.pause), "Pause", tint = Color.Black)
                        }
                    }
                }

                //else
                /*
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ejercicio",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.remaining_reps) + ":",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "10",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )

            }
            */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //si es el primer ejercicio no mostrar el boton BACK
                    Button(
                        onClick = {/*TODO*/ },
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text(
                            text = stringResource(R.string.back),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Button(
                        onClick = {/*TODO*/ },
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00))
                    ) {
                        Text(
                            text = stringResource(R.string.next),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

            }
        } else {
            routinesViewModel.getRoutine(routineId)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //si es el primer ejercicio no mostrar el boton BACK
            Button(
                onClick = {/*TODO*/ },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(
                    text = stringResource(R.string.back),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Button(
                onClick = { routinesViewModel.nextExercise() },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00))
            ) {
                Text(
                    text = stringResource(R.string.next),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
    if (routinesViewModel.uiState.isExecuting) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.routine_finished),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(R.string.rank_routine),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            RatingBar(rating = 2.5)
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00))
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(
        modifier = modifier,
        Arrangement.Center,
        Alignment.CenterVertically,
    ) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Outlined.Star, contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(65.dp)
            )
        }
        if (halfStar) {
            Icon(
                painterResource(R.drawable.star_half),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(65.dp)
            )
        }
        repeat(unfilledStars) {
            Icon(
                painterResource(R.drawable.star_border),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(65.dp)
            )
        }
    }
}
