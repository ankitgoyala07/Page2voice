package com.example.page2voice.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.page2voice.R
import com.example.page2voice.ui.theme.Accent
import com.example.page2voice.ui.theme.Background
import com.example.page2voice.ui.theme.Border
import com.example.page2voice.ui.theme.ChipBackground
import com.example.page2voice.ui.theme.Dimens
import com.example.page2voice.ui.theme.MainText
import com.example.page2voice.ui.theme.Page2VoiceTheme
import com.example.page2voice.ui.theme.Primary
import com.example.page2voice.ui.theme.SecondaryText
import com.example.page2voice.ui.theme.Surface

class MyLibraryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Page2VoiceTheme {
                MyLibraryBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLibraryBody() {
    val activity = LocalActivity.current
    var selectedFilter by remember { mutableIntStateOf(0) }
    var selectedNavIndex by remember { mutableIntStateOf(1) }
    var isPlaying by remember { mutableStateOf(true) }
    val filters = listOf("All", "PDFs", "Scanned", "Summary")

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Library", Icons.Default.Book),
        BottomNavItem("History", Icons.Default.History),
        BottomNavItem("Profile", Icons.Default.Person)
    )

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    titleContentColor = MainText,
                    navigationIconContentColor = Color.Unspecified
                ),
                title = {
                    Text("Page2Voice")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.p2v_logo),
                            contentDescription = "Logo",
                            tint = Color.Unspecified
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notification")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Surface
            ) {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedNavIndex == index,
                        onClick = { selectedNavIndex = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.screenPadding)
        ) {
            Text(
                text = "My Library",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MainText,
                modifier = Modifier.padding(top = Dimens.sm)
            )

            Text(
                text = "All your saved documents in one place.",
                fontSize = 14.sp,
                color = SecondaryText
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.buttonRadius))
                    .background(Surface)
                    .border(1.dp, Border, RoundedCornerShape(Dimens.buttonRadius))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = SecondaryText)
                Text(
                    text = "Search books or authors...",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    color = SecondaryText,
                    fontSize = 14.sp
                )
                Icon(Icons.Default.Mic, contentDescription = "Voice search", tint = SecondaryText)
            }

            // Category Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEachIndexed { index, label ->
                    val selected = selectedFilter == index
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.chipRadius))
                            .background(if (selected) Primary else Surface)
                            .border(1.dp, if (selected) Primary else Border, RoundedCornerShape(Dimens.chipRadius))
                            .clickable { selectedFilter = index }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = label,
                            color = if (selected) Color.White else MainText,
                            fontSize = 14.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // Sort Option Row
            Row(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Tune, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(17.dp))
                Text("Sort by:", modifier = Modifier.padding(start = 6.dp), color = SecondaryText, fontSize = 13.sp)
                Text("Recently Added ⌄", modifier = Modifier.padding(start = 6.dp), color = Primary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            // Book Cards List
            LibraryBookCard("Deep Work", "Cal Newport", "DEEPW\nORK", "Now Playing", "Today, 10:45 AM", "18m left", 68, true)
            Spacer(modifier = Modifier.height(12.dp))
            LibraryBookCard("Atomic Habits", "James Clear", "ATOMIC\nHABITS", "PDF", "Yesterday", "34m left", 42, false)

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons (Scan Document / Upload PDF)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.buttonHeight),
                    shape = RoundedCornerShape(Dimens.buttonRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text(" Scan Document", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.buttonHeight),
                    shape = RoundedCornerShape(Dimens.buttonRadius)
                ) {
                    Icon(Icons.Default.UploadFile, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                    Text(" Upload PDF", color = Primary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Now Playing Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(Dimens.cardRadius))
                    .background(Surface)
                    .border(1.dp, Border, RoundedCornerShape(Dimens.cardRadius))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Book, contentDescription = null, tint = Color.White, modifier = Modifier.size(19.dp))
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text("Deep Work – Ch. 4", color = MainText, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Cal Newport • 18m left", color = SecondaryText, fontSize = 11.sp)
                }
                Text(
                    text = "1.15x",
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ChipBackground)
                        .padding(horizontal = 7.dp, vertical = 4.dp),
                    color = Primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = { isPlaying = !isPlaying }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Primary)
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LibraryBookCard(
    title: String,
    author: String,
    cover: String,
    badge: String,
    time: String,
    left: String,
    progress: Int,
    playing: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardRadius))
            .background(Surface)
            .border(1.dp, if (playing) Accent else Border, RoundedCornerShape(Dimens.cardRadius))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(56.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ChipBackground)
                    .padding(7.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(cover, fontSize = 9.sp, lineHeight = 11.sp, fontWeight = FontWeight.Bold, color = MainText)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MainText)
                        Text(author, fontSize = 13.sp, color = SecondaryText)
                    }
                    Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = SecondaryText)
                }
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = badge,
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(ChipBackground)
                            .padding(horizontal = 7.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        color = Primary,
                        fontWeight = FontWeight.Medium
                    )
                    Text("  ◷  $time", fontSize = 11.sp, color = SecondaryText)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("◖  $left", fontSize = 12.sp, color = if (playing) Accent else SecondaryText)
                    Text("$progress%", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MainText)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(5.dp)
                .clip(CircleShape)
                .background(Border)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress / 100f)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(if (playing) Accent else Primary)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
fun MyLibraryPreview() {
    Page2VoiceTheme {
        MyLibraryBody()
    }
}
