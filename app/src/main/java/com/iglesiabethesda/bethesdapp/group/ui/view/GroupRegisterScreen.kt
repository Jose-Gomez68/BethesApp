package com.iglesiabethesda.bethesdapp.group.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.group.ui.viewmodel.GroupRegisterViewModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.view.UserListSelectedDialogScreen
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions

@Composable
fun GroupRegisterScreen(navController: NavController) {
    Screen(navController)
}


@Composable
private fun Screen(navController: NavController,viewModel: GroupRegisterViewModel = hiltViewModel()) {

    val showDialog by viewModel.isLoading
    val isGroupCreated by viewModel::isGroupCreated
    val isShowError by viewModel::showErrorDialog
    val isShowErrorInternet by viewModel::showErrorDialogInternet


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            FormRegister(viewModel)

             if (isGroupCreated) {
                 LaunchedEffect(Unit) {
                     navController.popBackStack()
                 }
             }else if(isShowError) {
                 SimpleAlertDialog(
                     title = "Error",
                     message = "Hubo un error al crear el grupo, intente nuevamente.",
                     buttonNegativeText = "Aceptar"
                 ) {
                     viewModel.resetShowErrorDialog()
                     navController.popBackStack()
                 }
             }else if(isShowErrorInternet) {
                 SimpleAlertDialog(
                     title = "Error Conexion",
                     message = "Hubo un error de conexcion. Verifica tu Internet" +
                             "al conectar nuevamente regrese a esta ventana y despues vuelva al listado " +
                             "paara verificar que se creo el grupo automaticamente.",
                     buttonNegativeText = "Aceptar"
                 ) {
                     viewModel.resetShowErrorDialogInternet()
                     navController.popBackStack()
                 }
             }

        }

        LoadingDialog(showDialog)

    }
}


@Composable
private fun FormRegister(viewModel: GroupRegisterViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    val selectedMembers = remember { mutableStateListOf<MembersModel>() }

    LaunchedEffect(Unit) {
        viewModel.getMember()
    }
    val membersResult by viewModel.getMembers
    var members by remember { mutableStateOf<List<MembersModel>>(emptyList()) }
    membersResult?.onSuccess { memb ->
        if (memb.isNotEmpty()){
            members = memb
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nombre del Grupo", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

        )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Ingresa el Nombre") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.nameError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Descripción", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor1 = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = viewModel.descrip,
            onValueChange = { viewModel.descrip = it },
            label = { Text("Ingresa una descripción") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            maxLines = 5,
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor1,
                unfocusedContainerColor = containerColor1,
                disabledContainerColor = containerColor1,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.descripError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Miembros que pertenecen al Grupo", // Aquí pones el nombre del usuario
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,

                )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .size(48.dp) // Tamaño del botón
                    .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                    .background(Color(0xFFF5F5F5)) // Fondo gris claro
            ) {
                Icon(
                    imageVector = Icons.Default.Person, // Icono de usuario
                    contentDescription = "Seleccionar usuario",
                    tint = Color.Gray // Color gris para mantenerlo minimalista
                )
            }

        }

        Spacer(modifier = Modifier.height(10.dp))
        UserGroupList(
            selectedMembers,
            onDelete = { uid ->
                selectedMembers.removeIf{ it.uid == uid }
            }
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.listMembersError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if(viewModel.validateForm())
                    viewModel.groupRegister()
            },
            modifier = Modifier
                .fillMaxWidth() // Hace que el botón ocupe tod el ancho disponible
                .height(50.dp), // Altura personalizada
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF197FE6), // Color de fondo #197FE6
                contentColor = Color.White // Texto en color blanco
            ),
            shape = RoundedCornerShape(12.dp) // Bordes redondeados
        ) {
            Text(
                text = "Guardar",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }

    /*if (showDialog) {
        UserListSelectedDialogScreen(showDialog, members,
            onDismiss = { showDialog = false } // Cerrar el diálogo al presionar fuera
        )
    }*/
    if (showDialog) {
        UserListSelectedDialogScreen(
            show = showDialog,
            membersList = members,
            selectedMembers = selectedMembers,   // 🔥 Para ocultarlos en el dialogo
            onDismiss = { showDialog = false },
            onMemberSelected = { member ->
                selectedMembers.add(member)   // 🔥 Agregar seleccionado
                viewModel.listMembers.clear()
                viewModel.listMembers.addAll(selectedMembers)
                showDialog = false
            }
        )
    }


}

@Composable
fun UserGroupList(membersList: List<MembersModel>, onDelete: (String) -> Unit) {

    if (membersList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
                //.padding(start = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(membersList) { user ->
                UserItem(
                    user,
                    onDelete = {
                        onDelete(user.uid)
                    }
                )
            }
        }
    }
}

@Composable
private fun UserItem(member: MembersModel, onDelete:() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColorApp)
            .padding(6.dp)
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp)) // Borde redondeado
            .clip(RoundedCornerShape(11.dp))
            .combinedClickable(
                onClick = {

                },
                onLongClick = {

                }
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        UserImage(imageUser = 1)
        UserDescrip(member)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                onDelete()
            },
            modifier = Modifier
                .size(40.dp) // Tamaño del botón
                .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                .background(Color.Red) // Fondo gris claro
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.Delete, // Icono de usuario
                contentDescription = "Eliminar usuario",
                tint = Color.Black, // Color gris para mantenerlo minimalista
                modifier = Modifier.padding(end = 3.dp)
            )
        }
    }

}

@Composable
private fun UserImage(imageUser: Int?) {
    Box(
        modifier = Modifier
            .size(48.dp) // Tamaño del círculo
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_identity),
            contentDescription = "Imagen del Usuario",
            modifier = Modifier.size(25.dp) // Tamaño del ícono dentro del círculo
        )
    }
}


@Composable
private fun UserDescrip(member: MembersModel, modifier: Modifier = Modifier) {


    Column(
        modifier = modifier
            .padding(start = 8.dp, top = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = "${member.name} ${member.apPaterno} ${member.apMaterno}", // Aquí pones el nombre del usuario
            style = typography.bodyLarge,
            color = Color.Black
        )

        Text(
            text = "Sexo: M, Edad: ${UtilsFunctions().ageCalculated(member.birthDay)}", // Aquí pones el sexo y edad
            style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )
    }
}