package com.iglesiabethesta.bethestaapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iglesiabethesta.bethestaapp.Login.ui.LoginScreen
import com.iglesiabethesta.bethestaapp.Login.ui.SignUpScreen
import com.iglesiabethesta.bethestaapp.events.ui.view.EventScreen
import com.iglesiabethesta.bethestaapp.group.ui.view.GroupRegisterScreen
import com.iglesiabethesta.bethestaapp.group.ui.view.GroupScreen
import com.iglesiabethesta.bethestaapp.home.ui.view.HomeScreen
import com.iglesiabethesta.bethestaapp.me.ui.view.MeScreen
import com.iglesiabethesta.bethestaapp.members.ui.view.MembersScreen
import com.iglesiabethesta.bethestaapp.navigationcompose.Routes
import com.iglesiabethesta.bethestaapp.ui.theme.BethestaAppTheme
import com.iglesiabethesta.bethestaapp.ui.theme.backgroundColorApp

/**ACCOUNT GOOGLE DB BETHESTAPP
 * bethestapp@gmail.com
 * PASS:68120568App*/

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BethestaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    val navigationController = rememberNavController()
                    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route ?: ""
                    // Ocultar barras en estas rutas
                    val hideBars = currentRoute in listOf(
                        Routes.SplashScreen.route,
                        Routes.LoginScreen.route,
                        Routes.SignUp.route
                    )

                    Scaffold(
                        topBar = {
                            if (!hideBars) {
                                Toolbar(currentRoute)
                            }
                        },
                        bottomBar = {
                            if (!hideBars) {
                                MenuBottonNavigation(navController = navigationController)
                            }
                        }
                    ) {
                        NavigationGraph(
                            navController = navigationController,
                            modifier = Modifier.padding(it)
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(currentRoute: String) {

    val screenTitles = mapOf(
        Routes.HomeScreen.route to "Inicio",
        Routes.MembersScreen.route to "Miembros",
        Routes.GroupsScreen.route to "Grupos",
        Routes.GroupsScreen.GroupRegisterScreen.route to "Registrar Grupo",
        Routes.EventsScreen.route to "Eventos",
        Routes.MeScreen.route to "Perfil"
    )

    val title = screenTitles[currentRoute] ?: "App"

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = title,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1.2f))
            }
        },
        Modifier.background(backgroundColorApp)
    )
}

@Composable
fun MenuBottonNavigation(navController: NavController) {
    val screens = listOf(
        Routes.HomeScreen,
        Routes.MembersScreen,
        Routes.GroupsScreen,
        Routes.EventsScreen,
        Routes.MeScreen
    )

    NavigationBar(containerColor = backgroundColorApp) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoutes = navBackStackEntry?.destination?.route

        screens.forEach {screen ->
            NavigationBarItem(
                selected = currentRoutes == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )},
                label = { Text(text = screen.title)}
            )
        }
    }

}

//viedeo https://www.youtube.com/watch?v=Duidcy6ieUc
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        //startDestination = Routes.HomeScreen.route,
        startDestination = Routes.SplashScreen.route,
        modifier = modifier
    ){
        composable(Routes.SplashScreen.route) { AppSplashScreen(navController) }
        composable(Routes.LoginScreen.route) { LoginScreen() }
        composable(Routes.SignUp.route) { SignUpScreen() }
        composable(Routes.HomeScreen.route) { HomeScreen() }
        composable(Routes.MembersScreen.route) { MembersScreen() }
        composable(Routes.GroupsScreen.route) { GroupScreen(navController) }
        composable(
            route = Routes.GroupsScreen.GroupRegisterScreen.route
        ) {
            GroupRegisterScreen()
        }
        composable(Routes.EventsScreen.route) { EventScreen() }
        composable(Routes.MeScreen.route) { MeScreen() }
    }

}