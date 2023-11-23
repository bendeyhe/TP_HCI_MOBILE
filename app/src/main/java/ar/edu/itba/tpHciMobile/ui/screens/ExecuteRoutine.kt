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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.tpHciMobile.data.model.Review
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory

var view by mutableIntStateOf(0)
var routineFinished by mutableStateOf(false)

@Composable
fun ExecuteRoutine(
    modifier: Modifier = Modifier,
    navController: NavController,
    routinesViewModel: RoutinesViewModel,
    routineId: Int
) {
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = { if (!routineFinished) TabScreen() },
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
    routinesViewModel: RoutinesViewModel,
    routineId: Int
) {
    var currentRating by remember { mutableStateOf(0) }
    if (routinesViewModel.uiState.isFetchingExecution) {
        Loading()
    } else {
        if (routinesViewModel.uiState.finishedExecution || (!routinesViewModel.uiState.isFetchingExecution && (routinesViewModel.uiState.currentRoutine == null || routinesViewModel.uiState.currentRoutineCycle == null || routinesViewModel.uiState.currentExercise == null)))
            routinesViewModel.startExecution(routineId)
        if (!routinesViewModel.uiState.isFetchingExecution) {
            if (routinesViewModel.uiState.isExecuting) {
                var timeLeft by remember { mutableStateOf(routinesViewModel.uiState.currentExercise?.duration) }
                var isPaused by remember { mutableStateOf(false) }
                if (routinesViewModel.uiState.hasChangedExercise) {
                    routinesViewModel.changeHasChangedExercise()
                    timeLeft = routinesViewModel.uiState.currentExercise?.duration
                }
                routineFinished = false

                if (view == 0) {
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
                                text = routinesViewModel.uiState.currentRoutineCycle!!.name+ "   " + (routinesViewModel.uiState.currentRepetitionIndex + 1) + "/" + routinesViewModel.uiState.cycleDetailList[routinesViewModel.uiState.currentCycleIndex].cycle!!.repetitions,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = routinesViewModel.uiState.currentExercise!!.exercise.name + "   " + (routinesViewModel.uiState.currentExerciseIndex+1) + "/" + routinesViewModel.uiState.cycleDetailList[routinesViewModel.uiState.currentCycleIndex].exercises.size,
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
                                                if (timeLeft == 0)
                                                    if (!routinesViewModel.uiState.isFetchingRoutine) {
                                                        routinesViewModel.nextExercise()
                                                        timeLeft =
                                                            routinesViewModel.uiState.currentExercise?.duration
                                                    }
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
                                                Icon(
                                                    Icons.Filled.PlayArrow,
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
                                    if (routinesViewModel.uiState.nextExercise != null) {
                                        Text(
                                            text = stringResource(R.string.next_exercise) + ":",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(top = 16.dp)
                                        )
                                        Text(
                                            text = routinesViewModel.uiState!!.nextExercise!!.exercise.name,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                            Spacer(modifier.weight(1f))
                        }
                    }
                }
                else {
                    //TODO ESTO NO ES UN TODO, SOLO QUERIA QUE SE ME MARQUE LA SEGUNDA VIEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW

                    Surface(modifier = modifier.fillMaxSize()) {
                        Column {
                            for (cycle in routinesViewModel.uiState.cycleDetailList) {
                                if (cycle == routinesViewModel.uiState.cycleDetailList[routinesViewModel.uiState.currentCycleIndex]) {
                                    Row (Modifier.background(Color.LightGray)){
                                        Text(
                                            text = cycle.cycle!!.name,
                                            style = MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "" + (routinesViewModel.uiState.currentRepetitionIndex + 1) + "/" + routinesViewModel.uiState.cycleDetailList[routinesViewModel.uiState.currentCycleIndex].cycle!!.repetitions,
                                            style = MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                    }
                                }
                                else
                                    Text(
                                        text = cycle.cycle!!.name,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Normal
                                        ),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                Divider()
                                for (exercise in cycle.exercises) {
                                    if (exercise == routinesViewModel.uiState.currentExercise) {
                                        Surface(modifier = Modifier.fillMaxWidth()) {
                                            Column(
                                                Modifier.background(Color(0xFF8EFE00)),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                Text(
                                                    text = exercise.exercise.name,
                                                    style = MaterialTheme.typography.headlineMedium.copy(
                                                        fontWeight = FontWeight.ExtraBold,
                                                    ),
                                                )
                                                //Spacer(modifier = Modifier.weight(1f))
                                                if (exercise.repetitions!! > 0) {
                                                    var txt =
                                                        if (exercise.repetitions!! > 1) "${exercise.repetitions} " + stringResource(
                                                            R.string.repetitions
                                                        ) else "${exercise.repetitions} " + stringResource(
                                                            R.string.repetition
                                                        )
                                                    if (exercise.duration!! > 0)
                                                        txt += " " + stringResource(R.string.`in`) + " "
                                                    Text(
                                                        text = txt,
                                                        style = MaterialTheme.typography.headlineMedium.copy(
                                                            fontWeight = FontWeight.ExtraBold,
                                                        ),
                                                    )
                                                }
                                                if (exercise.duration!! > 0) {
                                                    Text(
                                                        text = if (timeLeft!! > 1) "$timeLeft " + stringResource(
                                                            R.string.seconds
                                                        ) else "$timeLeft " + stringResource(R.string.second),
                                                        style = MaterialTheme.typography.headlineMedium.copy(
                                                            fontWeight = FontWeight.ExtraBold,
                                                        ),
                                                    )
                                                    LaunchedEffect(
                                                        key1 = timeLeft,
                                                        key2 = isPaused
                                                    ) {
                                                        while (timeLeft!! > 0 && !isPaused) {
                                                            delay(1000L)
                                                            timeLeft = timeLeft!! - 1
                                                            if (timeLeft == 0)
                                                                if (!routinesViewModel.uiState.isFetchingRoutine) {
                                                                    routinesViewModel.nextExercise()
                                                                    timeLeft =
                                                                        routinesViewModel.uiState.currentExercise?.duration
                                                                }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    } else {
                                        Row() {
                                            Text(
                                                text = exercise.exercise.name,
                                                style = MaterialTheme.typography.bodyLarge.copy(
                                                    fontWeight = FontWeight.Normal
                                                ),
                                                modifier = Modifier.padding(start = 8.dp)
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            if (exercise.repetitions!! > 0) {
                                                var txt = if(exercise.repetitions!! > 1) "${exercise.repetitions} " + stringResource(R.string.repetitions) else "${exercise.repetitions} " + stringResource(R.string.repetition)
                                                if(exercise.duration!! > 0)
                                                    txt += " " + stringResource(R.string.`in`) + " "
                                                Text(
                                                    text = txt,
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Normal,
                                                    ),
                                                )
                                            }
                                            if (exercise.duration!! > 0) {
                                                Text(
                                                    text = if(exercise.duration!! > 1) "${exercise.duration} " + stringResource(R.string.seconds) else "${exercise.duration} " + stringResource(R.string.second),
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Normal,
                                                    ),
                                                )
                                            }
                                            Text(" ")
                                        }
                                    }
                                    Divider()
                                }
                            }
                        }
                    }
                }
            } else {
                routineFinished = true
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.routine_finished) + " " + routinesViewModel.uiState.currentRoutine!!.name + "!",
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
                    RatingBar(
                        currentRating = currentRating,
                        onRatingChanged = { currentRating = it })
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        Button(
                            onClick = {
                                routinesViewModel.finishExecution()
                                navController.navigate(Screen.Routines.route)
                            },
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
                                if (!routinesViewModel.uiState.isFetchingRoutine) {
                                    var review: Review = Review(currentRating * 2)
                                    routinesViewModel.setReview(
                                        routinesViewModel.uiState.currentRoutine!!.id, review
                                    )
                                }
                                routinesViewModel.finishExecution()
                                navController.navigate(Screen.Routines.route)
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

data class ListExercise(val name: String, val isSelected: Boolean)

@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    starsColor: Color = Color.Black
) {
    Row {
        for (i in 1..maxRating) {
            if (i <= currentRating)
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clickable { onRatingChanged(i) }
                )
            else
                Icon(
                    painterResource(R.drawable.star_border),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clickable { onRatingChanged(i) }
                )
        }
    }
}

@Composable
fun TabScreen() {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(stringResource(R.string.view_1), stringResource(R.string.view_2))

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.Gray,
            contentColor = Color(0xFF8EFE00),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color(0xFF8EFE00)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title, style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    selectedContentColor = Color(0xFF8EFE00),
                    unselectedContentColor = Color.Black,
                )
            }
        }
        when (tabIndex) {
            0 -> view = 0
            1 -> view = 1
        }
    }
}