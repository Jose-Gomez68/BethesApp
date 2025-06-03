package com.iglesiabethesda.bethesdapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.splashscreen.ui.viewmodel.AppSplashScreenViewModel
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import kotlinx.coroutines.delay


@Composable
fun AppSplashScreen(navController: NavController, viewModel: AppSplashScreenViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val sessionStatus by viewModel.sessionStatus

    LaunchedEffect(sessionStatus) {
        delay(1000) // opcional: para mostrar un logo o algo por 1 seg

        when (sessionStatus) {
            is LoginResult.Success -> {
                val isVerified = (sessionStatus as LoginResult.Success).verified
                if (isVerified) {
                    Log.e("SE FUE AL HOME", "")
                    navController.navigate(Routes.HomeScreen.route) {
                        popUpTo(Routes.SplashScreen.route) { inclusive = true }
                    }

                } else {
                    Log.e("SE FUE AL VERIFICATION", "")
                    navController.navigate(Routes.VerificationScreen.route) {
                        popUpTo(Routes.SplashScreen.route) { inclusive = true }
                    }
                }
            }
            is LoginResult.Error -> {
                Log.e("SE FUE AL LOGIN", "")
                navController.navigate(Routes.LoginScreen.route) {
                    popUpTo(Routes.SplashScreen.route) { inclusive = true }
                }
            }
            null -> { /* Loading... */ }
        }

    }

    /*LaunchedEffect(Unit) {
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



    }*/

    // Puedes mostrar un logo aquí o solo un fondo blanco
    Box(modifier = Modifier.fillMaxSize().background(Color.White))
}

/**
 * ESTE CODIGO SE USARA AQUI PARA SABER
 * SI HAY UNA SESION INICIADA VERIFICADA -> MANDA AL HOME
 * SI HAY UNA SESION INICIADA Y NO VERIFICADA -> MANDA AL VERIFICACION DE CUENTA
 * SI NO HAY NINGUNA SESION -> MANDA LOGIN
 *
* viewModelScope.launch {
 *     when (val result = authService.getActiveSessionStatus()) {
 *         is LoginResult.Success -> {
 *             if (result.verified) {
 *                 // Navegar al Home
 *             } else {
 *                 // Navegar a verificación de email
 *             }
 *         }
 *         is LoginResult.Error -> {
 *             // Navegar al login
 *         }
 *     }
 * }
 *
 * */

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