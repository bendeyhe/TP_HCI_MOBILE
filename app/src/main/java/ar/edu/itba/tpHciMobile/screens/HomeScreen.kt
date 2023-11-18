package ar.edu.itba.tpHciMobile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ar.edu.itba.tpHciMobile.R

@Composable
fun HomeScreen(
    onNavigateToOtherScreen: (id:Int) -> Unit
) {
    Surface{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            ElevatedButton(onClick = { onNavigateToOtherScreen(1234) }) {
                Text(
                    text = stringResource(R.string.goto_other_screen),
                    fontSize = 30.sp
                )
            }
        }
    }
}
