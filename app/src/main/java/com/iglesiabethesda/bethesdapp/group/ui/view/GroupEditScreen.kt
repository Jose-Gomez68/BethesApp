package com.iglesiabethesda.bethesdapp.group.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.GsonBuilder
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.ui.viewmodel.GroupEditViewModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.view.UserListSelectedDialogScreen
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun GroupEditScreen(
    navController: NavController,
    groupJson: GroupModel
) {
    val viewModel: GroupEditViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.initGroup(groupJson)
    }

    Screen(navController, viewModel)
}

@Composable
private fun Screen(
    navController: NavController,
    viewModel: GroupEditViewModel = hiltViewModel()
) {
    val showDialog by viewModel.isLoading
    val isGroupEdited by viewModel::isGroupEdited
    val isShowError by viewModel::showErrorDialog
    val isShowErrorGetMember by viewModel::showErrorDialogGetMembers
    val isShowErrorInternet by viewModel::showErrorDialogInternet

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            FormEdit(viewModel)

            if (isGroupEdited) {

                LaunchedEffect(Unit) {
                    navController.popBackStack()

                    val gson = GsonBuilder()
                        .setDateFormat("MMM dd, yyyy hh:mm:ss a") // ejemplo: "Nov 10, 1992 12:00:00 AM"
                        .create()

                    val groupJson = URLEncoder.encode(
                        gson.toJson(viewModel.response.value),
                        StandardCharsets.UTF_8.toString()
                    )

                    navController.navigate(
                        "DetailsGrupo/${groupJson}"
                    ){
                        popUpTo("DetailsGrupo") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }

            if (isShowError) {
                SimpleAlertDialog(
                    title = "Error",
                    message = "Error al editar el grupo",
                    buttonNegativeText = "Aceptar"
                ) {
                    viewModel.resetShowErrorDialog()
                }
            }

            if (isShowErrorGetMember) {
                SimpleAlertDialog(
                    title = "Error",
                    message = "Error al obtener los miembros del grupo",
                    buttonNegativeText = "Aceptar"
                ) {
                    viewModel.resetShowErrorGetMembersDialog()
                }
            }

            if (isShowErrorInternet) {
                SimpleAlertDialog(
                    title = "Sin conexión",
                    message = "Verifica tu conexión a Internet",
                    buttonNegativeText = "Aceptar"
                ) {
                    viewModel.resetShowErrorDialogInternet()
                }
            }
        }

        LoadingDialog(showDialog)
    }
}

@Composable
private fun FormEdit(viewModel: GroupEditViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    val selectedMembers = viewModel.listMembersGson
    val membersResult by viewModel.getMembers

    var members by remember { mutableStateOf<List<MembersModel>>(emptyList()) }
    LaunchedEffect(membersResult) {
        membersResult?.onSuccess { memb ->
            if (memb.isNotEmpty()) {
                members = memb
            }
        }
    }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {

        /* -------- NOMBRE -------- */
        Text("Nombre del Grupo", style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Ingresa el nombre") },
            shape = RoundedCornerShape(12.dp)
        )

        viewModel.nameError?.let {
            Text(it, color = Color.Red, style = typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* -------- DESCRIPCIÓN -------- */
        Text("Descripción", style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold))

        OutlinedTextField(
            value = viewModel.descrip,
            onValueChange = { viewModel.descrip = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            maxLines = 5,
            label = { Text("Ingresa una descripción") },
            shape = RoundedCornerShape(12.dp)
        )

        viewModel.descripError?.let {
            Text(it, color = Color.Red, style = typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* -------- MIEMBROS -------- */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Miembros del grupo", style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Person, contentDescription = null)
            }
        }

        UserGroupList(
            membersList = selectedMembers,
            onDelete = { uid ->
                selectedMembers.removeIf { it.uid == uid }
            }
        )

        viewModel.listMembersError?.let {
            Text(it, color = Color.Red, style = typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* -------- BOTONES -------- */

        Button(
            onClick = {
                if (viewModel.validateForm()) {
                    viewModel.editGroup()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar Grupo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { viewModel.closeGroup() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Text("Finalizar / Cerrar Grupo")
        }
    }

    if (showDialog) {
        UserListSelectedDialogScreen(
            show = true,
            membersList = members,
            selectedMembers = selectedMembers,
            onDismiss = { showDialog = false },
            onMemberSelected = {
                selectedMembers.add(it)
                showDialog = false
            }
        )
    }
}


