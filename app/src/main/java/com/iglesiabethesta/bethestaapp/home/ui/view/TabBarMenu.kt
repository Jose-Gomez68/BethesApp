package com.iglesiabethesta.bethestaapp.home.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iglesiabethesta.bethestaapp.R

@Composable
fun TabBarMenu() {

}



@Composable
fun BottomNavigationBar(function: () -> Unit) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem("Today", R.drawable.ic_launcher_foreground),
        BottomNavItem("Schedule", R.drawable.ic_launcher_foreground)
    )

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // Asegura que el ícono y el texto estén centrados
                        verticalArrangement = Arrangement.Center // Alinea el icono y el texto en el centro
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = item.title
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp) // Espaciado entre el icono y el texto
                        )
                    }
                },
                selected = currentRoute == item.title,
                onClick = {
                    navController.navigate(item.title) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


data class BottomNavItem(val title: String, val icon: Int)
