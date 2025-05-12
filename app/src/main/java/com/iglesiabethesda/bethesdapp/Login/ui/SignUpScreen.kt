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
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import java.util.Calendar

@Composable
fun SignUpScreen() {
    Screen()
}

@Preview
@Composable
private fun Screen() {
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
            FormRegister()
        }

    }
}


@Composable
private fun FormRegister() {

    var etGroupName by remember { mutableStateOf("") }
    var etGroupDescrip by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nombre del Usuario", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Apellido Paterno", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Apellido Materno", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pasa tiempo", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        val containerColor1 = Color(0xFFF5F5F5)
        OutlinedTextField(
            value = etGroupName,
            onValueChange = { etGroupName = it },
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Oficio", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
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
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
            label = { Text("Tel") }, // Label flotante
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
            text = "Contacto", // Aquí pones el nombre del usuario
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = etGroupDescrip,
            onValueChange = { etGroupDescrip = it },
            label = { Text("Contacto de Emergencia") }, // Label flotante
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

        Spacer(modifier = Modifier.height(20.dp))

        BirthdayPicker()

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /*onLoginClick()*/ },
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
fun BirthdayPicker() {
    var selectedDate by remember { mutableStateOf("Selecciona la fecha") }
    var isDatePickerDialogOpen by remember { mutableStateOf(false) }

    val containerColor = Color(0xFFF1F1F1)
    val context = LocalContext.current

    // Mostrar DatePicker al abrir el diálogo
    if (isDatePickerDialogOpen) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val dayFormatted = day.toString().padStart(2, '0')
                val monthFormatted = (month + 1).toString().padStart(2, '0')
                selectedDate = "$dayFormatted/$monthFormatted/$year"
                isDatePickerDialogOpen = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Evitar que se cierre al tocar fuera
        datePickerDialog.setCanceledOnTouchOutside(false)
        datePickerDialog.setOnCancelListener {
            isDatePickerDialogOpen = false // Asegura que se cierre si se cancela manualmente
        }

        datePickerDialog.show()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tu fecha de nacimiento",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        // Caja simulando el OutlinedTextField
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
                text = selectedDate,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

       /* Button(onClick = {
            isDatePickerDialogOpen = true
        }) {
            Text("Confirmar")
        }*/
    }
}
