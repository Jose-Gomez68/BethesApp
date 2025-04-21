package com.iglesiabethesta.bethestaapp.group.ui.view

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iglesiabethesta.bethestaapp.R
import com.iglesiabethesta.bethestaapp.ui.theme.backgroundColorApp

@Composable
fun GroupScreen() {
    Screen()
}

@Preview
@Composable
fun Screen() {

    val context = LocalContext.current

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
            GroupList()

        }

        FloatingActionButton(
            onClick = { Toast.makeText(context, "Click en nuevo grupo", Toast.LENGTH_SHORT).show() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Agregar")
        }

    }
}


@Composable
fun GroupList() {
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
        items(userList) { user ->
            GroupItem(user)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GroupItem(group: String) {
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
                    Toast.makeText(context, "Click en ${group}", Toast.LENGTH_SHORT).show()
                },
                onLongClick = {
                    Toast.makeText(context, "Long Click ", Toast.LENGTH_SHORT).show()
                }
            )

    ) {
        GroupImage(imageUser = 1)
        GroupDescrip(group)
    }

}

@Composable
private fun GroupImage(imageUser: Int?) {
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
private fun GroupDescrip(user: String) {


    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {

        Text(
            text = "Nombre del Grupo", // Aquí pones el nombre del usuario
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Text(
            text = "Miembros: 10", // Aquí pones el sexo y edad
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color.Gray
        )

        Text(
            text = "Descripcion corta: Grupo de instrumentos.....",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color.Gray
        )
    }
}
