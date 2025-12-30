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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.GsonBuilder
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MemberDetailsViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.userandpermissions.enums.Permission
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MemberDetailsScreen(member: MembersModel, navController: NavHostController?) {
    Screen(member, navController)
}

@Composable
private fun Screen(
    member: MembersModel,
    navController: NavHostController?,
    viewModel: MemberDetailsViewModel = hiltViewModel()
) {

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
                viewModel,
                onEditClick = {
                    if (navController != null) {
                        navController!!.let {
                            // Gson con formato de fecha personalizado
                            val gson = GsonBuilder()
                                .setDateFormat("MMM dd, yyyy hh:mm:ss a") // ejemplo: "Nov 10, 1992 12:00:00 AM"
                                .create()

                            // Convertir el objeto a JSON
                            val memberJson = URLEncoder.encode(
                                gson.toJson(member),
                                StandardCharsets.UTF_8.toString()
                            )

                            // Navegar pasando el JSON
                            it.navigate("MembersEditScreen/$memberJson")
                        }
                    }
                },
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
                1 -> stringResource(id = R.string.status_pending)
                2 -> stringResource(id = R.string.status_active)
                3 -> stringResource(id = R.string.status_disabled)
                else -> stringResource(id = R.string.status_deleted)
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
            text = stringResource(id = R.string.title_info_contact_member_detail),
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
            title = stringResource(id = R.string.info_tel_member_detail),
            value = member.tel
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.phone_stich,
            title = stringResource(id = R.string.info_tel_contact_member_detail),
            value = member.emergencyContact
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.email_stich,
            title = stringResource(id = R.string.info_email_member_detail),
            value = if (member.email.isNullOrEmpty()) stringResource(id = R.string.info_email2_member_detail) else member.email
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.address_stich,
            title = stringResource(id = R.string.info_address_member_detail),
            value = if (member.address.isNullOrEmpty()) stringResource(id = R.string.info_address2_member_detail) else member.address
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.title_info_aditional_member_detail),
            style = typography.bodyLarge,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.height(10.dp))

        CardViewInfo(
            icon = R.drawable.calendar,
            title = stringResource(id = R.string.info_dateBirth_member_detail),
            value = UtilsFunctions().formatDateInSpanish(member.birthDay)
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.calendar,
            title = stringResource(id = R.string.info_age_member_detail),
            value = stringResource(id = R.string.info_age2_member_detail, UtilsFunctions().ageCalculated(member.birthDay))
        )

        Spacer(modifier = Modifier.height(5.dp))

        CardViewInfo(
            icon = R.drawable.user_stich,
            title = stringResource(id = R.string.info_status_member_detail),
            value = stringResource(id = R.string.info_status2_member_detail)
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
    viewModel: MemberDetailsViewModel,
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

            if (viewModel.can(Permission.EDIT)) {
                Button(
                    onClick = onEditClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F2F4),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.info_button_edit_member_detail))
                }
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
                    Text(text = stringResource(id = R.string.info_button_contact_member_detail))
                }

                // ======== MENÚ QUE SE DESPLIEGA ========
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.info_button_send_email_member_detail)) },
                        onClick = {
                            expanded = false
                            onEmailClick()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.info_button_send_whatsapp_member_detail)) },
                        onClick = {
                            expanded = false
                            onWhatsappClick()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.info_button_send_call_member_detail)) },
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



