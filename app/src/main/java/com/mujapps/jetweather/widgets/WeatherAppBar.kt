package com.mujapps.jetweather.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mujapps.jetweather.model.Favourite
import com.mujapps.jetweather.navigation.WeatherScreens
import com.mujapps.jetweather.screens.favourite.FavouriteViewModel

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val context = LocalContext.current

    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingsDropDownMenu(showDialogState = showDialog, navController)
    }

    TopAppBar(title = {
        Text(
            text = title,
            color = MaterialTheme.colors.onSecondary,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = {
                onAddActionClicked.invoke()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Data")
            }
            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More options")
            }
        } else {
            Box() {}
        }
    }, navigationIcon = {
        if (icon != null && !isMainScreen) {
            Icon(
                imageVector = icon,
                contentDescription = "Home Icon",
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier.clickable {
                    onButtonClicked.invoke()
                })
        }
        if (isMainScreen) {
            val isAlreadyFavList = favViewModel.favList.collectAsState().value.filter { item ->
                (item.city.substringBefore("-").trim().equals(title.split(",")[0], true))
            }

            if (isAlreadyFavList.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite Icon",
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            favViewModel
                                .insertFavouriteCity(
                                    Favourite(
                                        title
                                            .substringBefore(",")
                                            .trim(),
                                        title
                                            .substringAfter(",")
                                            .trim()
                                    )
                                )
                                .run {
                                    showIt.value = true
                                }
                        }, tint = Color.Red.copy(alpha = 0.6f)
                )
            } else {
                showIt.value = false
                Box {}
            }

            ShowToast(context = context, showIt)
        }
    }, backgroundColor = Color.Transparent, elevation = elevation
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingsDropDownMenu(showDialogState: MutableState<Boolean>, navController: NavController) {

    var expandedState by remember {
        mutableStateOf(true)
    }

    val menuItems = listOf("About", "Favourites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expandedState,
            onDismissRequest = { expandedState = false },
            modifier = Modifier
                .width(140.dp)
                .background(
                    Color.White
                )
        ) {
            menuItems.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    showDialogState.value = false
                    expandedState = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favourites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        }, contentDescription = null,
                        tint = Color.Gray
                    )
                    Text(text = text, modifier = Modifier.clickable {
                        navController.navigate(
                            when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favourites" -> WeatherScreens.FavouriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            }
                        )
                    }, fontWeight = FontWeight.W300)
                }
            }
        }
    }
}
