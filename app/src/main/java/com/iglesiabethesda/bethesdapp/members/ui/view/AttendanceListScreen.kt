package com.iglesiabethesda.bethesdapp.members.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceItem
import com.iglesiabethesda.bethesdapp.members.domain.model.MemberAttendance
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersListModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.AttendanceListViewModel
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import java.util.Date

val PrimaryBlue = Color(0xFF1173D4)
val BackgroundLight = Color(0xFFF6F7F8)
val CardGray = Color(0xFFF0F2F5)
val TextSecondary = Color(0xFF617589)
val BorderColor = Color(0xFFDBE0E6)

@Preview(showBackground = true)
@Composable
fun AttendanceListScreen(
    navController: NavHostController,
    member: MembersListModel,// VERIFICAR COMO PASAR EL LISTADO DE GRUPOS A MI PASE DE LISTA
    groupUId: String
    //viewModel: AttendanceListViewModel = hiltViewModel()
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        //viewModel.getMember()
        Log.e("AQUI", member.toString())
    }

   /* val membersResult by viewModel.getMembers
    val showProgress by viewModel.isLoading*/

    var members by remember { mutableStateOf<List<MembersModel>>(emptyList()) }
    var searchQuery by remember {
        mutableStateOf("")
    }

    /*membersResult?.onSuccess { memb ->
        if (memb.isNotEmpty()){
            members = memb
        }
    }*/



    var attendanceList by remember {
        mutableStateOf<List<MemberAttendance>>(emptyList())
    }

    /*LaunchedEffect(membersResult) {

        if (attendanceList.isEmpty()) {

            membersResult?.onSuccess { list ->

                attendanceList = list.map {
                    MemberAttendance(member = it)
                }

            }

        }
    }*/

    LaunchedEffect(member) {

        if (attendanceList.isEmpty()) {

            attendanceList = member.list.map {
                MemberAttendance(member = it)
            }

        }
    }

    /*val filteredMembers = if (searchQuery.isNotBlank()) {
        members.filter { member ->
            // Aquí defines los campos donde buscar
            member.name.contains(searchQuery, ignoreCase = true) ||
                    member.apPaterno.contains(searchQuery, ignoreCase = true) ||
                    member.apMaterno.contains(searchQuery, ignoreCase = true)
        }
    } else {
        members
    }*/

    val filteredMembers = if (searchQuery.isNotBlank()) {
        attendanceList.filter { member ->
            // Aquí defines los campos donde buscar
            member.member.name.contains(searchQuery, ignoreCase = true) ||
                    member.member.apPaterno.contains(searchQuery, ignoreCase = true) ||
                    member.member.apMaterno.contains(searchQuery, ignoreCase = true)
        }
    } else {
        attendanceList
    }

    val membersMock = listOf(
        MembersModel(
            uid = "1",
            name = "Juan",
            apPaterno = "Pérez",
            apMaterno = "García",
            hobby = "Fútbol",
            job = "Ingeniero",
            tel = "9621234567",
            address = "Tapachula, Chiapas",
            emergencyContact = "María Pérez - 9627654321",
            email = "juan.perez@email.com",
            birthDay = Date(),
            statusAccount = 1,
            createdDate = Date(),
            updateDate = Date()
        ),
        MembersModel(
            uid = "2",
            name = "Ana",
            apPaterno = "López",
            apMaterno = "Martínez",
            hobby = "Lectura",
            job = "Maestra",
            tel = "9621112233",
            address = "Tuxtla Gutiérrez, Chiapas",
            emergencyContact = "José López - 9623334455",
            email = "ana.lopez@email.com",
            birthDay = Date(),
            statusAccount = 1,
            createdDate = Date(),
            updateDate = Date()
        ),
        MembersModel(
            uid = "3",
            name = "Carlos",
            apPaterno = "Hernández",
            apMaterno = "Ruiz",
            hobby = "Música",
            job = "Contador",
            tel = "9624445566",
            address = "Tapachula, Chiapas",
            emergencyContact = "Laura Hernández - 9627778899",
            email = "carlos.hernandez@email.com",
            birthDay = Date(),
            statusAccount = 1,
            createdDate = Date(),
            updateDate = Date()
        ),
        MembersModel(
            uid = "4",
            name = "María",
            apPaterno = "Gómez",
            apMaterno = "Castillo",
            hobby = "Pintura",
            job = "Diseñadora",
            tel = "9629876543",
            address = "Huixtla, Chiapas",
            emergencyContact = "Pedro Gómez - 9621122334",
            email = "maria.gomez@email.com",
            birthDay = Date(),
            statusAccount = 1,
            createdDate = Date(),
            updateDate = Date()
        ),
        MembersModel(
            uid = "5",
            name = "Luis",
            apPaterno = "Ramírez",
            apMaterno = "Morales",
            hobby = "Videojuegos",
            job = "Programador",
            tel = "9625556677",
            address = "Tapachula, Chiapas",
            emergencyContact = "Carmen Ramírez - 9628889900",
            email = "luis.ramirez@email.com",
            birthDay = Date(),
            statusAccount = 1,
            createdDate = Date(),
            updateDate = Date()
        )
    )

    AttendanceScreen(
        filteredMembers,
        searchQuery,
        onSearchQueryChange = {
            searchQuery = it
        },
        onCheckedChange = { uid, checked ->

            attendanceList = attendanceList.map {

                if (it.member.uid == uid)
                    it.copy(isPresent = checked)
                else
                    it
            }
        },
        onSelectAll = { checked ->

            attendanceList = attendanceList.map {
                it.copy(isPresent = checked)
            }

        },
        groupUId
    )
}

