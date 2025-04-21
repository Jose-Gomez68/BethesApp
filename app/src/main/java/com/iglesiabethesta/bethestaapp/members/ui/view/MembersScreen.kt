package com.iglesiabethesta.bethestaapp.members.ui.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iglesiabethesta.bethestaapp.R
import com.iglesiabethesta.bethestaapp.ui.theme.backgroundColorApp

@Composable
fun MembersScreen() {
    MembersRegisterScreen()
}

@Composable
fun screen() {

    /*val searchQuery by remember {
        mutableStateOf("")
    }

    val filteredUsers = users.filter { it.contains(searchQuery, ignoreCase = true) }*/
    var searchQuery by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

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
            UsersList()
        }

        FloatingActionButton(
            onClick = { Toast.makeText(context, "Click en nuevo miembro", Toast.LENGTH_SHORT).show() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Agregar")
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