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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp

@Composable
fun LoginScreen() {
    Screen()
}

@Preview
@Composable
private fun Screen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onLoginClick = {
                    // Aquí puedes llamar a tu ViewModel
                    println("Login con: $email y $password")
                    // viewModel.login(email, password)
                }
            )
        }
    }
}

@Composable
private fun HeaderView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp)//agrege esto recientemente
            .background(Color.Transparent),//agrege esto recientemente
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // usa el nombre real de tu imagen
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(16.dp))//agrege esto recientemente
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome back",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "We're glad to see you again. Sign in with your email and password.",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun FormLogin(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
){
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
        )
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Campo de contraseña
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("Password", color = Color(0xFF637588)) },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F2F4),
            unfocusedContainerColor = Color(0xFFF0F2F4),
            focusedTextColor = Color(0xFF111418),
            unfocusedTextColor = Color(0xFF111418),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .clickable { /*onClick()*/ },
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
                .clickable { /*onClick()*/ },
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