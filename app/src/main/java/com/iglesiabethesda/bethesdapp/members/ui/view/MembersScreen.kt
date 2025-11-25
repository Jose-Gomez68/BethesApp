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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MembersViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog2

/*
firesbase
* https://www.youtube.com/watch?v=hgLgedigea0*/

@Composable
fun MembersScreen(navController: NavHostController) {
    Screen(navController)
}

@Composable
private fun Screen(
    navController: NavHostController,
    viewModel: MembersViewModel = hiltViewModel()
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
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
    var showDeleteDialog by remember { mutableStateOf(false) }
    var memberToDelete by remember { mutableStateOf<MembersModel?>(null) }


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
            SearchFieldList(titleLabel = stringResource(id = R.string.title_search_member_screen),
                searchQuery = searchQuery, onSearchChanged = { searchQuery = it } )
            UsersList(
                filteredMembers,
                navController,
                onDelete = { member ->
                    //viewModel.deleteMemberByUid(member.uid)
                    memberToDelete = member
                    showDeleteDialog = true
                }
            )

            LoadingDialog(showProgress)

            if (showDeleteDialog) {
                SimpleAlertDialog2(
                    title = stringResource(id = R.string.title_dialog_delete_member_screen),
                    message = stringResource(
                        id = R.string.message_dialog_delete_member_screen,
                        memberToDelete?.name ?: ""
                    ),
                    buttonNegativeText = stringResource(id = R.string.title_buttonnegative_dialog_delete_member_screen),
                    buttonPositiveeText = stringResource(id = R.string.title_buttonpositive_dialog_delete_member_screen),
                    onConfirm = {
                        // Aquí sí eliminas
                        viewModel.deleteMemberByUid(memberToDelete!!.uid)
                        showDeleteDialog = false
                        viewModel.getMember()
                    },
                    onDismiss = {
                        showDeleteDialog = false
                    }
                )
            }

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
                    text = stringResource(id = R.string.title_fl_button1_member_screen),
                    onClick = {
                        // Acción 1
                        expanded = false
                        navController.navigate("RegistrarUsuario")
                    }
                )

                FabOption(
                    icon = Icons.Filled.AccountBox,
                    text = stringResource(id = R.string.title_fl_button2_member_screen),
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
