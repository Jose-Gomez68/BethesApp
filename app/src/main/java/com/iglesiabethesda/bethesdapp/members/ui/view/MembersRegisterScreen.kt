package com.iglesiabethesda.bethesdapp.members.ui.view

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MemberRegisterViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import java.util.Calendar

//fun MembersRegisterScreen(viewModel: UserRegisterViewModel = hiltViewModel()) {
@Composable
fun MembersRegisterScreen(navController: NavController, viewModel: MemberRegisterViewModel = hiltViewModel()) {
    Screen(navController,viewModel)
}

//private fun Screen(viewModel: UserRegisterViewModel) {
@Composable
private fun Screen(navController: NavController, viewModel: MemberRegisterViewModel) {

    val showDialog by viewModel.isLoading
    val isMemberCreate by remember { viewModel::isMemberCreated }
    val isShowError by remember { viewModel::showErrorDialog }

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

            if (isMemberCreate) {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }else if(isShowError) {
                SimpleAlertDialog(
                    title = "Error",
                    message = "Hubo un error al crear a la persona, intente nuevamente.",
                    buttonNegativeText = "Aceptar"
                ) { }
            }
        }

        LoadingDialog(showDialog)

    }
}


//private fun FormRegister(viewModel: UserRegisterViewModel) {
@Composable
private fun FormRegister(viewModel: MemberRegisterViewModel) {

   /* var etMemberName by remember { mutableStateOf("") }
    var etMemberApPa by remember { mutableStateOf("") }
    var etMemberApMa by remember { mutableStateOf("") }
    var etMemberHobby by remember { mutableStateOf("") }
    var etMemberJob by remember { mutableStateOf("") }
    var etMemberTel by remember { mutableStateOf("") }
    var etMemberEmergency by remember { mutableStateOf("") }
    var etMemberEmail by remember { mutableStateOf("") }
    var etMemberBirthDay by remember { mutableStateOf("") }*/


    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nombre de la Persona", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = viewModel.memberName,
            onValueChange = { viewModel.memberName = it },
            label = { Text("Ingresa el Nombre") }, // Label flotante
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
        viewModel.memberNameError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Apellido Paterno", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberApPa,
            onValueChange = { viewModel.memberApPa = it },
            label = { Text("Ingresa el Apellido Paterno") }, // Label flotante
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
        viewModel.memberApPaError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Apellido Materno", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberApMa,
            onValueChange = { viewModel.memberApMa = it },
            label = { Text("Ingresa el Apellido Materno") }, // Label flotante
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
        viewModel.memberApMaError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pasa tiempo", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor1 = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = viewModel.memberHobby,
            onValueChange = { viewModel.memberHobby = it },
            label = { Text("Descripcion") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            maxLines = 5,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor1,
                unfocusedContainerColor = containerColor1,
                disabledContainerColor = containerColor1,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.memberHobbyError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Oficio", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberJob,
            onValueChange = { viewModel.memberJob = it },
            label = { Text("Oficio (Opcional)") }, // Label flotante
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Telefono", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberTel,
            onValueChange = { viewModel.memberTel = it },
            label = { Text("Tel") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.memberTelError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Contacto (Tel)", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberEmergency,
            onValueChange = { viewModel.memberEmergency = it },
            label = { Text("Contacto de Emergencia") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.memberEmergencyError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Correo Electronico", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberEmail,
            onValueChange = { viewModel.memberEmail = it },
            label = { Text("Email (Opcional)") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
                unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
            )
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.memberEmailError?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Dirección", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberAddress,
            onValueChange = { viewModel.memberAddress = it },
            label = { Text("Dirección (Opcional)") }, // Label flotante
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            maxLines = 5,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor1,
                unfocusedContainerColor = containerColor1,
                disabledContainerColor = containerColor1,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        BirthdayPicker(
            selectedDate = viewModel.memberBirthDay,
            onDateSelected = { viewModel.memberBirthDay = it }
        )

        Spacer(modifier = Modifier.height(3.dp))
        viewModel.memberBirthDayError?.let { error ->
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
                    viewModel.registerMember()
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

@Composable
private fun BirthdayPicker(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    var isDatePickerDialogOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val containerColor = Color(0xFFF1F1F1)

    if (isDatePickerDialogOpen) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val dayFormatted = day.toString().padStart(2, '0')
                val monthFormatted = (month + 1).toString().padStart(2, '0')
                val formattedDate = "$dayFormatted/$monthFormatted/$year"
                onDateSelected(formattedDate) // ⬅️ Notifica al padre
                isDatePickerDialogOpen = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.setCanceledOnTouchOutside(false)
        datePickerDialog.setOnCancelListener {
            isDatePickerDialogOpen = false
        }

        datePickerDialog.show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tu fecha de nacimiento",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor, shape = RoundedCornerShape(12.dp))
                .border(1.dp, Color.Transparent, shape = RoundedCornerShape(12.dp))
                .clickable { isDatePickerDialogOpen = true }
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = if (selectedDate.isNotEmpty()) selectedDate else "Selecciona la fecha",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
