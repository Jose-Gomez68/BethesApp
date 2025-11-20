package com.iglesiabethesda.bethesdapp.members.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MemberDetailsViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions

@Preview
@Composable
fun MemberDetailsScreen(member: MembersModel) {
    Screen(member)
}

@Composable
private fun Screen(member: MembersModel, viewModel: MemberDetailsViewModel = hiltViewModel()) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PerfilName(member)

            ContactInf(member)

            ActionButtonsRow(
                onEditClick = {  },
                onEmailClick = {
                    if (!member.email.isNullOrEmpty())
                        viewModel.sendEmail(context, member.email)
                },
                onWhatsappClick = {
                    viewModel.openWhatsApp(context, member.tel)
                },
                onCallClick = { viewModel.callPhone(context, member.tel) }
            )

        }

    }

}


@Composable
private fun PerfilName(member: MembersModel) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center
    ){
        Row (
            modifier = Modifier
                .wrapContentSize()
                .background(backgroundColorApp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)

        ){
            /*Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Imagen del Usuario",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )*/ //no se usara imagen ya que no se pagara Storage

            InitialsAvatar(fullName = "${member.name} ${member.apPaterno} ${member.apMaterno}", modifier = Modifier.size(100.dp))

        }

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "${member.name} ${member.apPaterno} ${member.apMaterno}",
            style = typography.bodyLarge,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = when (member.statusAccount) {
                1 -> "Cuenta Pendiente"
                2 -> "Cuenta Activa"
                3 -> "Cuenta Desactivada"
                else -> "Cuenta Eliminada"
            }, // modificar saber cual estatus es activo
            style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )
    }
}

@Composable
private fun ContactInf(member: MembersModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ){

        Text(
            text = "Informacion de Contacto",
            style = typography.bodyLarge,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.height(10.dp))

       /* Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Miembro Activo",
            style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )*/

        //Spacer(modifier = Modifier.height(8.dp))
        CardViewInfo(
            icon = R.drawable.phone_stich,
            title = "Telefono",
            value = member.tel
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.phone_stich,
            title = "Telefono de Contacto",
            value = member.emergencyContact
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.email_stich,
            title = "Email",
            value = if (member.email.isNullOrEmpty()) "no hay un correo registrado" else member.email
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.address_stich,
            title = "Direccion",
            value = if (member.address.isNullOrEmpty()) "sin dirección" else member.address
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Informacion Adicional",
            style = typography.bodyLarge,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.height(10.dp))

        CardViewInfo(
            icon = R.drawable.calendar,
            title = "Fecha de Nacimiento",
            value = "${UtilsFunctions().formatDateInSpanish(member.birthDay)}"
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.user_stich,
            title = "Estatus",
            value = "Activo como Miembro"
        )



    }

}

@Composable
private fun CardViewInfo(
    @DrawableRes icon: Int,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColorApp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // centra icono y texto verticalmente
    ) {
        // Ícono a la izquierda
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(55.dp) // tamaño pequeño del icono
                .padding(end = 8.dp)
        )

        // Textos a la derecha
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = value,
                fontSize = 13.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
private fun ActionButtonsRow(
    onEditClick: () -> Unit,
    onEmailClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    onCallClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onEditClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F2F4),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Editar")
            }

            // ======= BOTÓN CONTACTAR =======
            Box {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3792F8),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Contactar")
                }

                // ======== MENÚ QUE SE DESPLIEGA ========
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("Enviar Email") },
                        onClick = {
                            expanded = false
                            onEmailClick()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("WhatsApp") },
                        onClick = {
                            expanded = false
                            onWhatsappClick()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Llamar") },
                        onClick = {
                            expanded = false
                            onCallClick()
                        }
                    )
                }
            }
        }
    }
}



