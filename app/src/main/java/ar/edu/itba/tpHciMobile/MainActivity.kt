package ar.edu.itba.tpHciMobile

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.tpHciMobile.data.model.NetworkData
import ar.edu.itba.tpHciMobile.ui.theme.TP_HCI_MOBILETheme
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP_HCI_MOBILETheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomBar(navController = navController)}
                ) {
                    MyAppNavHost(navController = navController)
                }
                //MyApp(modifier = Modifier.fillMaxSize())

                // A surface container using the 'background' color from the theme
                MyApp(modifier = Modifier.fillMaxSize())

                val appState = rememberMyAppState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { appState.snackbarHostState }
                ) { contentPadding ->
                    MainScreen(
                        modifier = Modifier.padding(contentPadding),
                        appState = appState,
                        viewModel = viewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        Screen.FirstScreen,
        Screen.Routines,
        Screen.ThirdScreen
    )

    NavigationBar{
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach{ item ->
            NavigationBarItem(
                icon = {Icon(imageVector = item.icon, contentDescription = item.title)},
                label = {Text(text = item.title)},
                alwaysShowLabel  = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

    }
}



@Composable
fun MyApp (modifier: Modifier = Modifier) {
    var shouldShowOnBoarding by rememberSaveable {mutableStateOf(true)}
    Surface(modifier) {
        if (shouldShowOnBoarding) {
            OnBoardingScreen(onContinueClicked = { shouldShowOnBoarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(50) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name)
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the app")
        ElevatedButton(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun GreetingsPreview() {
    TP_HCI_MOBILETheme {
        Greetings()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode", showSystemUi = true)
@Composable
fun GreetingsDarkPreview() {
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
        ), label = ""
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
                Text(
                    text = if (expanded.value) stringResource(R.string.show_less) else stringResource(
                        R.string.show_more
                    )
                )
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

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appState: MyAppState,
    viewModel: MainViewModel
) {
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (uiState.isLoading)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.loading),
                        fontSize = 16.sp
                    )
                }
            else {
                val list = uiState.users?.data.orEmpty()
                LazyVerticalGrid(
                    state = rememberLazyGridState(),
                    columns = GridCells.Adaptive(minSize = 250.dp),
                ) {
                    items(
                        count = list.size,
                        key = { index -> list[index].id.toString() }
                    ) { index ->
                        userCard(list[index])
                    }
                }
            }

            ElevatedButton(
                onClick = {
                          viewModel.fetchUsers(1)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.load_users),
                    modifier = Modifier.padding(8.dp)
                )
            }

            if(uiState.hasError){
                appState.showSnackbar(
                    uiState.message!!,
                    { viewModel.dissmissMessage() },
                    { viewModel.dissmissMessage() }
                )
            }
        }
    }
}

@Composable
fun userCard(data: NetworkData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        elevation = cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = data.avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "${data.firstName} - ${data.lastName}",
                    fontSize = 16.sp
                )
                Text(
                    text = data.email,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun userCardPreview() {
    userCard(
        NetworkData(
            id = 1,
            email = "hola@gmail.com",
            firstName = "John",
            lastName = "Doe",
            avatar = "https://reqres.in/img/faces/1-image.jpg"
        )
    )
}