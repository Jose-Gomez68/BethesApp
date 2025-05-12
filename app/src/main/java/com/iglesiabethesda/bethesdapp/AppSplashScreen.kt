package com.iglesiabethesda.bethesdapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import kotlinx.coroutines.delay


@Composable
fun AppSplashScreen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val sharedPreferences = SharedPreferencesConfig(context)
        val isLoggedIn = sharedPreferences.getUserName()

        delay(1000) // opcional: para mostrar un logo o algo por 1 seg

        if (!isLoggedIn.isNotEmpty()) {//quitar ! para que todo este correcto
            navController.navigate(Routes.HomeScreen.route) {
                popUpTo(0) // limpia el backstack
            }
        } else {
            navController.navigate(Routes.LoginScreen.route) {
                popUpTo(0) // limpia el backstack
            }
        }
    }

    // Puedes mostrar un logo aquí o solo un fondo blanco
    Box(modifier = Modifier.fillMaxSize().background(Color.White))
}

@Composable
private fun Screen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val sharedPreferences = SharedPreferencesConfig(context)
        val isLoggedIn = sharedPreferences.getUserName()

        delay(1000) // opcional: para mostrar un logo o algo por 1 seg

        if (isLoggedIn.isNotEmpty()) {
            navController.navigate(Routes.HomeScreen.route) {
                popUpTo(0) // limpia el backstack
            }
        } else {
            navController.navigate("login") {
                popUpTo(0) // limpia el backstack
            }
        }
    }

    // Puedes mostrar un logo aquí o solo un fondo blanco
    Box(modifier = Modifier.fillMaxSize().background(Color.White))
}