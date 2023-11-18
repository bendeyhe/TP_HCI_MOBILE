package ar.edu.itba.tpHciMobile.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.tpHciMobile.R

@Composable
fun RoutineDetails() {

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        bottomBar = { Buttons() }
    ) { contentPadding ->
        RoutineDetailsContent(Modifier.padding(contentPadding))
    }
}

@Composable
fun RoutineDetailsContent(modifier: Modifier) {
    val cycles = listOf(
        "Entrada en Calor",
        "Principal",
        "Enfriamiento",
    )
    val exercises = listOf(
        "Ejercicio 1",
        "Ejercicio 2",
        "Ejercicio 3",
        "Ejercicio 4",
        "Ejercicio 5",
        "Ejercicio 6",
        "Ejercicio 7",
        "Ejercicio 8",
        "Ejercicio 9",
        "Ejercicio 10"
    )

    Surface(modifier = modifier)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(enabled = true, state = ScrollState(0)),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Routine Name",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
            )
            for (cycle in cycles) {
                Text(
                    text = "$cycle",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                for (exercise in exercises)
                    Row() {
                        Text(
                            text = "$exercise"
                        )
                        Text(
                            text = "Duración"
                        )
                    }
            }
        }
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
