package com.iglesiabethesda.bethesdapp.events.ui.view

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserModel
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.events.ui.viewmodel.NewEventScreenViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NewEventScreen(navController: NavController) {
    Screen(navController)
}

@Composable
private fun Screen(
    navController: NavController,
    viewModel: NewEventScreenViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState() // estado del scroll
    val isEventCreate by remember { viewModel::isEventCreated }
    val isShowError by remember { viewModel::showErrorDialog }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ){

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
        ) {

            FormText(viewModel)

            if (isEventCreate){
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }else if(isShowError) {
                SimpleAlertDialog(
                    title = stringResource(id = R.string.title_alert_dialog_error_new_event),
                    message = stringResource(id = R.string.message_alert_dialog_error_new_event),
                    buttonNegativeText = stringResource(id = R.string.buttonpostiive_alert_dialog_error_new_event)
                ) { }
            }

        }

    }
}

@Composable
private fun FormText(viewModel: NewEventScreenViewModel) {

    var selectedDate by remember { mutableStateOf<String>("") }
    var showDatePcikerModal by remember { mutableStateOf(false) }
    var showUsersSelected by remember { mutableStateOf(false) }

    Text(
        text = stringResource(R.string.tv_title_new_event),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = viewModel.etTitleEvent,
        onValueChange = { viewModel.etTitleEvent = it },
        label = { Text(stringResource(R.string.tv_title_hint_new_event)) }, // Label flotante
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.Black,          // Color de la letra
            fontWeight = FontWeight.Bold   // Peso de la letra (gordita)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedBorderColor = Color.Blue, // Color del borde cuando está seleccionado
            unfocusedBorderColor = Color.Transparent, // Color del borde cuando no está seleccionado
        )
    )

    Spacer(modifier = Modifier.height(3.dp))
    viewModel.etTitleEventError?.let { error ->
        Text(
            text = error,
            color = Color.Red,
            style = typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )
    }

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
        value = viewModel.etDescriptionEvent,
        onValueChange = { viewModel.etDescriptionEvent = it },
        label = { Text(stringResource(R.string.txt_field_descrip_new_event)) }, // Label flotante
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

    Spacer(modifier = Modifier.height(3.dp))
    viewModel.etDescriptionEventError?.let { error ->
        Text(
            text = error,
            color = Color.Red,
            style = typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )
    }

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
        viewModel.etEventPriority = selectedValue
    }

    Spacer(modifier = Modifier.height(3.dp))
    viewModel.etEventPriorityError?.let { error ->
        Text(
            text = error,
            color = Color.Red,
            style = typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )
    }

    Spacer(Modifier.height(10.dp))

    Text(
        text = stringResource(R.string.tv_date_event_new_event),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDatePcikerModal = true
            }
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { }, // no se cambia directamente
            label = { Text(stringResource(R.string.tv_date_event_hint_new_event)) },
            shape = RoundedCornerShape(12.dp),
            enabled = false, // deshabilitamos edición
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }

    Spacer(modifier = Modifier.height(3.dp))
    viewModel.etDateEventError?.let { error ->
        Text(
            text = error,
            color = Color.Red,
            style = typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )
    }

    Spacer(Modifier.height(10.dp))

    Text(
        text = stringResource(R.string.tv_notify_title_new_event),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(8.dp))

    SwitchAllUsersNotify(
        check = viewModel.notifyAllUsers,
        onChecked = {
            viewModel.notifyAllUsers = it
            showUsersSelected = it
        }
    )

    if (!showUsersSelected) {
        Spacer(Modifier.height(8.dp))

        SelectUsersScreen(viewModel)
    }

    Spacer(Modifier.height(35.dp))


    Button(
        onClick = {
            if (viewModel.validateForm()){
                viewModel.registerEvent()
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1980E6)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(stringResource(R.string.btn_title_save_new_event), color = Color.White, fontWeight = FontWeight.Bold)
    }

    if (showDatePcikerModal) {
        ShowDatePickerDialog(
            onDateSelected = {

                showDatePcikerModal = false
                selectedDate = it
                viewModel.etDateEvent = it
            },
            onDismiss = { showDatePcikerModal = false }
        )
    }

    LoadingDialog(viewModel.isLoading)

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

@Composable
private fun ShowDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay, hour, minute)

            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = formatter.format(selectedCalendar.time)

            onDateSelected(formattedDate)
        },
        year, month, day
    )

    datePickerDialog.datePicker.minDate = calendar.timeInMillis

    // Listener que se llama al cerrar el diálogo
    datePickerDialog.setOnDismissListener {
        onDismiss()
    }

    datePickerDialog.show()
}

@Composable
fun SwitchAllUsersNotify(
    check: Boolean,
    onChecked: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(R.string.tv_switch_title_new_event),
            modifier = Modifier.weight(1f)
        )
        Switch(checked = check, onCheckedChange = onChecked)
    }
}

@Composable
fun UserSelectionScreen(
    users: List<UserModel>,
    onSelectionChange: (List<UserModel>) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedUserIds by remember { mutableStateOf(mutableSetOf<String>()) }

    // Filtrar usuarios según búsqueda por realName
    val filteredUsers = users.filter {
        it.realName.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
    {

        // 🔍 Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar usuario...") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 📋 Lista de usuarios con CheckBox
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
            items(filteredUsers) { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val newSet = selectedUserIds.toMutableSet()
                            if (user.uid in newSet) newSet.remove(user.uid)
                            else newSet.add(user.uid)
                            selectedUserIds = newSet
                            onSelectionChange(users.filter { it.uid in selectedUserIds })
                        }
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedUserIds.contains(user.uid),
                        onCheckedChange = { checked ->
                            val newSet = selectedUserIds.toMutableSet()
                            if (checked) newSet.add(user.uid)
                            else newSet.remove(user.uid)
                            selectedUserIds = newSet
                            onSelectionChange(users.filter { it.uid in selectedUserIds })
                        }
                    )
                    Text(
                        text = user.realName,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Divider()
            }
        }
    }
}


@Composable
fun SelectUsersScreen(viewModel: NewEventScreenViewModel) {
    // Lista de prueba de usuarios
    val users = listOf(
        UserModel("uid1","María López","mlp","maria@mail.com",1, Date(), Date()),
        UserModel("uid2","José Ramírez","jr","jose@mail.com",1, Date(), Date()),
        UserModel("uid3","Laura González","lg","laura@mail.com",1, Date(), Date()),
        UserModel("uid4","Carlos Pérez","cp","carlos@mail.com",1, Date(), Date())
    )

    UserSelectionScreen(
        users = users,
        onSelectionChange = { selected ->
            viewModel.setUsers(selected.map { it.uid }) // Guardas solo los uid en el ViewModel
        }
    )
}

