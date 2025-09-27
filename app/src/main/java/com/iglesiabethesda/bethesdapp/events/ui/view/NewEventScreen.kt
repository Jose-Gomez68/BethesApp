package com.iglesiabethesda.bethesdapp.events.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp

@Composable
fun NewEventScreen() {
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

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            FormText()

        }

    }
}

@Composable
private fun FormText() {

    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(0) }

    Text(
        text = stringResource(R.string.tv_title_new_event),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Titulo del Evento") }, // Label flotante
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

    Spacer(Modifier.height(10.dp))

    Text(
        text = stringResource(R.string.tv_subtitle_descrip_new_event),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = text2,
        onValueChange = { text2 = it },
        label = { Text(stringResource(R.string.txt_field_descrip_new_event)) }, // Label flotante
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = Modifier.fillMaxWidth()
            .height(200.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
            unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
        ),
        singleLine = false,
        maxLines = 9
    )

    Spacer(Modifier.height(10.dp))

    Text(
        text = stringResource(R.string.tv_title_priority_new_event),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(8.dp))

    CustomPriorityRadioGroup { selectedValue ->
        priority = selectedValue
        Log.e("aquiii", selectedValue.toString())
    }

    Spacer(Modifier.height(35.dp))

    Button(
        onClick = {  },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1980E6)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(stringResource(R.string.btn_title_save_new_event), color = Color.White, fontWeight = FontWeight.Bold)
    }

}

@Composable
fun CustomPriorityRadioGroup(
    onSelected: (Int) -> Unit // callback que devuelve el número
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val options = listOf("Urgente", "Normal", "Bajo")

    // Colores por prioridad
    val optionColors = mapOf(
        "Urgente" to Color.Red,
        "Normal" to Color(0xFFFFC107), // amarillo
        "Bajo" to Color(0xFF4CAF50) // verde
    )

    // Valores enteros por prioridad
    val optionValues = mapOf(
        "Urgente" to 1,
        "Normal" to 2,
        "Bajo" to 3
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        options.forEach { option ->
            val color = optionColors[option] ?: Color.Gray

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 2.dp,
                        color = if (selectedOption == option) color else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        if (selectedOption == option) color.copy(alpha = 0.2f) else Color.White
                    )
                    .clickable {
                        selectedOption = option
                        onSelected(optionValues[option] ?: 0)
                    }
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = option,
                    color = if (selectedOption == option) color else Color.Black,
                    fontWeight = if (selectedOption == option) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}


