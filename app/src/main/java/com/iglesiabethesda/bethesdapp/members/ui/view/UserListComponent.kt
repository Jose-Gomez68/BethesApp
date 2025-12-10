package com.iglesiabethesda.bethesdapp.members.ui.view

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.GsonBuilder
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun UsersListNoDelete(
    membersList: List<MembersModel>,
    navController: NavHostController? = null,
    onClick: ((MembersModel) -> Unit)? = null
) {
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(membersList) { member ->
            UserItem(member, navController, onClick)
        }
    }
}

/**
 * AQUI USAMOS EL DELETE DESLIZABLE*/
@Composable
fun UsersList(
    membersList: List<MembersModel>,
    navController: NavHostController?,
    onDelete: (MembersModel) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(
            items = membersList,
            key = { it.uid ?: it.hashCode() } //asegura que la clave sea unica
        ) { member ->
            SwipeableUserItem(
                member = member,
                navController = navController,
                onDelete = onDelete
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
                            onClick(member)  // 🔥 devolución al padre
                        }
                    }
                },
                onLongClick = {
                    Toast.makeText(context, "Long Click ", Toast.LENGTH_SHORT).show()
                }
            )

    ) {
        //UserImage(imageUser = 1)// no se usa imagen ya que no se pagara el Storage
        InitialsAvatar(fullName = "${member.name} ${member.apPaterno} ${member.apMaterno}")
        UserDescrip(member)
    }

}

@Composable
fun SwipeableUserItem(
    member: MembersModel,
    navController: NavHostController?,
    onDelete: (MembersModel) -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete(member)  // sólo abrir el diálogo
            }
            false // nunca confirmar el swipe
        }
    )


    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // Solo derecha → izquierda
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.basura_100),
                    contentDescription = "Delete",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(end = 20.dp)
                )
            }
        }

    ) {
        UserItem(member = member, navController = navController, onClick = null)
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