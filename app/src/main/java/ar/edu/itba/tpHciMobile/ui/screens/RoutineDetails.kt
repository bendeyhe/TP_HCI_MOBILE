package ar.edu.itba.tpHciMobile.ui.screens

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
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.RoutinesViewModel
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.UserViewModel
import ar.edu.itba.tpHciMobile.util.getViewModelFactory

@Composable
fun RoutineDetails(
    userViewModel: UserViewModel = viewModel(factory = getViewModelFactory()),
    navController: NavController,
    routinesViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory()),
    id: Int
) {
    if (!userViewModel.uiState.isAuthenticated || userViewModel.uiState.currentUser == null) {
        navController.navigate(Screen.Routines.route)
    }
    else {

        @OptIn(ExperimentalMaterial3Api::class)
        Scaffold(
            bottomBar = { Buttons() }
        ) { contentPadding ->
            RoutineDetailsContent(Modifier.padding(contentPadding), id, routinesViewModel)
        }
    }
}

@Composable

fun RoutineDetailsContent(modifier: Modifier, id: Int, routinesViewModel: RoutinesViewModel) {
    Column() {
        Text(
            text = "Routine Name",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
        CollapsableLazyColumn(
            sections = listOf(
                CollapsableSection(
                    title = "Entrada en Calor",
                    rows = listOf<Exercise>(
                        Exercise("Ej de calentamiento 1", "Duración 1"),
                        Exercise("Ej de calentamiento 2", "Duración 2"),
                        Exercise("Ej de calentamiento 3", "Duración 3")
                    )
                ),
                CollapsableSection(
                    title = "Principal",
                    rows = listOf<Exercise>(
                        Exercise("Ej 1", "Duración 1"),
                        Exercise("Ej 2", "Duración 2"),
                        Exercise("Ej 3", "Duración 3")
                    )
                ),
                CollapsableSection(
                    title = "Enfriamiento",
                    rows = listOf<Exercise>(
                        Exercise("Ej de enfriamiento 1", "Duración 1"),
                        Exercise("Ej de enfriamiento 2", "Duración 2"),
                        Exercise("Ej de enfriamiento 3", "Duración 3")
                    )
                ),
            ),
        )
    }

}

@Composable
fun Buttons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /*todo compartir rutina */ },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Filled.Share, "Share", tint = Color.Black)
            Text(
                text = stringResource(R.string.share_routine),
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Button(
            onClick = { /*todo empezar rutina */ },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Filled.PlayArrow, "Share", tint = Color.Black)
            Text(
                text = stringResource(R.string.start_routine),
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
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
