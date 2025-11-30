package com.iglesiabethesda.bethesdapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.GsonBuilder
import com.iglesiabethesda.bethesdapp.Login.ui.LoginResPasswordScreen
import com.iglesiabethesda.bethesdapp.Login.ui.LoginScreen
import com.iglesiabethesda.bethesdapp.Login.ui.SignUpScreen
import com.iglesiabethesda.bethesdapp.Login.ui.VerificationScreen
import com.iglesiabethesda.bethesdapp.events.ui.view.EventScreen
import com.iglesiabethesda.bethesdapp.events.ui.view.NewEventScreen
import com.iglesiabethesda.bethesdapp.group.ui.view.GroupRegisterScreen
import com.iglesiabethesda.bethesdapp.group.ui.view.GroupScreen
import com.iglesiabethesda.bethesdapp.home.ui.view.HomeScreen
import com.iglesiabethesda.bethesdapp.me.ui.view.MeScreen
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.view.MemberDetailsScreen
import com.iglesiabethesda.bethesdapp.members.ui.view.MembersEditScreen
import com.iglesiabethesda.bethesdapp.members.ui.view.MembersRegisterScreen
import com.iglesiabethesda.bethesdapp.members.ui.view.MembersScreen
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.ui.theme.BethestaAppTheme
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**ACCOUNT GOOGLE DB BETHESTAPP
 * bethestapp@gmail.com
 * PASS:68120568App*/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BethestaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route ?: ""

                    // Rutas donde ocultas TODO (login, splash, etc.)
                    val hideAllBars = currentRoute in listOf(
                        Routes.SplashScreen.route,
                        Routes.LoginScreen.route,
                        Routes.SignUp.route,
                        Routes.VerificationScreen.route,
                        Routes.LoginRestPasswordScreen.route
                    )

                    // Subrutas donde ocultas solo el bottom bar (pero mantienes top con back)
                    val hideBottomBar = currentRoute in listOf(
                        Routes.MembersScreen.MembersRegisterScreen.route,
                        Routes.MembersScreen.MemberDetailsScreen.route,
                        Routes.GroupsScreen.GroupRegisterScreen.route,
                        Routes.EventsScreen.NewEventScreen.route
                    )

                    Scaffold(
                        topBar = {
                            if (!hideAllBars) {
                                Toolbar(currentRoute = currentRoute, navController = navigationController)
                            }
                        },
                        bottomBar = {
                            if (!hideAllBars && !hideBottomBar) {
                                MenuBottonNavigation(navController = navigationController)
                            }
                        }
                    ) { innerPadding -> // ← aquí está el content (PaddingValues)
                        NavigationGraph(
                            navController = navigationController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(currentRoute: String, navController: NavController) {
    val screenTitles = mapOf(
        Routes.HomeScreen.route to "Inicio",
        Routes.MembersScreen.route to "Miembros",
        Routes.MembersScreen.MembersRegisterScreen.route to "Registrar Usuario",
        Routes.MembersScreen.MemberDetailsScreen.route to "Detalles de la Persona",
        Routes.GroupsScreen.route to "Grupos",
        Routes.GroupsScreen.GroupRegisterScreen.route to "Registrar Grupo",
        Routes.EventsScreen.route to "Eventos",
        Routes.EventsScreen.NewEventScreen.route to "Nuevo Evento",
        Routes.MeScreen.route to "Perfil"
    )

    val title = screenTitles[currentRoute] ?: "App"

    //flecha de return en vistas secundarias
    val showBackButton = currentRoute in listOf(
        Routes.MembersScreen.MembersRegisterScreen.route,
        Routes.MembersScreen.MemberDetailsScreen.route,
        Routes.MembersScreen.MembersEditScreen.route,
        Routes.GroupsScreen.GroupRegisterScreen.route,
        Routes.EventsScreen.NewEventScreen.route
    )

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.Black
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColorApp
        )
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
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        //startDestination = Routes.HomeScreen.route,
        startDestination = Routes.SplashScreen.route,
        modifier = modifier
    ){
        composable(Routes.SplashScreen.route) { AppSplashScreen(navController) }
        composable(Routes.LoginScreen.route) { LoginScreen(navController) }
        composable(Routes.SignUp.route) { SignUpScreen(navController) }
        composable(Routes.VerificationScreen.route) { VerificationScreen(navController) }
        composable(Routes.LoginRestPasswordScreen.route) { LoginResPasswordScreen(navController) }
        composable(Routes.HomeScreen.route) { HomeScreen() }
        composable(Routes.MembersScreen.route) { MembersScreen(navController) }
        composable(
            Routes.MembersScreen.MemberDetailsScreen.route,
            arguments = listOf(
                navArgument("memberJson") { type = NavType.StringType }
            )
        ) {backStackEntry ->
            val memberJson = backStackEntry.arguments?.getString("memberJson")

            val gson = GsonBuilder()
                .setDateFormat("MMM dd, yyyy hh:mm:ss a")
                .create()

            val member = gson.fromJson(
                URLDecoder.decode(memberJson, StandardCharsets.UTF_8.toString()),
                MembersModel::class.java
            )
            MemberDetailsScreen(member, navController)
        }
        composable(
            Routes.MembersScreen.MembersRegisterScreen.route
        ) {
            MembersRegisterScreen(navController)
        }
        composable(
            Routes.MembersScreen.MembersEditScreen.route,
            arguments = listOf(
                navArgument("memberJson") { type = NavType.StringType }
            )
        ) {backStackEntry ->
            val memberJson = backStackEntry.arguments?.getString("memberJson")

            val gson = GsonBuilder()
                .setDateFormat("MMM dd, yyyy hh:mm:ss a")
                .create()

            val member = gson.fromJson(
                URLDecoder.decode(memberJson, StandardCharsets.UTF_8.toString()),
                MembersModel::class.java
            )
            MembersEditScreen(navController, member)
        }
        composable(Routes.GroupsScreen.route) { GroupScreen(navController) }
        composable(
            route = Routes.GroupsScreen.GroupRegisterScreen.route
        ) {
            GroupRegisterScreen(navController)
        }
        composable(Routes.EventsScreen.route) { EventScreen(navController = navController) }
        composable(Routes.EventsScreen.NewEventScreen.route) { NewEventScreen(navController) }
        composable(Routes.MeScreen.route) { MeScreen(navController = navController) }
    }

}