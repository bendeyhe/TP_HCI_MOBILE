package ar.edu.itba.tpHciMobile.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.ui.main.Screen
import ar.edu.itba.tpHciMobile.ui.main.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTablet(
    navController: NavController,
    userViewModel: UserViewModel
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val errorMessage = stringResource(R.string.long_input)
    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimit = 25 // todo ver cuanto era el limite de caracteres
    val context = LocalContext.current
    fun validate(text: String) {
        isError = text.length > charLimit
    }

    if (!userViewModel.uiState.isAuthenticated) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login_msg),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
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
                            text = stringResource(R.string.limit) + ": ${username.length}/$charLimit",
                        )
                    },
                    isError = isError,
                    keyboardActions = KeyboardActions { validate(username) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .semantics {
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
                            text = stringResource(R.string.limit) + ": ${password.length}/$charLimit",
                        )
                    },
                    isError = isError,
                    keyboardActions = KeyboardActions { validate(password) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .semantics {
                            // Provide localized description of the error
                            if (isError) error(errorMessage)
                        },
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) painterResource(R.drawable.visibility_off) else painterResource(
                                    R.drawable.visibility
                                )
                            val description =
                                if (passwordHidden) "Show password" else "Hide password"
                            Icon(painter = visibilityIcon, contentDescription = description)
                        }
                    }
                )

                val notSubmittedUsername = stringResource(R.string.not_submitted_username)
                val notSubmittedPassword = stringResource(R.string.not_submitted_password)
                val notValidUsername = stringResource(R.string.not_valid_username)
                val notValidPassword = stringResource(R.string.not_valid_password)
                val notValidCredentials = stringResource(R.string.not_valid_credentials)
                Button(
                    onClick = {
                        if (username.isEmpty())
                            Toast.makeText(context, notSubmittedUsername, Toast.LENGTH_SHORT).show()
                        else if (username.length > charLimit)
                            Toast.makeText(context, notValidUsername, Toast.LENGTH_SHORT).show()
                        else if (password.isEmpty())
                            Toast.makeText(context, notSubmittedPassword, Toast.LENGTH_SHORT).show()
                        else if (password.length > charLimit)
                            Toast.makeText(context, notValidPassword, Toast.LENGTH_SHORT).show()
                        else {
                            var isAuth = userViewModel.login(username, password)
                            if (!userViewModel.uiState.isFetching) {
                                if (isAuth)
                                    navController.navigate(Screen.Routines.route)
                                else
                                    Toast.makeText(context, notValidCredentials, Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
                    },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Text(
                    text = stringResource(R.string.create_account),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.weight(2f))
            }
        }
    } else {
        if (userViewModel.uiState.currentUser == null)
            userViewModel.getCurrentUser()
        if (!userViewModel.uiState.isFetching) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.profile),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextField(
                        value = userViewModel.uiState.currentUser?.username.toString(),
                        onValueChange = {
                            userViewModel.uiState.currentUser?.username = it
                            validate(userViewModel.uiState.currentUser?.username.toString())
                        },
                        enabled = false,
                        singleLine = true,
                        label = { Text(stringResource(R.string.username)) },
                        placeholder = { Text(stringResource(R.string.username)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Password"
                            )
                        },
                        isError = isError,
                        keyboardActions = KeyboardActions { validate(userViewModel.uiState.currentUser?.username.toString()) },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .semantics {
                                // Provide localized description of the error
                                if (isError) error(errorMessage)
                            }
                    )

                    TextField(
                        value = userViewModel.uiState.currentUser?.firstName.toString(),
                        onValueChange = {
                            userViewModel.uiState.currentUser?.firstName = it
                            validate(userViewModel.uiState.currentUser?.firstName.toString())
                        },
                        enabled = false,
                        singleLine = true,
                        label = { Text(stringResource(R.string.first_name)) },
                        placeholder = { Text(stringResource(R.string.first_name)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Password"
                            )
                        },
                        isError = isError,
                        keyboardActions = KeyboardActions { validate(userViewModel.uiState.currentUser?.firstName.toString()) },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .semantics {
                                // Provide localized description of the error
                                if (isError) error(errorMessage)
                            }
                    )

                    TextField(
                        value = userViewModel.uiState.currentUser?.lastName.toString(),
                        onValueChange = {
                            userViewModel.uiState.currentUser?.lastName = it
                            validate(userViewModel.uiState.currentUser?.lastName.toString())
                        },
                        enabled = false,
                        singleLine = true,
                        label = { Text(stringResource(R.string.last_name)) },
                        placeholder = { Text(stringResource(R.string.last_name)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Password"
                            )
                        },
                        isError = isError,
                        keyboardActions = KeyboardActions { validate(userViewModel.uiState.currentUser?.lastName.toString()) },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .semantics {
                                // Provide localized description of the error
                                if (isError) error(errorMessage)
                            }
                    )

                    TextField(
                        value = userViewModel.uiState.currentUser?.email.toString(),
                        onValueChange = {
                            userViewModel.uiState.currentUser?.email = it
                            validate(userViewModel.uiState.currentUser?.email.toString())
                        },
                        enabled = false,
                        singleLine = true,
                        label = { Text(stringResource(R.string.email)) },
                        placeholder = { Text(stringResource(R.string.email)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Password"
                            )
                        },
                        isError = isError,
                        keyboardActions = KeyboardActions { validate(userViewModel.uiState.currentUser?.email.toString()) },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .semantics {
                                // Provide localized description of the error
                                if (isError) error(errorMessage)
                            }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            userViewModel.logout()
                            if (!userViewModel.uiState.isFetching) {
                                username = ""
                                password = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EFE00)),
                    ) {
                        Text(
                            text = stringResource(R.string.logout),
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
        } else
            Loading()
    }
}