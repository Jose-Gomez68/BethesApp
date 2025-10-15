package com.iglesiabethesda.bethesdapp.events.ui.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import com.iglesiabethesda.bethesdapp.events.ui.viewmodel.EventScreenViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog2
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventScreen(navController: NavHostController) {
    Screen(navController)
}

@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Screen(
    navController: NavHostController,
    viewModel: EventScreenViewModel = hiltViewModel()
) {

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    LaunchedEffect(Unit) {
        viewModel.getEvents(currentMonth.year, currentMonth.monthValue)
    }

    val eventResult by viewModel.getEvents
    val eventDeleteResult by viewModel.getEventsDelete
    val showProgress by viewModel.isLoading
    // variable para guardar los eventos
    var eventt by remember { mutableStateOf<List<EventModel>>(emptyList()) }

    // actualizar la variable cuando la petición sea exitosa
    eventResult?.onSuccess { events ->
        if (events.isNotEmpty()) {
            eventt = events
        }
    }


    //Harcode de eventos
    val events = listOf(
        CalendarEvent(LocalDate.of(2025, 9, 22), "Reunión urgente", "Con el equipo de ventas", 1),
        CalendarEvent(LocalDate.of(2025, 9, 10), "Entrega del informe", "Informe de análisis de datos", 2),
        CalendarEvent(LocalDate.of(2025, 9, 15), "Taller interno", "Tema: productividad", 3),
        CalendarEvent(LocalDate.of(2025, 4, 20), "Taller interno", "Tema: productividadaa", 3),
        CalendarEvent(LocalDate.of(2025, 4, 15), "Taller interno", "Tema: productividad2", 1),
        CalendarEvent(LocalDate.of(2025, 4, 20), "Taller interno", "Tema: productividad3", 2),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SimpleCalendarScreen(
                eventt,
                refreshEvents = { year, month ->
                    Log.e("AQUII", "ANO "+year)
                    Log.e("AQUII", "MES "+month)
                    viewModel.getEvents(year, month)
                },
                onDeleteEventSelected = {
                    viewModel.deleteEventByUid(it!!.uidEvent)
                }
            )
        }

        FloatingActionButton(
            onClick = { navController.navigate("NewEvent") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Agregar")
        }

        LoadingDialog(showProgress)

        if (eventDeleteResult){
            SimpleAlertDialog(
                title = "Ups!",
                message = "No se pudo eliminar el evento \n favor de contactar con soporte.",
                buttonNegativeText = "Cerrar",
                onDismiss = {  }
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimpleCalendarScreen(
    events: List<EventModel>,
    refreshEvents: (year: Int, month: Int) -> Unit,
    onDeleteEventSelected: (EventModel?) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val selectedEvent = remember { mutableStateOf<EventModel?>(null) }
    val showDialogDeleteEvent = remember { mutableStateOf<EventModel?>(null) }

    val daysOfWeek = DayOfWeek.values()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Header con flechas y nombre del mes
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                currentMonth = currentMonth.minusMonths(1)
                refreshEvents(currentMonth.year, currentMonth.monthValue)
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Mes anterior")
            }

            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    .replaceFirstChar { it.uppercase() } + " ${currentMonth.year}",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(onClick = {
                currentMonth = currentMonth.plusMonths(1)
                refreshEvents(currentMonth.year, currentMonth.monthValue)
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
            }
        }

        Spacer(Modifier.height(8.dp))

        // Días de la semana
        Row(Modifier.fillMaxWidth()) {
            for (day in daysOfWeek) {
                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Generar días del mes
        val firstDayOfMonth = currentMonth.atDay(1)
        val lastDayOfMonth = currentMonth.atEndOfMonth()
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        val totalDays = lastDayOfMonth.dayOfMonth
        val totalGridCells = ((firstDayOfWeek + totalDays + 6) / 7) * 7

        val daysList = (1..totalGridCells).map { index ->
            val dayOffset = index - firstDayOfWeek
            if (dayOffset in 0 until totalDays) {
                currentMonth.atDay(dayOffset + 1)
            } else {
                null
            }
        }

        // Calendario visual
        Column {
            daysList.chunked(7).forEach { week ->
                Row(Modifier.fillMaxWidth()) {
                    week.forEach { date ->
                        val hasEvents = date != null && events.any { it.dateEvent.toLocalDate() == date }
                        val isToday = date == LocalDate.now()
                        val isFutureOrToday = date != null && !date.isBefore(LocalDate.now())

                        val background = when {
                            hasEvents && isFutureOrToday-> {
                                val eventPriorities = events.filter { it.dateEvent.toLocalDate() == date }.map { it.priorityEvent }
                                val highestPriority = eventPriorities.minOrNull()
                                when (highestPriority) {
                                    1 -> Color.Red.copy(alpha = 0.3f)
                                    2 -> Color.Yellow.copy(alpha = 0.3f)
                                    3 -> Color.Green.copy(alpha = 0.3f)
                                    else -> Color.Green.copy(alpha = 0.3f)
                                }
                            }
                            isToday -> Color.Gray.copy(alpha = 0.2f) // gris translúcido para día actual sin eventos
                            else -> Color.Transparent
                        }

                        val isSelected = date == selectedDate
                        val isValidDay = date != null && date.month == currentMonth.month

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .background(
                                    color = background,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.Blue else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = isValidDay) {
                                    selectedDate = date ?: LocalDate.now()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = date?.dayOfMonth?.toString() ?: "",
                                    color = if (date?.month == currentMonth.month) Color.Black else Color.LightGray
                                )
                                if (isSelected) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Color.Blue)
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Lista de eventos del día seleccionado
        ListEventDays(
            selectedDate,
            events,
            onEventSelected = { selectedEvent.value = it },
            onDeleteEvent = {
                showDialogDeleteEvent.value = it
            }
        )
    }

    showDialogDeleteEvent.value?.let { event ->
        SimpleAlertDialog2(
            title = "Eliminar Evento ${event.titleEvent}",
            message = "Deseas eliminar el evento seleccionado?",
            buttonNegativeText = "No",
            buttonPositiveeText = "Eliminar",
            onConfirm = {
                onDeleteEventSelected(event)
                showDialogDeleteEvent.value = null
                refreshEvents(currentMonth.year, currentMonth.monthValue)
            },
            onDismiss = { showDialogDeleteEvent.value = null }
        )
    }
    // Diálogo de evento al hacer clic en un evento listado
    DescriptionEvetsDialog(selectedEvent)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ListEventDays(
    selectedDate: LocalDate?,
    events: List<EventModel>,
    onEventSelected: (EventModel) -> Unit,
    onDeleteEvent: (EventModel) -> Unit
) {

    selectedDate?.let { date ->
        val eventsOfDay = events.filter { it.dateEvent.toLocalDate() == date }
        if (eventsOfDay.isNotEmpty()) {
            Text(
                text = "Eventos del ${date.dayOfMonth}/${date.monthValue.toString().padStart(2, '0')}/${date.year}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            eventsOfDay.forEach { event ->
                val backgroundColor = when (event.priorityEvent) {
                    1 -> Color(0xFFFFCDD2) // rojo claro
                    2 -> Color(0xFFFFF9C4) // amarillo claro
                    3 -> Color(0xFFC8E6C9) // verde claro
                    else -> Color.LightGray
                }

                //.clickable { onEventSelected(event) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .combinedClickable (
                            onClick = { onEventSelected(event) },
                            onLongClick = { onDeleteEvent(event) }
                        ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(text = event.titleEvent, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }else {

            Text(
                text = "Eventos del ${date.dayOfMonth}/${date.monthValue.toString().padStart(2, '0')}/${date.year}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Sin eventos registrados",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)  // Espaciado para separar del calendario
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

}

@Composable
private fun DescriptionEvetsDialog(
    selectedEvent: MutableState<EventModel?>
) {

    selectedEvent.value?.let { event ->
        AlertDialog(
            onDismissRequest = { selectedEvent.value = null },
            title = { Text(event.titleEvent) },
            text = { Text(event.descriptionEvent) },
            confirmButton = {
                TextButton(onClick = { selectedEvent.value = null }) {
                    Text("Cerrar")
                }
            }
        )
    }

}

fun Date.toLocalDate(): LocalDate =
    this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()


//pasar el modelo a una clase
data class CalendarEvent(
    val date: LocalDate,
    val title: String,
    val description: String,
    val priority: Int // 1 = urgente, 2 = media, 3 = baja
)

