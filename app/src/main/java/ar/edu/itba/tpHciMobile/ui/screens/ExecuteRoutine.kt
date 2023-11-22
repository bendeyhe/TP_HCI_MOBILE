package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
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
import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory
import java.lang.Math.ceil
import java.lang.Math.floor

@Composable
fun ExecuteRoutine(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int
) {
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        bottomBar = {
            if (routinesViewModel.uiState.isExecuting) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { routinesViewModel.previousExercise() },
                        modifier = Modifier.padding(16.dp),
                        enabled = !(routinesViewModel.uiState.currentCycleIndex == 0 && routinesViewModel.uiState.currentExerciseIndex == 0),
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

        }
    ) { contentPadding ->
        ExecuteRoutineContent(
            Modifier.padding(contentPadding),
            navController,
            routinesViewModel,
            routineId
        )
    }
}

@Composable
fun ExecuteRoutineContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int
) {
    var currentRating by remember { mutableStateOf(0) }
    if (routinesViewModel.uiState.isFetchingExecution) {
        Loading()
    } else {
        if (routinesViewModel.uiState.currentRoutine == null)
            routinesViewModel.startExecution(routineId)
        if (!routinesViewModel.uiState.isFetchingExecution) {
            if (routinesViewModel.uiState.isExecuting) {
                var timeLeft by remember { mutableStateOf(routinesViewModel.uiState.currentExercise?.duration) }
                var isPaused by remember { mutableStateOf(false) }

                if (timeLeft == null || timeLeft == 0)
                    timeLeft = 5
                Surface(
                    modifier = modifier.fillMaxSize()
                )
                {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = routinesViewModel.uiState.currentRoutine!!.name,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = routinesViewModel.uiState.currentRoutineCycle!!.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = routinesViewModel.uiState.currentExercise!!.exercise.name,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = routinesViewModel.uiState.currentExercise!!.exercise.detail!!,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                if (routinesViewModel.uiState.currentExercise?.repetitions!! > 0) {
                                    Text(
                                        text = stringResource(R.string.remaining_reps) + ":",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = "${routinesViewModel.uiState.currentExercise?.repetitions}",
                                        style = MaterialTheme.typography.displayLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    )
                                }
                                if (routinesViewModel.uiState.currentExercise?.duration!! > 0) {
                                    LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
                                        while (timeLeft!! > 0 && !isPaused) {
                                            delay(1000L)
                                            timeLeft = timeLeft!! - 1
                                        }
                                    }
                                    Text(
                                        text = stringResource(R.string.time_left) + ":",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = "$timeLeft",
                                        style = MaterialTheme.typography.displayLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        fontSize = 100.sp
                                    )
                                    Button(
                                        onClick = { isPaused = !isPaused },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(
                                                0xFF8EFE00
                                            )
                                        ),
                                    )
                                    {
                                        if (isPaused) {
                                            Icon(Icons.Filled.PlayArrow,
                                                "Play",
                                                tint = Color.Black,
                                                modifier = Modifier.size(35.dp)
                                            )
                                        } else {
                                            Icon(
                                                painterResource(R.drawable.pause),
                                                "Pause",
                                                tint = Color.Black,
                                                modifier = Modifier.size(35.dp)
                                            )
                                        }
                                    }
                                }
                                //TODO IF HAY SIGUIENTE EJERCICIO MOSTRARLO
                                Text(
                                    text = stringResource(R.string.next_exercise) + ":",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                                Text(
                                    text = "siguiente ejercicio", //TODO ACA PONER EL SIGUIENTE EJERCICIO
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                //CERRAR EL IF ACA
                            }
                        }
                        Spacer(modifier.weight(1f))
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.routine_finished),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                    Text(
                        text = stringResource(R.string.rank_routine),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    RatingBar(currentRating = currentRating, onRatingChanged = { currentRating = it })
                    Row() {
                        Button(
                            onClick = { navController.navigate("routine/${routinesViewModel.uiState.currentRoutine?.id}") },
                            modifier = Modifier.padding(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
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
                        Button(
                            onClick = {
                                var review: Review = Review()
                                review.review = ""
                                review.score = 3 // TODO CAMBIAR ESTO A LO QUE SEA QUE ELIJA
                                routinesViewModel.setReview(routinesViewModel.uiState.currentRoutine!!.id, review)
                                navController.navigate(Screen.RoutineDetails.route + "/${routinesViewModel.uiState.currentRoutine!!.id}")
                            },
                            modifier = Modifier.padding(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    0xFF8EFE00
                                )
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.send),
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
        } else
            Loading()
    }
}

@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    starsColor: Color = Color.Black
) {
    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star
                else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= currentRating) starsColor
                else Color.LightGray,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}