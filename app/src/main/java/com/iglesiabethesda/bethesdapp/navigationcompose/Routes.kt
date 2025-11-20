package com.iglesiabethesda.bethesdapp.navigationcompose

import com.iglesiabethesda.bethesdapp.R

sealed class Routes (val title: String, val icon: Int, val route: String) {
    object SplashScreen: Routes( "AppSplashScreen", R.drawable.ic_launcher_background, "AppSplashScreen" )
    object LoginScreen: Routes( "LoginScreen", R.drawable.ic_launcher_background, "LoginScreen" )
    object SignUp: Routes( "SignUp", R.drawable.ic_launcher_background, "SignUp" )
    object VerificationScreen: Routes( "VerificationScreen", R.drawable.ic_launcher_background, "VerificationScreen" )
    object LoginRestPasswordScreen: Routes( "LoginRestPasswordScreen", R.drawable.ic_launcher_background, "LoginRestPasswordScreen" )
    object HomeScreen: Routes( "inicio", R.drawable.ic_home, "inicio" )
    object MembersScreen: Routes( "usuarios", R.drawable.ic_users, "usuarios" ) {
        object MembersRegisterScreen: Routes( "RegisterUser", R.drawable.ic_users, "RegistrarUsuario" )
        object MemberDetailsScreen: Routes( "Detalle del Persona", R.drawable.ic_users, "MemberDetailsScreen/{memberJson}" )
    }
    object EventsScreen: Routes( "eventos", R.drawable.ic_events, "eventos" ){
        object NewEventScreen: Routes("Nuevo Evento", R.drawable.ic_events, "NewEvent")
    }
    object GroupsScreen: Routes( "grupos", R.drawable.ic_groups, "grupos" ){
        // Subrutas específicas del módulo de grupos
        object GroupRegisterScreen : Routes("Nuevo Grupo", R.drawable.ic_groups, "nuevoGrupo")
    }
    object MeScreen: Routes( "yo", R.drawable.ic_user, "yo" )
}