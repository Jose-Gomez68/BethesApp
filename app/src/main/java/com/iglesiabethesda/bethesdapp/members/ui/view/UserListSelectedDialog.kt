package com.iglesiabethesda.bethesdapp.members.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp

@Composable
fun UserListSelectedDialogScreen(show: Boolean, membersList: List<MembersModel>, onDismiss: () -> Unit) {
    Screen(show, membersList, onDismiss)
}


@Composable
private fun Screen(show: Boolean, membersList: List<MembersModel>, onDismiss: () -> Unit) {

    if (show) {
        Dialog(
            onDismissRequest = {onDismiss()},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            ScreenDialog(membersList)
        }
    }

}

@Composable
private fun ScreenDialog(membersList: List<MembersModel>) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredMembers = if (searchQuery.isNotBlank()) {
        membersList.filter { member ->
            // Aquí defines los campos donde buscar
            member.name.contains(searchQuery, ignoreCase = true) ||
                    member.apPaterno.contains(searchQuery, ignoreCase = true) ||
                    member.apMaterno.contains(searchQuery, ignoreCase = true)
        }
    } else {
        membersList
    }

    Box(
        modifier = Modifier
            .width(600.dp) // Ajusta el ancho del diálogo
            .height(500.dp) // Ajusta la altura del diálogo
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 25.dp)
            .background(backgroundColorApp, shape = RoundedCornerShape(16.dp))
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SearchFieldList(titleLabel = "Buscar Miembros",
                searchQuery = searchQuery, onSearchChanged = { searchQuery = it } )
            UsersList(filteredMembers)
        }
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