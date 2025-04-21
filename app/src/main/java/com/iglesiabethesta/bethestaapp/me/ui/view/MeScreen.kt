package com.iglesiabethesta.bethestaapp.me.ui.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iglesiabethesta.bethestaapp.R
import com.iglesiabethesta.bethestaapp.ui.theme.backgroundColorApp

@Composable
fun MeScreen() {
    Screen()
}

@Preview
@Composable
fun Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            PerfilName()

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /* Acción al presionar el botón */ },
                modifier = Modifier
                    .fillMaxWidth() // Hace que el botón ocupe todo el ancho disponible
                    .height(40.dp)
                    .padding(start = 30.dp, end = 30.dp), // Altura personalizada
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f), // Color de fondo #197FE6
                    contentColor = Color.Black // Texto en color blanco
                ),
                shape = RoundedCornerShape(12.dp) // Bordes redondeados
            ) {
                Text(
                    text = "Editar",
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Details()
            Spacer(modifier = Modifier.height(20.dp))
            ChurchActivity()


        }
    }
}


@Composable
private fun PerfilName () {
    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ){
        Row (
            modifier = Modifier
                .wrapContentSize()
                .background(backgroundColorApp)
                .padding(8.dp)

        ){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Imagen del Usuario",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = "Nombre del Usuario",
                    style = typography.bodyLarge,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Sexo: M, Edad: 25",
                    style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
                    color = Color.Gray
                )

                Text(
                    text = "Fecha de Nacimiento: 06-noviembre-1996",
                    style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
                    color = Color.Gray
                )
            }

        }
    }
}

@Composable
private fun Details() {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ){

        Text(
            text = "Detalles",
            style = typography.bodyLarge,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Row (
            modifier = Modifier
                .wrapContentSize()
                .background(backgroundColorApp)
                .padding(8.dp)

        ){
            Box( // Usamos Box para centrar solo el Icon
                modifier = Modifier
                    .size(40.dp) // Ajusta el tamaño si es necesario
                    .align(Alignment.CenterVertically), // Centra solo el Icon en el eje vertical
                        contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "ic"
                )
            }


            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = "Nombre del Usuario",
                    style = typography.bodySmall,
                    color = Color.Black,
                    fontSize = 18.sp,
                )


            }

        }

        Row (
            modifier = Modifier
                .wrapContentSize()
                .background(backgroundColorApp)
                .padding(8.dp)

        ){
            Box( // Usamos Box para centrar solo el Icon
                modifier = Modifier
                    .size(40.dp) // Ajusta el tamaño si es necesario
                    .align(Alignment.CenterVertically), // Centra solo el Icon en el eje vertical
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "ic"
                )
            }


            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = "Pasatiempo favorito es Armar rompecabezas",
                    style = typography.bodySmall,
                    color = Color.Black,
                    fontSize = 18.sp,
                )


            }

        }

        Row (
            modifier = Modifier
                .wrapContentSize()
                .background(backgroundColorApp)
                .padding(8.dp)

        ){
            Box( // Usamos Box para centrar solo el Icon
                modifier = Modifier
                    .size(40.dp) // Ajusta el tamaño si es necesario
                    .align(Alignment.CenterVertically), // Centra solo el Icon en el eje vertical
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "ic"
                )
            }


            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = "Trabajo en Desarrollando Software",
                    style = typography.bodySmall,
                    color = Color.Black,
                    fontSize = 18.sp,
                )


            }

        }

    }

}

@Composable
private fun ChurchActivity() {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ){

        Text(
            text = "Actividades en la Iglesia",
            style = typography.bodyLarge,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

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
        ) {
            items(userList) { user ->
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 10.dp),
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Text(
                        text = "Community Group",
                        style = typography.bodyLarge,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Every Thursday 7:00PM",
                        style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
                        color = Color.Gray
                    )

                }
            }
        }



        /*Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 10.dp),
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = "PlayerRequest",
                style = typography.bodyLarge,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Every Thursday 7:00PM",
                style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
                color = Color.Gray
            )

        }*/

    }

}