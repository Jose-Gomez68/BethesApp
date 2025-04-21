package com.iglesiabethesta.bethestaapp.members.ui.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iglesiabethesta.bethestaapp.R
import com.iglesiabethesta.bethestaapp.ui.theme.backgroundColorApp

@Composable
fun MembersRegisterScreen() {
    Screen()
}

@Preview
@Composable
private fun Screen() {
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
            FormRegister()
        }

    }
}


@Composable
private fun FormRegister() {

    var etGroupName by remember { mutableStateOf("") }
    var etGroupDescrip by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

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
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Descripción", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor1 = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = etGroupName,
            onValueChange = { etGroupName = it },
            label = { Text("Ingresa el Nombre") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            maxLines = 5,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor1,
                unfocusedContainerColor = containerColor1,
                disabledContainerColor = containerColor1,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

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
        UserGroupList()
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /* Acción al presionar el botón */ },
            modifier = Modifier
                .fillMaxWidth() // Hace que el botón ocupe todo el ancho disponible
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

    if (showDialog) {
        UserListSelectedDialogScreen(showDialog,
            onDismiss = { showDialog = false } // Cerrar el diálogo al presionar fuera
        )
    }

}

@Composable
private fun UserGroupList() {
    val userList = listOf(
        "Juan Pérez - M, 30",
        "María López - F, 25",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35",
        "Carlos García - M, 35"
    )

    if (userList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.7f)
                .padding(start = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(userList) { user ->
                UserItem(user)
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserItem(user: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
            .padding(8.dp)
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp)) // Borde redondeado
            .clip(RoundedCornerShape(11.dp))
            .combinedClickable(
                onClick = {
                    Toast.makeText(context, "Click en ${user}", Toast.LENGTH_SHORT).show()
                },
                onLongClick = {
                    Toast.makeText(context, "Long Click ", Toast.LENGTH_SHORT).show()
                }
            )

    ) {
        UserImage(imageUser = 1)
        UserDescrip(user)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { /* Acción para seleccionar usuario */ },
            modifier = Modifier
                .size(48.dp) // Tamaño del botón
                .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                .background(Color.Red) // Fondo gris claro
        ) {
            Icon(
                imageVector = Icons.Default.Delete, // Icono de usuario
                contentDescription = "Eliminar usuario",
                tint = Color.Black // Color gris para mantenerlo minimalista
            )
        }
    }

}

@Composable
private fun UserImage(imageUser: Int?) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Imagen del Usuario",
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
private fun UserDescrip(user: String) {


    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = "Nombre del Usuario", // Aquí pones el nombre del usuario
            style = typography.bodyLarge,
            color = Color.Black
        )

        Text(
            text = "Sexo: M, Edad: 25", // Aquí pones el sexo y edad
            style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )
    }
}