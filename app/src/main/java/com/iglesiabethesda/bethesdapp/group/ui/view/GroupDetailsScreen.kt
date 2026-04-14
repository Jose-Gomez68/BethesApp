package com.iglesiabethesda.bethesdapp.group.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.GsonBuilder
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.ui.viewmodel.GroupDetailsViewModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundGrayColorApp
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun GroupDetailSceen(
    navController: NavHostController,
    group: GroupModel
) {
    Screen(group, navController)
}

@Composable
private fun Screen(group: GroupModel, navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGrayColorApp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GroupDetailsScreen(group, navController)

        }

    }

}

@Composable
fun GroupDetailsScreen(
    group: GroupModel,
    navController: NavHostController,
    viewModel: GroupDetailsViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.getMember(group.listMembers)
        viewModel.loadGroup(group)
    }

    val membersResult by viewModel.getMembers
    val showProgress by viewModel.isLoading
    var members by remember { mutableStateOf<List<MembersModel>>(emptyList()) }

    membersResult?.onSuccess { memb ->
        if (memb.isNotEmpty()){
            members = memb
        }
    }

        Spacer(Modifier.height(16.dp))

        // Title
        Text(
            viewModel.groupName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111418)
        )

        // Chips
        Row(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFD1FAD6), RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                when (viewModel.statusGroup) {
                    1 -> {
                        Text(
                            stringResource(id = R.string.group_detail_status_text),
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    2 -> {
                        Text(
                            stringResource(id = R.string.group_detail_status_text2),
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    3 -> {
                        Text(
                            stringResource(id = R.string.group_detail_status_text3),
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }


            }


        }

        // Description
        Text(
            viewModel.descrip,
            color = Color(0xFF4B5563),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 12.dp)
        )

        // Section title
        Text(
            "Members (${viewModel.listMember})",
            fontSize = 20.sp,
            color = Color(0xFF111418),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
        )

        // Members Box
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {


                members.forEach { member ->
                    UserItem(
                        member = member,
                        navController = navController
                    )

                    Divider()
                }


            }
        }

        // INFO SECTION
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {

                InfoRow(stringResource(id = R.string.group_detail_created_date), UtilsFunctions().formatDateTo_ddMMyyyy(viewModel.createdDate))

                Divider()

                InfoRow(stringResource(id = R.string.group_detail_end_date), if (viewModel.endDate != null)
                    UtilsFunctions().formatDateTo_ddMMyyyy(viewModel.endDate!!)
                else
                    stringResource(id = R.string.group_detail_end_date2)
                )
            }
        }

        Spacer(Modifier.height(40.dp))

    Button(
        onClick = {
            // Acción al presionar

            val gson = GsonBuilder()
                .setDateFormat("MMM dd, yyyy hh:mm:ss a") // ejemplo: "Nov 10, 1992 12:00:00 AM"
                .create()

            val groupJson = URLEncoder.encode(
                gson.toJson(group),
                StandardCharsets.UTF_8.toString()
            )//REVISAR POR QUE CRASHEA AL PASAR EL DATO
            navController.navigate("EditGrupo/${groupJson}")
        },
        modifier = Modifier
            .fillMaxWidth()   // Hace el botón largo (ocupa todo el ancho)
            .height(50.dp),   // Altura del botón (puedes ajustarla)
        shape = RoundedCornerShape(12.dp) // Bordes redondeados (opcional)
    ) {
        Text(text = stringResource(id = R.string.group_detail_edit_send))
    }

    LoadingDialog(showProgress)

}



@Composable
private fun UserItem(
    member: MembersModel,
    navController: NavHostController?,
    onClick: ((MembersModel) -> Unit)? = null
) {
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
                            it.navigate("MemberDetailsScreen/$memberJson")
                        }
                    }else {
                        if (onClick != null) {
                            onClick(member)  // devolución al padre
                        }
                    }
                },
                onLongClick = {
                }
            )

    ) {
        InitialsAvatar(fullName = "${member.name} ${member.apPaterno} ${member.apMaterno}")
        UserDescrip(member)
    }

}

@Composable
private fun UserDescrip(member: MembersModel) {

    val utilFunction = UtilsFunctions()

    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        // Nombre del usuario
        Text(
            text = "${member.name} ${member.apPaterno} ${member.apMaterno}", // Aquí pones el nombre del usuario
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        // Sexo y Edad del usuario
        Text(
            text = "Edad: ${utilFunction.ageCalculated(member.birthDay)}", // Aquí pones el sexo y edad
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

