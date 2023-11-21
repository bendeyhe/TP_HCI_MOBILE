package ar.edu.itba.tpHciMobile.ui.screens

import android.content.Intent
import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun RoutineDetails(
    userViewModel: UserViewModel = viewModel(factory = getViewModelFactory()),
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int
) {
    if (!routinesViewModel.uiState.isFetchingR) {
        val currentRoutine = routinesViewModel.uiState.currentRoutine
        @OptIn(ExperimentalMaterial3Api::class)
        if (currentRoutine != null) {
            Scaffold(
                bottomBar = {
                    Buttons(currentRoutine, navController)
                }
            ) { contentPadding ->
                RoutineDetailsContent(
                    Modifier.padding(contentPadding),
                    routinesViewModel,
                    currentRoutine
                )
            }
        } else {
            routinesViewModel.getRoutine(routineId)
        }
    } else {
        Loading()
    }
}

@Composable

fun RoutineDetailsContent(
    modifier: Modifier,
    routinesViewModel: RoutinesViewModel,
    currentRoutine: Routine
) {

    /*for (cycle in routinesViewModel.uiState.cycleDetailList) {
        if (cycle.cycle?.name != null)
            Text(text = cycle.cycle.name)
        cycle.exercises?.forEach {
            Text(text = it.exercise.name)
        }
    }*/
    Column {
        Text(
            text = currentRoutine.name,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
        CollapsableLazyColumn(
            sections = routinesViewModel.uiState.cycleDetailList.map {
                CollapsableSection(
                    title = it.cycle?.name ?: "",
                    rows = it.exercises?.map { exercise ->
                        Exercise(
                            name = exercise.exercise.name,
                            duration = exercise.duration.toString()
                        )
                    } ?: emptyList()
                )
            }
        )
    }




    }/*
    Column() {
        Text(
            text = currentRoutine.name,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
        CollapsableLazyColumn(

            sections = mutzaa
        )
    }
    */



@Composable
fun Buttons(routine: Routine, navController: NavController){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "https://toobig.com/routine/" + routine.id)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                context.startActivity(shareIntent)
            },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.share_routine) + " ",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Icon(Icons.Filled.Share, "Share", tint = Color.Black)
        }
        Button(
            onClick = { navController.navigate(Screen.ExecuteRoutine.route) },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.start_routine) + " ",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Icon(Icons.Filled.PlayArrow, "Play", tint = Color.Black)
        }
    }
}


data class CollapsableSection(val title: String, val rows: List<Exercise>)

data class Exercise(val name: String, val duration: String)

@Composable
fun CollapsableLazyColumn(
    sections: List<CollapsableSection>,
    modifier: Modifier = Modifier
) {
    val collapsedState = remember(sections) { sections.map { true }.toMutableStateList() }
    LazyColumn(modifier) {
        sections.forEachIndexed { i, dataItem ->
            val collapsed = collapsedState[i]
            item(key = "header_$i") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            collapsedState[i] = !collapsed
                        }
                ) {
                    Icon(
                        Icons.Default.run {
                            if (collapsed)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.LightGray,
                    )
                    Text(
                        dataItem.title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                Divider()
            }
            if (!collapsed) {
                items(dataItem.rows) { row ->
                    Row {
                        Spacer(modifier = Modifier.size(MaterialIconDimension.dp))
                        Column() {
                            Text(
                                row.name,
                                modifier = Modifier
                                    .padding(vertical = 10.dp),
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                row.duration,
                                modifier = Modifier
                                    .padding(vertical = 10.dp),
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
}

const val MaterialIconDimension = 24f
