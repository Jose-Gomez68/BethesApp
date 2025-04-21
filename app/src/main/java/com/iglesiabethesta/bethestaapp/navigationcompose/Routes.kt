package com.iglesiabethesta.bethestaapp.navigationcompose

import com.iglesiabethesta.bethestaapp.R

sealed class Routes (val title: String, val icon: Int, val route: String) {
    object HomeScreen: Routes( "inicio", R.drawable.ic_home, "inicio" )
    object MembersScreen: Routes( "usuarios", R.drawable.ic_users, "usuarios" )
    object EventsScreen: Routes( "eventos", R.drawable.ic_events, "eventos" )
    object GroupsScreen: Routes( "grupos", R.drawable.ic_groups, "grupos" )
    object MeScreen: Routes( "yo", R.drawable.ic_user, "yo" )
}