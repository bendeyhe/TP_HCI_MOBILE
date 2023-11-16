package ar.edu.itba.tpHciMobile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.tpHciMobile.ui.theme.TP_HCI_MOBILETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP_HCI_MOBILETheme {
                // A surface container using the 'background' color from the theme
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp (modifier: Modifier = Modifier) {
    var shouldShowOnBoarding by rememberSaveable {mutableStateOf(true)}
    Surface(modifier) {
        if(shouldShowOnBoarding) {
            OnBoardingScreen(onContinueClicked = {shouldShowOnBoarding = false})
        } else{
            Greetings()
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(50) {"$it"}) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names){name ->
            Greeting(name)
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome to the app")
        ElevatedButton(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick =  onContinueClicked ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun GreetingsPreview(){
    TP_HCI_MOBILETheme {
        Greetings()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode", showSystemUi = true)
@Composable
fun GreetingsDarkPreview(){
    TP_HCI_MOBILETheme {
        Greetings()
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //el "remember" sería como static en C, solo se inicializa la primera vez que se llama
    //mutableStateOf es como el ref para hacer variables reactivas en JS que le avisa a Vue que se tiene que monitorear la variable por si hay cambios
    var expanded = rememberSaveable { mutableStateOf(false) }
    //luego para usar lo que hay en expanded se usa expanded.value
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) { //de esta manera se le da peso a la columna
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = "$name!",
                    modifier = modifier.then(Modifier.padding(24.dp)) //de esta manera se concatenan los modifiers
                )
            }
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    TP_HCI_MOBILETheme {
        MyApp()
    }
}



@Composable
fun MyText() {
    val names = listOf("John", "Jane", "Tom")
    Column(Modifier.padding(12.dp)) {
        for (name in names) {
            Text(text = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyTextPreview() {
    MyText()
}