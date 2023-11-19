package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.ui.theme.TP_HCI_MOBILETheme

@Composable
fun OtherScreen(id:Int?){
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Text(
                text = stringResource(id = R.string.received_id, id ?: "null"),
                fontSize = 30.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OtherScreenPreview() {
    TP_HCI_MOBILETheme {
        OtherScreen(id = 1234)
    }
}


