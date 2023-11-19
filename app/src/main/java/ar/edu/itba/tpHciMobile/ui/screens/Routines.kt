package ar.edu.itba.tpHciMobile.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ar.edu.itba.tpHciMobile.R
import ar.edu.itba.tpHciMobile.ui.main.Screen


@Composable
fun Routines(modifier: Modifier = Modifier, navController: NavController) {
    val names = listOf(
        "Rutina 1",
        "Rutina 2",
        "Rutina 3",
        "Rutina 4",
        "Rutina 5",
        "Rutina 6",
        "Rutina 7",
        "Rutina 8",
        "Rutina 9",
        "Rutina 10"
    )
    val description = "Esta es una rutina de prueba"
    val rating = "4.5"
    val difficulty = "Hard"

    Surface(
        color = Color(0xFFAEB0B2)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            OrderByBtn(modifier.padding(end = 8.dp))
            LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                items(items = names) { name ->
                    Routine(name, description, difficulty, rating, onItemClick = {
                        navController.navigate(Screen.RoutineDetails.route)
                    })
                }
            }
        }
    }
}


@Composable
fun Routine(
    name: String,
    description: String,
    difficulty: String,
    rating: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    var expanded = rememberSaveable { mutableStateOf(false) }
    var fav = rememberSaveable { mutableStateOf(false) }
    //luego para usar lo que hay en expanded se usa expanded.value
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onItemClick)
    ) {
        Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding),
            ) { //de esta manera se le da peso a la columna
                Text(
                    text = "$name",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = "$description!",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
                Text(
                    text = stringResource(R.string.difficulty) + ": $difficulty",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
                Text(
                    text = stringResource(R.string.rating) + ": $rating",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
            }
            Column() {
                FavButton(fav = fav.value, onClick = { fav.value = !fav.value })
                /*
        ElevatedButton(onClick = { expanded.value = !expanded.value }) {
            Text(text = if (expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more))
        }

         */

            }
        }
    }
}


@Composable
fun FavButton(fav: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        if (fav) {
            Icon(
                Icons.Filled.Favorite,
                "FavIcon",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Icon(
                Icons.Outlined.FavoriteBorder,
                "FavIcon",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

/*
@Preview
@Composable
fun RoutinesPreview() {
    Routines()
}
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderByBtn(modifier: Modifier = Modifier) {

    val options = listOf(
        stringResource(R.string.order_by_date_desc),
        stringResource(R.string.order_by_date_asc),
        stringResource(R.string.order_by_rating_desc),
        stringResource(R.string.order_by_rating_asc),
        stringResource(R.string.order_by_diff_desc),
        stringResource(R.string.order_by_diff_asc)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val msg = stringResource(R.string.order_by)
    var label by remember{mutableStateOf(msg)}
    var selected by remember { mutableStateOf(false) }
    
    FilterChip(
        modifier = modifier
            .wrapContentSize()
            .padding(top = 8.dp),
        //trailingIcon = Icons.Filled.KeyboardArrowDown,
        onClick = { selected = !selected },
        colors = FilterChipDefaults.filterChipColors(
            labelColor = Color.Black,
            containerColor = Color(0xFF8EFE00),
            selectedLabelColor = Color(0xFF8EFE00),
            selectedLeadingIconColor = Color.Black,
            selectedContainerColor = Color.Black
        ),
        border = null,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Icon(Icons.Filled.KeyboardArrowDown, "Share", tint = Color.Black)

        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                DropdownMenu(
                    expanded = selected,
                    onDismissRequest = {
                        selected = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                selected = false
                                label = selectedOptionText
                            }
                        )
                    }
                }
            }
        } else {
            null
        },
    )


}

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {

    val radioOptions = listOf(
        stringResource(R.string.order_by_date_desc),
        stringResource(R.string.order_by_date_asc),
        stringResource(R.string.order_by_rating_desc),
        stringResource(R.string.order_by_rating_asc),
        stringResource(R.string.order_by_diff_desc),
        stringResource(R.string.order_by_diff_asc)
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }// supongo que aca hay que llamar a la funcion que reordena
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                // Row para colocar los botones "Cerrar" y "Aceptar"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Botón "Cerrar"
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.close),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                        )
                    }
                    // Botón "Confirmar"
                    TextButton(
                        onClick = {
                            // todo Aca agregar la lógica para el botón "Aceptar"
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.confirm),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
}


