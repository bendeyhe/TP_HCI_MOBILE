package ar.edu.itba.tpHciMobile.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController : NavController) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val errorMessage = stringResource(R.string.long_input)
    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimit = 25 // todo ver cuanto era el limite de caracteres
    fun validate(text: String) {
        isError = text.length > charLimit
    }

    Surface() {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_msg),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            TextField(
                value = username,
                onValueChange = {
                    username = it
                    validate(username)
                },
                singleLine = true,
                label = { Text(stringResource(R.string.username)) },
                placeholder = { Text(stringResource(R.string.username)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Limit: ${username.length}/$charLimit",
                    )
                },
                isError = isError,
                keyboardActions = KeyboardActions { validate(username) },
                modifier = Modifier.padding(16.dp).fillMaxWidth().semantics {
                    // Provide localized description of the error
                    if (isError) error(errorMessage)
                }
            )

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    validate(password)
                },
                singleLine = true,
                label = { Text(stringResource(R.string.password)) },
                placeholder = { Text(stringResource(R.string.password)) },
                visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Limit: ${password.length}/$charLimit",
                    )
                },
                isError = isError,
                keyboardActions = KeyboardActions { validate(password) },
                modifier = Modifier.padding(16.dp).fillMaxWidth().semantics {
                    // Provide localized description of the error
                    if (isError) error(errorMessage)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) painterResource(R.drawable.visibility_off) else painterResource(R.drawable.visibility)//todo buscar como hacer que funcione Visibility
                        // Please provide localized description for accessibility services
                        val description = if (passwordHidden) "Show password" else "Hide password"
                        Icon(painter = visibilityIcon, contentDescription = description)
                    }
                }
            )

            Button(
                onClick = { /*TODO validar usuario y ver a donde navegar navController.navigate(Screen.Routines.route) */ },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Medium)
                )
            }

        }
    }
}
/*
@Preview
@Composable
fun LoginPreview() {
    Login()
}

 */