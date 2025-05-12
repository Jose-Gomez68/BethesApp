package com.iglesiabethesda.bethesdapp.events.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventScreen() {
    Screen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Screen() {

    //Harcode de eventos
    val events = listOf(
        CalendarEvent(LocalDate.of(2025, 4, 5), "Reunión urgente", "Con el equipo de ventas", 1),
        CalendarEvent(LocalDate.of(2025, 4, 10), "Entrega del informe", "Informe de análisis de datos", 2),
        CalendarEvent(LocalDate.of(2025, 4, 15), "Taller interno", "Tema: productividad", 3),
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
            SimpleCalendarScreen(events)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimpleCalendarScreen(events: List<CalendarEvent>) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val selectedEvent = remember { mutableStateOf<CalendarEvent?>(null) }

    val daysOfWeek = DayOfWeek.values()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header con flechas y nombre del mes
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    .replaceFirstChar { it.uppercase() } + " ${currentMonth.year}",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = null)
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
                        val hasEvents = date != null && events.any { it.date == date }
                        val isToday = date == LocalDate.now()
                        val isFutureOrToday = date != null && !date.isBefore(LocalDate.now())

                        val background = when {
                            hasEvents && isFutureOrToday-> {
                                val eventPriorities = events.filter { it.date == date }.map { it.priority }
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
        ListEventDays(selectedDate, events, onEventSelected = { selectedEvent.value = it })
    }

    // Diálogo de evento al hacer clic en un evento listado
    DescriptionEvetsDialog(selectedEvent)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ListEventDays(
    selectedDate: LocalDate?,
    events: List<CalendarEvent>,
    onEventSelected: (CalendarEvent) -> Unit
) {

    selectedDate?.let { date ->
        val eventsOfDay = events.filter { it.date == date }
        if (eventsOfDay.isNotEmpty()) {
            Text(
                text = "Eventos del ${date.dayOfMonth}/${date.monthValue.toString().padStart(2, '0')}/${date.year}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            eventsOfDay.forEach { event ->
                val backgroundColor = when (event.priority) {
                    1 -> Color(0xFFFFCDD2) // rojo claro
                    2 -> Color(0xFFFFF9C4) // amarillo claro
                    3 -> Color(0xFFC8E6C9) // verde claro
                    else -> Color.LightGray
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onEventSelected(event) },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(text = event.title, style = MaterialTheme.typography.titleSmall)
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
    selectedEvent: MutableState<CalendarEvent?>
) {

    selectedEvent.value?.let { event ->
        AlertDialog(
            onDismissRequest = { selectedEvent.value = null },
            title = { Text(event.title) },
            text = { Text(event.description) },
            confirmButton = {
                TextButton(onClick = { selectedEvent.value = null }) {
                    Text("Cerrar")
                }
            }
        )
    }

}


//pasar el modelo a una clase
data class CalendarEvent(
    val date: LocalDate,
    val title: String,
    val description: String,
    val priority: Int // 1 = urgente, 2 = media, 3 = baja
)

