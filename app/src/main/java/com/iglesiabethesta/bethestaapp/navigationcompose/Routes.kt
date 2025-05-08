package com.iglesiabethesta.bethestaapp.navigationcompose

import com.iglesiabethesta.bethestaapp.R

sealed class Routes (val title: String, val icon: Int, val route: String) {
    object SplashScreen: Routes( "AppSplashScreen", R.drawable.ic_launcher_background, "AppSplashScreen" )
    object LoginScreen: Routes( "LoginScreen", R.drawable.ic_launcher_background, "LoginScreen" )
    object SignUp: Routes( "SignUp", R.drawable.ic_launcher_background, "SignUp" )
    object HomeScreen: Routes( "inicio", R.drawable.ic_home, "inicio" )
    object MembersScreen: Routes( "usuarios", R.drawable.ic_users, "usuarios" )
    object EventsScreen: Routes( "eventos", R.drawable.ic_events, "eventos" )
    object GroupsScreen: Routes( "grupos", R.drawable.ic_groups, "grupos" ){
        // Subrutas específicas del módulo de grupos
        object GroupRegisterScreen : Routes("Nuevo Grupo", R.drawable.ic_groups, "nuevoGrupo")
    }
    object MeScreen: Routes( "yo", R.drawable.ic_user, "yo" )
}