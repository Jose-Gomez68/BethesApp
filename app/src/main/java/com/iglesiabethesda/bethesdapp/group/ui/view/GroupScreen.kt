package com.iglesiabethesda.bethesdapp.group.ui.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.ui.viewmodel.GroupScreenViewModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog2

@Composable
fun GroupScreen(navController: NavHostController) {
    Screen(navController)
}

@Composable
private fun Screen(
    navController: NavHostController,
    viewModel: GroupScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        viewModel.getGroups()
    }

    /*var searchQuery by remember {
        mutableStateOf("")
    }*/

    val groupsResult by viewModel.getGroups
    val showProgress by viewModel.isLoading
    val showDeleteGroup by viewModel.getGroupDelete
    val groups = remember { mutableStateListOf<GroupModel>() }
    var deleteGroup by remember { mutableStateOf<GroupModel?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var searchQuery by remember {
        mutableStateOf("")
    }

    groupsResult?.onSuccess { group ->
        if (group.isNotEmpty()){
            groups.clear()
            groups.addAll(group)
        }
    }

    val filteredGroups = if (searchQuery.isNotBlank()) {
        groups.filter { gp ->
            gp.name.contains(searchQuery, ignoreCase = true)
        }
    } else {
        groups
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
            SearchFieldList(titleLabel = "Buscar Grupo",
                searchQuery = searchQuery, onSearchChanged = { searchQuery = it })
            GroupList(
                filteredGroups,
                onDelete = { item ->
                    deleteGroup = item
                    showDeleteDialog = true
                }
            )

            if (showDeleteDialog){
                SimpleAlertDialog2(
                    title = "Eliminar gurpo",
                    message = "Estas seguro de eliminar el grupo ${deleteGroup!!.name}",
                    buttonNegativeText = "Cancelar",
                    buttonPositiveeText = "Eliminar",
                    onConfirm = {
                        //delete for list
                        deleteGroup?.let { groupToDelete ->
                            groups.remove(groupToDelete)
                        }
                        viewModel.deleteGroupByUid(deleteGroup!!.uid)
                        //remover con viewmodel y queda
                        showDeleteDialog = false
                        viewModel.getGroups()
                    },
                    onDismiss = {
                        showDeleteDialog = false
                    }
                )
            }

            if (showDeleteGroup){
                SimpleAlertDialog(
                    title = "Ups Error",
                    message = "Ocurrio un error al intentar eliminar el grupo: ${deleteGroup!!.name}",
                    buttonNegativeText = "Cancelar",
                    onDismiss = {
                        viewModel.resetShowDeleteError()
                    }
                )
            }

        }

        FloatingActionButton(
            onClick = { navController.navigate("nuevoGrupo") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Agregar")
        }

        LoadingDialog(showProgress)

    }
}


@Composable
fun GroupList(groups: List<GroupModel>, onDelete: (GroupModel) -> Unit) {


    val userList = listOf(
        "Grupo de alabanza - M, 30",
        "Grupo de Cocina - F, 25",
        "Grupo de predica  - M, 35",
        "Grupo de algo - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(groups) { group ->
            GroupItem(
                group,
                groups.size,
                onEdit = { item ->

                },
                onDelete = { item ->
                    onDelete(item)
                }
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GroupItem(
    group: GroupModel,
    groupSize: Int,
    onEdit: (GroupModel) -> Unit = {},
    onDelete: (GroupModel) -> Unit = {}
) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(11.dp))
            .combinedClickable(
                onClick = {
                    Toast.makeText(context, "Click en ${group.name}", Toast.LENGTH_SHORT).show()
                },
                onLongClick = {
                    showMenu = true
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GroupImage(imageUser = 1)
        GroupDescrip(group, groupSize)
    }

    if (showMenu) {
        AlertDialog(
            onDismissRequest = { showMenu = false },
            title = { Text("Opciones del grupo") },
            text = {
                Column {
                    Text(
                        "Editar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clickable {
                                showMenu = false
                                onEdit(group)
                            }
                    )
                    Text(
                        "Eliminar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clickable {
                                showMenu = false
                                onDelete(group)
                            },
                        color = Color.Red
                    )
                }
            },
            confirmButton = {}
        )
    }
}

@Composable
private fun GroupImage(imageUser: Int?) {
    Box(
        modifier = Modifier
            .size(55.dp) // Tamaño del círculo
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_users),
            contentDescription = "Imagen del Usuario",
            modifier = Modifier.size(30.dp) // Tamaño del ícono dentro del círculo
        )
    }
}

@Composable
private fun GroupDescrip(group: GroupModel, groupSize: Int) {


    Column(
        modifier = Modifier
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.Center,

        ) {

        Text(
            text = group.name, // Aquí pones el nombre del usuario
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Text(
            text = "Miembros: ${groupSize}", // numero de miembros
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color.Gray
        )

        Text(
            text = group.description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color.Gray
        )
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