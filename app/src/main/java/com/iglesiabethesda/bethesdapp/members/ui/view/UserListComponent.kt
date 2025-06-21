package com.iglesiabethesda.bethesdapp.members.ui.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar

@Composable
fun UsersList() {
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
        items(userList) { user ->
            UserItem(user)
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
        //UserImage(imageUser = 1)// no se usa imagen ya que no se pagara el Storage
        InitialsAvatar(fullName = "Jose Gomez")
        UserDescrip(user)
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
        // Nombre del usuario
        Text(
            text = "Nombre del Usuario", // Aquí pones el nombre del usuario
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        // Sexo y Edad del usuario
        Text(
            text = "Sexo: M, Edad: 25", // Aquí pones el sexo y edad
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )
    }
}