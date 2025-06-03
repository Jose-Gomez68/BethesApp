package com.iglesiabethesda.bethesdapp.Login.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.Login.viewmodel.VerificationViewModel
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.navigationcompose.Routes
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.GifImageLocal

/*
* @Composable
fun VerificationScreen(viewModel: VerificationViewModel = viewModel()) {
    Screen(viewModel)
}
*/

@Composable
fun VerificationScreen(navController: NavController,
                       viewModel: VerificationViewModel = hiltViewModel()) {
    Screen(navController, viewModel)
}

/*
* private fun Screen(viewModel: VerificationViewModel = viewModel()) {*/

//@Preview(showBackground = true)
@Composable
private fun Screen(navController: NavController,viewModel: VerificationViewModel) {

    val showContinueEvent = viewModel.showContinueButton.observeAsState()
    val navigateEvent = viewModel.navigateToVerifyAccount.observeAsState()

    /*PARA CUANDO SE CONFIRME LA CUENTA SE VA AL HOME AUTOMTICAMENTE
    LaunchedEffect(showContinueEvent.value?.peekContent()) {
        if (showContinueEvent.value?.peekContent() == true) {
            navController.navigate(Routes.HomeScreen.route) {
                popUpTo(Routes.VerificationScreen.route) {
                    inclusive = true
                }
            }
        }
    }*/

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
            Spacer(Modifier.height(20.dp))
            HeaderView(viewModel.getCurrentUserEmail().toString())
            Spacer(Modifier.height(50.dp))
            GifImageLocal(drawableId = R.drawable.reloj_arena,modifier = Modifier.size(110.dp))


            Spacer(modifier = Modifier.weight(0.1f))
            if (showContinueEvent.value?.getContentIfNotHandle() == true) {
                Button(
                    onClick = {
                        navController.navigate(Routes.HomeScreen.route) {
                            popUpTo(Routes.VerificationScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 60.dp)
                ) {
                    Text("Contiuar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
                    Spacer(modifier = Modifier.weight(0.1f))

        }
    }
}

@Composable
private fun HeaderView(email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Para continuar debes verificar la cuenta.",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Te hemos enviado un correo a la dirección $email " +
                    "donde deberás confirmar la cuenta que has creado." +
                    "\nUna vez confirmada la cuenta se iniciara sesion ",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}


