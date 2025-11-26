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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.Login.viewmodel.LoginViewModel
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig

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
    val showNetworkErrorDialog by viewModel.showErrorNetworkDialog.observeAsState(false)
    val showDisabledDialog by viewModel.showDisableAccountDialog.observeAsState(false)

    val navigateToHome by viewModel.navigateToDetails.observeAsState()
    val navigateToVerifyAccount by viewModel.navigateToVerifyAccount.observeAsState()
    val viewState by viewModel.viewState.collectAsState()
    val getUser by viewModel.getUserModel.observeAsState()
    val getMember by viewModel.getMemberModel.observeAsState()
    val context = LocalContext.current
    val sharedPrf = SharedPreferencesConfig(context)

    // Navegaciones
    LaunchedEffect(navigateToHome) {
        if (navigateToHome?.getContentIfNotHandle() == true) {
            snapshotFlow { Pair(getUser, getMember) }
                .collect { (user, member) ->
                    if (user != null && member != null) {
                        sharedPrf.saveUserUid(user.uid)
                        sharedPrf.saveUserName(user.nickName)
                        sharedPrf.saveMemberUid(member.uid)
                        sharedPrf.saveMemberName("${member.name} ${member.apPaterno} ${member.apMaterno}")
                        sharedPrf.saveEmail(user.email)
                        sharedPrf.saveBirthDay(member.birthDay.toString())
                        sharedPrf.saveHobby(member.hobby)
                        sharedPrf.saveJob(member.job)
                        navController.navigate(Routes.HomeScreen.route)
                    }
                }
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
                onEmailChange = { email = it.trim() },
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

                    viewModel.loginUser(email.trim(), password)
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

    if (showNetworkErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                // Ocultar diálogo al cerrarlo
                viewModel.clearNetworkErrorDialog()
            },
            title = { Text("Error") },
            text = { Text("Verifique la conexción a internet") },
            confirmButton = {
                Button(onClick = { viewModel.clearNetworkErrorDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    if (showDisabledDialog) {
        AlertDialog(
            onDismissRequest = {
                // Ocultar diálogo al cerrarlo
                viewModel.clearNetworkErrorDialog()
            },
            title = { Text("Inhabilitado") },
            text = { Text("Tu cuenta ha sido inhabilitada por el administrador. ponte" +
                    "en contacto con el admin para mas información") },
            confirmButton = {
                Button(onClick = { viewModel.clearDisnableDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    if (viewState.isLoading) {
        LoginLoadingDialog()
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

@Composable
fun LoginLoadingDialog() {
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
                Text("Iniciando sesión...", style = typography.bodyMedium)
            }
        }
    }
}