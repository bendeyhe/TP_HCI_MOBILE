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

@Composable
fun ExecuteRoutineTablet(
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