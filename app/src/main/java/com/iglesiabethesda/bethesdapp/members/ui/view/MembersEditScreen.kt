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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MemberUpdateViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.SimpleAlertDialog
import java.util.Calendar

@Composable
fun MembersEditScreen(
    navController: NavController,
    member: MembersModel
) {
    ScreenEdit(navController, member)
}

@Composable
private fun ScreenEdit(
    navController: NavController,
    member: MembersModel,
    viewModel: MemberUpdateViewModel = hiltViewModel()
) {

    val showDialog by viewModel.isLoading
    val isMemberUpdated by remember { viewModel::isMemberUpdate }   // puedes cambiar el nombre a isMemberUpdated
    val isShowError by remember { viewModel::showErrorDialog }

    LaunchedEffect(member) {
        viewModel.loadMember(member)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormEdit(viewModel)

            if (isMemberUpdated) {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            } else if (isShowError) {
                SimpleAlertDialog(
                    title = stringResource(id = R.string.dialog_error_title_member_edit),
                    message = stringResource(id = R.string.dialog_error_message_member_edit),
                    buttonNegativeText = stringResource(id = R.string.dialog_error_positivebutton_member_edit)
                ) { }
            }
        }

        LoadingDialog(showDialog)
    }
}

@Composable
private fun FormEdit(
    viewModel: MemberUpdateViewModel
) {

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.title_member_edit),
            style = typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = Color.Black
        )
        Spacer(Modifier.height(20.dp))

        // Misma UI que FormRegister
        val containerColor = Color(0xFFF5F5F5)
        val containerColor1 = Color(0xFFF5F5F5)

        /** NOMBRE **/
        Text(
            stringResource(id = R.string.form_title_name_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberName,
            onValueChange = { viewModel.memberName = it },
            label = { Text(stringResource(id = R.string.form_hint_name_member_edit)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberNameError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** APELLIDO PATERNO **/
        Text(
            stringResource(id = R.string.form_title_apP_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberApPa,
            onValueChange = { viewModel.memberApPa = it },
            label = { Text(stringResource(id = R.string.form_hint_apP_member_edit)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberApPaError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** APELLIDO MATERNO **/
        Text(
            stringResource(id = R.string.form_title_apM_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberApMa,
            onValueChange = { viewModel.memberApMa = it },
            label = { Text(stringResource(id = R.string.form_hint_apM_member_edit)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberApMaError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** HOBBY **/
        Text(
            stringResource(id = R.string.form_title_hobby_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberHobby,
            onValueChange = { viewModel.memberHobby = it },
            label = { Text(stringResource(id = R.string.form_hint_hobby_member_edit)) },
            maxLines = 5,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor1,
                unfocusedContainerColor = containerColor1,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberHobbyError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** OFICIO **/
        Text(
            stringResource(id = R.string.form_title_job_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberJob,
            onValueChange = { viewModel.memberJob = it },
            label = { Text(stringResource(id = R.string.form_hint_job_member_edit)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(20.dp))

        /** TELÉFONO **/
        Text(
            stringResource(id = R.string.form_title_tel_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberTel,
            onValueChange = { viewModel.memberTel = it },
            label = { Text(stringResource(id = R.string.form_hint_tel_member_edit)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberTelError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** CONTACTO EMERGENCIA **/
        Text(
            stringResource(id = R.string.form_title_tel_emergency_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberEmergency,
            onValueChange = { viewModel.memberEmergency = it },
            label = { Text(stringResource(id = R.string.form_hint_tel_emergency_member_edit)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberEmergencyError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** CORREO **/
        Text(
            stringResource(id = R.string.form_title_email_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberEmail,
            onValueChange = { viewModel.memberEmail = it },
            label = { Text(stringResource(id = R.string.form_hint_email_member_edit)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )
        viewModel.memberEmailError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(20.dp))

        /** DIRECCIÓN **/
        Text(
            stringResource(id = R.string.form_title_address_member_edit),
            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.memberAddress,
            onValueChange = { viewModel.memberAddress = it },
            label = { Text(stringResource(id = R.string.form_hint_address_member_edit)) },
            maxLines = 5,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor1,
                unfocusedContainerColor = containerColor1,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(20.dp))

        /** FECHA DE NACIMIENTO **/
        BirthdayPicker(
            selectedDate = viewModel.memberBirthDay,
            onDateSelected = { viewModel.memberBirthDay = it }
        )
        viewModel.memberBirthDayError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(Modifier.height(30.dp))

        /** BOTÓN ACTUALIZAR **/
        Button(
            onClick = {
                if (viewModel.validateForm()) {
                    viewModel.updateMember()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1980E6)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                stringResource(id = R.string.form_button_title_update_member_edit),
                color = Color.White, fontWeight = FontWeight.Bold
            )
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
            text = stringResource(id = R.string.form_title_birth_day_member_edit),
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
                text = if (selectedDate.isNotEmpty()) selectedDate else stringResource(id = R.string.form_hint_birth_day_member_edit),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}


