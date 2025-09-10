package com.iglesiabethesda.bethesdapp.Login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.Login.viewmodel.LoginViewModel
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Screen(navController, viewModel)
}

//@Preview
@Composable
private fun Screen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val showErrorDialog by viewModel.showErrorDialog.observeAsState()

    val navigateToHome by viewModel.navigateToDetails.observeAsState()
    val navigateToVerifyAccount by viewModel.navigateToVerifyAccount.observeAsState()
    val viewState by viewModel.viewState.collectAsState()

    // Navegaciones
    LaunchedEffect(navigateToHome) {
        navigateToHome?.getContentIfNotHandle()?.let {
            navController.navigate(Routes.HomeScreen.route)
        }
    }

    LaunchedEffect(navigateToVerifyAccount) {
        navigateToVerifyAccount?.getContentIfNotHandle()?.let {
            navController.navigate(Routes.VerificationScreen.route)
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
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(10.dp))
            HeaderView()
            FormLogin(
                navController,
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onLoginClick = {
                    // Aquí puedes llamar a tu ViewModel
                    println("Login con: $email y $password")
                    viewModel.onFieldsChanged(email, password)
                    // Si algún campo es inválido, NO intentar login
                    val state = viewModel.viewState.value
                    if (!state.isValidEmail || !state.isValidPassword) {
                        return@FormLogin
                    }

                    viewModel.loginUser(email, password)
                    // viewModel.login(email, password)
                },
                viewState
            )
        }
    }

    if (showErrorDialog?.showErrorDialog == true) {
        AlertDialog(
            onDismissRequest = {
                // Ocultar diálogo al cerrarlo
                viewModel.clearErrorDialog()
            },
            title = { Text("Error") },
            text = { Text("El correo o la contraseña son incorrectos") },
            confirmButton = {
                Button(onClick = { viewModel.clearErrorDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    }

}

@Composable
private fun HeaderView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 16.dp)  // Padding fuera del clip
            .clip(RoundedCornerShape(18.dp))  // Clip en el Box para todo el contenido
            .background(Color.White)  // Fondo blanco para evitar transparencias raras

    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))  // Clip también en la imagen para seguridad
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x1A000000),
                            Color(0x66000000)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = (-60).dp)
            ) {
                Text(
                    text = "Welcome back",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black
                )

            }
        }
    }
}
@Composable
private fun FormLogin(
    navController: NavController,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    viewState: LoginViewState
){

    var passwordVisible by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(24.dp))

    // Campo de correo electrónico
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
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
        ),
        isError = !viewState.isValidEmail
    )

    Spacer(modifier = Modifier.height(5.dp))

    if (!viewState.isValidEmail) {
        Text(
            text = "Correo inválido",
            color = Color.Red,
            fontSize = 12.sp
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Campo de contraseña
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("Password", color = Color(0xFF637588)) },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                R.drawable.eye_solid
            else
                R.drawable.eye_slash_solid

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F2F4),
            unfocusedContainerColor = Color(0xFFF0F2F4),
            focusedTextColor = Color(0xFF111418),
            unfocusedTextColor = Color(0xFF111418),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        isError = !viewState.isValidEmail
    )

    Spacer(modifier = Modifier.height(5.dp))

    if (!viewState.isValidPassword) {
        Text(
            text = "La contraseña debe tener al menos 6 caracteres",
            color = Color.Red,
            fontSize = 12.sp
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .clickable {
                navController.navigate(
                Routes.LoginRestPasswordScreen.route
            ) },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Forgot Passsword? ", color = Color(0xFF637588))

    }

    Spacer(modifier = Modifier.height(16.dp))

    // Botones
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onLoginClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1980E6)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Log in", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    navController.navigate(
                        Routes.SignUp.route
                    )
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "New user? ", color = Color(0xFF637588))
            Text(
                text = "Sign Up",
                color = Color(0xFF1980E6),
                fontWeight = FontWeight.Bold
            )
        }

    }

}