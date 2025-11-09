package com.iglesiabethesda.bethesdapp.members.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.events.ui.viewmodel.EventScreenViewModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MembersViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog

/*
firesbase
* https://www.youtube.com/watch?v=hgLgedigea0*/

@Preview
@Composable
fun MembersScreen(navController: NavHostController) {
    Screen(navController)
}

@Composable
private fun Screen(
    navController: NavHostController,
    viewModel: MembersViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getMember()
    }
    /*val searchQuery by remember {
        mutableStateOf("")
    }

    val filteredUsers = users.filter { it.contains(searchQuery, ignoreCase = true) }*/
    val membersResult by viewModel.getMembers
    val showProgress by viewModel.isLoading
    var members by remember { mutableStateOf<List<MembersModel>>(emptyList()) }
    var searchQuery by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    membersResult?.onSuccess { memb ->
        if (memb.isNotEmpty()){
            members = memb
        }
    }

    val filteredMembers = if (searchQuery.isNotBlank()) {
        members.filter { member ->
            // Aquí defines los campos donde buscar
            member.name.contains(searchQuery, ignoreCase = true) ||
                    member.apPaterno.contains(searchQuery, ignoreCase = true) ||
                    member.apMaterno.contains(searchQuery, ignoreCase = true)
        }
    } else {
        members
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SearchFieldList(titleLabel = "Buscar Miembros",
                searchQuery = searchQuery, onSearchChanged = { searchQuery = it } )
            UsersList(filteredMembers)

            LoadingDialog(showProgress)
        }

        MultiOptionFAB(navController)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchFieldList(titleLabel: String, searchQuery: String, onSearchChanged: (String) -> Unit) {

    var isActive by remember { mutableStateOf(false) } // Estado de la barra de búsqueda

    SearchBar(
        query = searchQuery,
        onQueryChange = { onSearchChanged(it) },
        onSearch = { isActive = false }, // Ocultar teclado al buscar
        active = false,
        onActiveChange = { isActive = it },
        placeholder = { Text(titleLabel) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Buscar"
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchChanged("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Borrar búsqueda")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),

    ) {
        // Aquí puedes mostrar sugerencias de búsqueda si lo deseas
    }

}

@Composable
fun MultiOptionFAB(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        if (expanded) {
            Column(
                modifier = Modifier.padding(bottom = 72.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                FabOption(
                    icon = Icons.Filled.Person,
                    text = "Nuevo usuario",
                    onClick = {
                        // Acción 1
                        expanded = false
                        navController.navigate("RegistrarUsuario")
                    }
                )

                FabOption(
                    icon = Icons.Filled.AccountBox,
                    text = "Registrar Visita",
                    onClick = {
                        // Acción 2
                        expanded = false
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = "Expandir opciones"
            )
        }
    }
}

@Composable
fun FabOption(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondary,
        shadowElevation = 4.dp,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(icon, contentDescription = text, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, color = Color.White)
        }
    }
}
