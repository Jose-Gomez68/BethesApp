package com.iglesiabethesda.bethesdapp.Login.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.Login.viewmodel.SignUpViewModel
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import java.util.Calendar

/**
 * ICONOS GRATIS ANIMADOS https://iconos8.es/icons/set/church-loading--animated*/
@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {

    /**
     * CONDICION PARA CUANDO SE INICIE SESION SABER
     * A DONDE MANDAR SI AL
     *
     * ACTUALIZAR EL CAMPO DE statusAccount
     * CUANDO SE CONFIRME LA CUENTA DESDE EL CORREO
     * RECUERDA QUE IRA LA PANTALLA DE CARGA HASTA QUE VERIFIQUEMOS LA CUENTA
     * AHI HAREMOS UN UPDATE A LA USERACCOUNT*/


    Screen(navController, viewModel)
}

//@Preview
@Composable
private fun Screen(navController: NavController, viewModel: SignUpViewModel) {

    val isUserCreated = viewModel.isUserCreated

    if (isUserCreated) {
        LaunchedEffect(Unit) {
            navController.navigate(Routes.VerificationScreen.route) {
                popUpTo(Routes.SignUp.route) { inclusive = true } // Opcional: evita volver atrás
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            FormRegister(viewModel)
        }

    }
}


@Composable
private fun FormRegister(viewModel: SignUpViewModel) {

    /*var etSignupMemberCode by remember { mutableStateOf("") }
    var etSignupName by remember { mutableStateOf("") }
    var etSignupEmail by remember { mutableStateOf("") }
    var etSignupRepeatEmail by remember { mutableStateOf("") }
    var etSignupPass by remember { mutableStateOf("") }
    var etSignupRepeatPass by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }*/

    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Codigo de Miembro", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = viewModel.etSignupMemberCode,
            onValueChange = { viewModel.etSignupMemberCode = it },
            label = { Text("Ingresa el Codigo") }, // Label flotante
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
        viewModel.memberCodeError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Correo Electronico", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.etSignupEmail,
            onValueChange = { viewModel.etSignupEmail = it },
            label = { Text("Ingresa el Correo Electronico") }, // Label flotante
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
        viewModel.emailError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Contraseña", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.etSignupPass,
            onValueChange = { viewModel.etSignupPass = it },
            label = { Text("Ingresa una Contraseña") }, // Label flotante
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
        viewModel.passwordError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Repita Contraseña", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.etSignupRepeatPass,
            onValueChange = { viewModel.etSignupRepeatPass = it },
            label = { Text("Repita Contraseña") }, // Label flotante
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
        viewModel.repeatPasswordError?.let { error ->
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
                if (viewModel.validateForm()){
                    viewModel.registerUserAccount()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1980E6)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Registrar", color = Color.White, fontWeight = FontWeight.Bold)
        }

    }

}

