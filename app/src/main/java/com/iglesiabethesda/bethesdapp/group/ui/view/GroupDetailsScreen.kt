package com.iglesiabethesda.bethesdapp.group.ui.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.GsonBuilder
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.ui.viewmodel.GroupDetailsViewModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.ui.viewmodel.MembersViewModel
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundColorApp
import com.iglesiabethesda.bethesdapp.ui.theme.backgroundGrayColorApp
import com.iglesiabethesda.bethesdapp.util.InitialsAvatar
import com.iglesiabethesda.bethesdapp.util.LoadingDialog
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun GroupDetailSceen(
    navController: NavHostController,
    group: GroupModel
) {
    Screen(group, navController)
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Screen(group: GroupModel, navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGrayColorApp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GroupDetailsScreen(group, navController)

        }

    }

}

@Composable
fun GroupDetailsScreen(
    group: GroupModel,
    navController: NavHostController,
    viewModel: GroupDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onMore: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.getMember(group.listMembers)
    }

    val membersResult by viewModel.getMembers
    val showProgress by viewModel.isLoading
    var members by remember { mutableStateOf<List<MembersModel>>(emptyList()) }

    membersResult?.onSuccess { memb ->
        if (memb.isNotEmpty()){
            members = memb
        }
    }
   /*mover o dividir en fuciones todo */

        Spacer(Modifier.height(16.dp))

        // Title
        Text(
            group.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111418)
        )

        // Chips
        Row(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFD1FAD6), RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                when (group.statusGroup) {
                    1 -> {
                        Text(
                            "Active",
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    2 -> {
                        Text(
                            "Desactivado",
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    3 -> {
                        Text(
                            "Finalizado",
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }


            }
        }

        // Description
        Text(
            group.description,
            color = Color(0xFF4B5563),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 12.dp)
        )

        // Section title
        Text(
            "Members (${group.listMembers.size})",
            fontSize = 20.sp,
            color = Color(0xFF111418),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
        )

        // Members Box
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {


                members.forEach { member ->
                    UserItem(
                        member = member,
                        navController = navController
                    )

                    Divider()
                }

                /*TODO
                *  pasar el listado de los miembros desdde el viewModel*/
                //InitialsAvatar(fullName = group.name, modifier = Modifier.size(100.dp))
                // MEMBER 1
                /*MemberItemImage(
                    name = "John Smith",
                    role = "Leader",
                    imageUrl = "https://chisellabs.com/glossary/wp-content/uploads/2023/05/962b45f9-e2a6-4f59-8f0a-9e1e1a1d1f7f.png"
                )
                Divider(Modifier.padding(horizontal = 16.dp))

                // MEMBER 2
                MemberItemImage(
                    name = "Sarah Johnson",
                    role = "Member",
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBY72uvgLEPCtC9Ql6LXA0kgLM9v6vhywxv5fMa9of1SLm_uBnPumwNBTDe2DeRApwhmOdvi_ckFm7gpez5NdwC4YcePeAZLgIFBvzJD5ZOk6Z-Sf59cUKQbTPhScdzd-wR-aozjcyiGyKgLHi99UXEMUYusT4qxHddkwFjPDZptPVECSu4aCZgZQn557Gfe2reYR_uX8QGA0-NiIl7EEwlInRbDcBs8P8JCmqRUSbHZZHY85ukHsgzaVzq2MegYxsxtkkDJvwDaow"
                )
                Divider(Modifier.padding(horizontal = 16.dp))

                // MEMBER 3 (INITIALS)
                MemberItemInitials(
                    name = "Michael Brown",
                    initials = "MB"
                )
                Divider(Modifier.padding(horizontal = 16.dp))

                // MEMBER 4
                MemberItemImage(
                    name = "Chris Lee",
                    role = "Member",
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCn3YcFjlsF406YvI0y9lBrswXIrZ9KAlz6j5ba4LMT198VU_PfY4vMDrgbXFT38e7bdQPd6AaWJBIAHeysdql9GSIAlDqqK8PGDlwLfMitpmvoTD7e0LAw5X-Rtu4vr_lpiovU8uyDHDx1cdvbHVpyIdgoaekyx9PzFrYhNot62VFbLLk-OIvGDkr5SmsK8TVPw1ZKjPOwVoGW65YgkTV7qtJssj1KBmDYuFicoxA79Fmd0YO1KRXu4H6p4SxNWuMOA26mqD60fis"
                )
                Divider(Modifier.padding(horizontal = 16.dp))

                // MEMBER 5 (INITIALS)
                MemberItemInitials(
                    name = "James Wilson",
                    initials = "JW",
                    bg = Color(0xFFEDE9FE),
                    fg = Color(0xFF6D28D9)
                )*/
            }
        }

        // INFO SECTION
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {

                InfoRow("Created On", UtilsFunctions().formatDateTo_ddMMyyyy(group.createdDate))

                Divider()

                InfoRow("End Date", if (group.endDate != null)
                    UtilsFunctions().formatDateTo_ddMMyyyy(group.endDate)
                else
                    "En curso"
                )
            }
        }

        Spacer(Modifier.height(40.dp))

    LoadingDialog(showProgress)

}



@Composable
private fun UserItem(
    member: MembersModel,
    navController: NavHostController?,
    onClick: ((MembersModel) -> Unit)? = null
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorApp)
            .padding(8.dp)
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp)) // Borde redondeado
            .clip(RoundedCornerShape(11.dp))
            .combinedClickable(
                onClick = {
                    if (navController != null) {
                        navController!!.let {
                            // Gson con formato de fecha personalizado
                            val gson = GsonBuilder()
                                .setDateFormat("MMM dd, yyyy hh:mm:ss a") // ejemplo: "Nov 10, 1992 12:00:00 AM"
                                .create()

                            // Convertir el objeto a JSON
                            val memberJson = URLEncoder.encode(
                                gson.toJson(member),
                                StandardCharsets.UTF_8.toString()
                            )

                            // Navegar pasando el JSON
                            it.navigate("MemberDetailsScreen/$memberJson")
                        }
                    }else {
                        if (onClick != null) {
                            onClick(member)  // 🔥 devolución al padre
                        }
                    }
                },
                onLongClick = {
                    Toast.makeText(context, "Long Click ", Toast.LENGTH_SHORT).show()
                }
            )

    ) {
        //UserImage(imageUser = 1)// no se usa imagen ya que no se pagara el Storage
        InitialsAvatar(fullName = "${member.name} ${member.apPaterno} ${member.apMaterno}")
        UserDescrip(member)
    }

}

@Composable
private fun UserDescrip(member: MembersModel) {

    val utilFunction = UtilsFunctions()

    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        // Nombre del usuario
        Text(
            text = "${member.name} ${member.apPaterno} ${member.apMaterno}", // Aquí pones el nombre del usuario
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        // Sexo y Edad del usuario
        Text(
            text = "Edad: ${utilFunction.ageCalculated(member.birthDay)}", // Aquí pones el sexo y edad
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), // Tamaño más pequeño
            color = Color.Gray
        )
    }
}

@Composable
fun MemberItemImage(name: String, role: String = "Member", imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = name,
            modifier = Modifier.size(48.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(role, color = Color(0xFF1173D4), fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )

    }
}

@Composable
fun MemberItemInitials(
    name: String,
    initials: String,
    bg: Color = Color(0x331173D4),
    fg: Color = Color(0xFF1173D4)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Text(initials, fontWeight = FontWeight.Bold, color = fg, fontSize = 18.sp)
        }

        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text("Member", fontSize = 14.sp, color = Color.Gray)
        }

        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
}



@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

