package com.iglesiabethesda.bethesdapp.Login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.Login.viewmodel.ResetPasswordUiState
import com.iglesiabethesda.bethesdapp.Login.viewmodel.ResetPasswordViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp

@Composable
fun LoginResPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
    ) {
    Screen(
        navController,
        viewModel
    )
}

//@Preview
@Composable
private fun Screen(
    navController: NavController,
    viewModel: ResetPasswordViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))
            Body(
                navController,
                viewModel
            )
        }
    }

}

@Composable
private fun Body(
    navController: NavController,
    viewModel: ResetPasswordViewModel
){

    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }

    // Escucha el estado para navegar o mostrar mensajes
    when (uiState) {
        is ResetPasswordUiState.Success -> {
            // Regresamos y reiniciamos el estado para que no lo vuelva a disparar
            LaunchedEffect(Unit) {
                viewModel.resetState()
                navController.popBackStack()
            }
        }

        is ResetPasswordUiState.InvalidEmail -> {
            // Muestra mensaje de error por email inválido
            LaunchedEffect(Unit) {
                // Aquí puedes usar un Snackbar o Toast
                // Toast.makeText(context, "Email no válido", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
        }

        else -> Unit
    }

    Text(
        text = "Ingresa tu correo electronico asociado," +
                "Se enviara un correo a la cuenta que ingresaste" +
                "con una liga para que puedas generar una nueva contraseña",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textAlign = TextAlign.Center,
        color = Color(0xFF637588)
    )

    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        value = email,
        onValueChange = {email = it},
        placeholder = { Text("Email", color = Color(0xFF637588)) },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F2F4),
            unfocusedContainerColor = Color(0xFFF0F2F4),
            focusedTextColor = Color(0xFF111418),
            unfocusedTextColor = Color(0xFF111418),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.height(80.dp))
    Button(
        onClick = { /*onLoginClick()*/ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1980E6)),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text("Enviar Email", color = Color.White, fontWeight = FontWeight.Bold)
    }

    if (uiState is ResetPasswordUiState.Loading) {
        Spacer(modifier = Modifier.height(24.dp))
        CircularProgressIndicator()
    }

    if (uiState is ResetPasswordUiState.Error) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ocurrió un error al enviar el correo.",
            color = Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

}