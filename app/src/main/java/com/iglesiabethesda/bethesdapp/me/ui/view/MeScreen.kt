package com.iglesiabethesda.bethesdapp.me.ui.view

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.me.ui.model.UserUiStateModel
import com.iglesiabethesda.bethesdapp.me.ui.viewmodel.LogoutState
import com.iglesiabethesda.bethesdapp.me.ui.viewmodel.MeScreenViewModel
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions

@Composable
fun MeScreen(viewModel: MeScreenViewModel = hiltViewModel(),
    navController: NavController) {
    Screen(viewModel, navController)
}


@Composable
fun Screen(viewModel: MeScreenViewModel, navController: NavController) {

    val logoutState by viewModel.logoutState.collectAsState()
    val context = LocalContext.current
    val userDataUiState by viewModel.uiState.collectAsState()
    /*val userName by viewModel.userName.collectAsState()
    val name by viewModel.name.collectAsState()
    val email by viewModel.userEmail.collectAsState()*/

    when(logoutState) {
        is LogoutState.Loading -> {
            //mostrar dialog de carga
            Log.e("CARGANDO","")
            LogoutLoadingDialog()
        }

        is LogoutState.Success -> {
            // Limpiar el onLogut
            LaunchedEffect(Unit) {
                navController.navigate(Routes.LoginScreen.route) {
                    popUpTo(0) // Limpia todo el historial de navegación
                    viewModel.clearPreferences()
                    launchSingleTop = true
                }
            }
        }

        is LogoutState.Error -> {
            val message = (logoutState as LogoutState.Error).message
            LaunchedEffect(message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        LogoutState.Idle -> {
            // No hacer nada
        }

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            PerfilName(userDataUiState)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /* Acción al presionar el botón */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Editar",
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Details(userDataUiState)
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Aquí agregamos la lista como múltiples elementos de los grupos
        items(11) { index ->
            ChurchActivity("Carlos García - M, 35") // Aquí puedes adaptar
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.logout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.2f),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(40.dp)) // espacio al final
        }
    }
}

/*
* fun Screen() {
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
}*/
@Preview
@Composable
private fun PerfilName(userDataUiState: UserUiStateModel) {
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
            /*Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Imagen del Usuario",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )*/ //no se usara imagen ya que no se pagara Storage

            InitialsAvatar(fullName = userDataUiState.name, modifier = Modifier.size(100.dp))

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = userDataUiState.userName,
                    style = typography.bodyLarge,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Sexo: M, Edad: ${UtilsFunctions().parseDateFlexible(userDataUiState.birthDay)
                        ?.let { UtilsFunctions().ageCalculated(it) }}",
                    style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
                    color = Color.Gray
                )

                Text(
                    text = "Fecha de Nacimiento: ${UtilsFunctions().parseDateFlexible(userDataUiState.birthDay)
                        ?.let { UtilsFunctions().formatDateInSpanish(it) }}",
                    style = typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
                    color = Color.Gray
                )
            }

        }
    }
}

@Composable
private fun Details(userDataUiState: UserUiStateModel) {

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
                    text = userDataUiState.name,
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
                    text = "Pasatiempo: ${userDataUiState.hobby}",
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
                    text = "Trabajo: ${userDataUiState.job}",
                    style = typography.bodySmall,
                    color = Color.Black,
                    fontSize = 18.sp,
                )


            }

        }

    }

}

@Composable
fun LogoutLoadingDialog() {
    Dialog(
        onDismissRequest = {}, // No permite que el usuario lo cierre manualmente
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .width(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    color = Color.Red,
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Cerrando sesión...", style = typography.bodyMedium)
            }
        }
    }
}



@Composable
private fun ChurchActivity(text: String) {

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .fillMaxWidth()
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
            style = typography.bodySmall.copy(fontSize = 12.sp),
            color = Color.Gray
        )
    }

}

/*
* private fun ChurchActivity() {

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
* */