@Composable
fun AttendanceScreen(
    members: List<MemberAttendance>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCheckedChange: (String, Boolean) -> Unit,
    onSelectAll: (Boolean) -> Unit,
    groupUId: String,
    viewModel: AttendanceListViewModel = hiltViewModel()
) {

    val total = members.size
    val present = members.count { it.isPresent }
    val absent = members.count { !it.isPresent }
    viewModel.uidGroup = groupUId
    var listPresent = members.map {
        AttendanceItem(
            it.member.uid,
            it.isPresent,
            it.isLate
        )

    }
    viewModel.listAttendanceGson.clear()
    viewModel.listAttendanceGson.addAll(listPresent)

        Scaffold(
            containerColor = BackgroundLight,

            /*topBar = {
                AttendanceTopBar()
            },*/

            bottomBar = {
                SaveAttendanceButton(members, viewModel)
            }
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                item {
                    DateSelector()
                }

                item {
                    AttendanceStats(present, absent, total)// filtros
                }

                item {
                    SearchAttendance(
                        searchQuery = searchQuery,
                        onSearchQueryChange = onSearchQueryChange
                    )
                }

                item {
                    SelectAllCard(
                        members,
                        onSelectAll = onSelectAll
                    )
                }

                item {
                    Text(
                        text = "REGISTERED MEMBERS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        )
                    )
                }

                items(members) { member ->
                    MemberItem(
                        member,
                        onCheckedChange = onCheckedChange
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceTopBar() {

    CenterAlignedTopAppBar(

        title = {
            Text(
                text = "Attendance",
                fontWeight = FontWeight.Bold
            )
        },

        navigationIcon = {

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector() {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(UtilsFunctions().formatDateTo_ddMMyyyy(Date()))
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            //se comento por que no se podra editar
            //expanded = !expanded
        }
    ) {

        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Service Date")
            },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            listOf(
                "Sunday, Oct 24, 2023",
                "Sunday, Oct 17, 2023",
                "Sunday, Oct 10, 2023"
            ).forEach {

                DropdownMenuItem(
                    text = {
                        Text(it)
                    },
                    onClick = {
                        selectedDate = it
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AttendanceStats(
    membersPresent: Int,
    membersAbsent: Int,
    membersTotal: Int
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        StatCard(
            value = "${membersPresent}",
            label = "Present",
            icon = Icons.Default.CheckCircle,
            color = PrimaryBlue,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            value = "${membersAbsent}",
            label = "Absent",
            icon = Icons.Default.Close,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            value = "${membersTotal}",
            label = "Total",
            icon = Icons.Default.Person,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    value: String,
    label: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = CardGray
        ),
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = label.uppercase(),
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SearchAttendance(searchQuery: String, onSearchQueryChange: (String) -> Unit) {

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,// no recibe el searech
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                null
            )
        },
        placeholder = {
            Text("Search by name...")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun SelectAllCard(
    member: List<MemberAttendance>,
    onSelectAll: (Boolean) -> Unit
) {

    var checked by remember {
        mutableStateOf(false)
    }

    val allSelected = member.isNotEmpty() &&
            member.all { it.isPresent }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    "Select All Members",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Mark everyone as present",
                    color = TextSecondary
                )
            }

            Checkbox(
                checked = allSelected,
                onCheckedChange = { checked ->
                    onSelectAll(checked)
                }
            )
        }
    }
}

@Composable
fun MemberItem(
    member: MemberAttendance,
    onCheckedChange: (String, Boolean) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp
            ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            /*AsyncImage(
                model = member.name,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )*/

            InitialsAvatar(fullName = "${member.member.name} ${member.member.apPaterno} ${member.member.apMaterno}", modifier = Modifier.size(50.dp))

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    "${member.member.name} ${member.member.apPaterno} ${member.member.apMaterno}",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    member.member.job,
                    color = TextSecondary
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        null
                    )
                }

                Text(
                    "Late",
                    fontSize = 10.sp
                )
            }

            Checkbox(
                checked = member.isPresent,
                onCheckedChange = { checked ->
                    onCheckedChange(member.member.uid, checked)
                }
            )
        }
    }
}

@Composable
fun SaveAttendanceButton(members: List<MemberAttendance>, viewModel: AttendanceListViewModel) {

    Button(
        onClick = {
            viewModel.saveAttenfanceList(members)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue
        )
    ) {

        Text(
            "Guardar lista",
            fontWeight = FontWeight.Bold
        )
    }
}