package com.example.page2voice.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.page2voice.R
import com.example.page2voice.ui.theme.LibraryBackground
import com.example.page2voice.ui.theme.LibraryBorder
import com.example.page2voice.ui.theme.LibraryCover
import com.example.page2voice.ui.theme.LibraryMutedText
import com.example.page2voice.ui.theme.LibraryPrimary
import com.example.page2voice.ui.theme.LibraryPrimarySoft
import com.example.page2voice.ui.theme.LibrarySecondaryText
import com.example.page2voice.ui.theme.LibraryTeal
import com.example.page2voice.ui.theme.LibraryText
import com.example.page2voice.ui.theme.Page2VoiceTheme

@Composable
fun MyLibraryBody() {
    var selectedFilter by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }
    val filters = listOf("All", "PDFs", "Scanned", "Summary")

    Scaffold(
        containerColor = LibraryBackground,
        bottomBar = { LibraryBottomBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.page2voice_logo),
                    contentDescription = "Page2Voice logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(42.dp)
                )
                Text("Page2Voice", modifier = Modifier.weight(1f).padding(start = 6.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = LibraryText)
                IconButton(onClick = {}) { Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = LibrarySecondaryText) }
                IconButton(onClick = {}) { Icon(Icons.Default.Settings, contentDescription = "Settings", tint = LibrarySecondaryText) }
            }

            Text("My Library", modifier = Modifier.padding(top = 10.dp), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = LibraryText)
            Text("All your saved documents in one place.", fontSize = 14.sp, color = LibrarySecondaryText)

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp).clip(RoundedCornerShape(14.dp)).background(Color.White).border(1.dp, LibraryBorder, RoundedCornerShape(14.dp)).padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = LibraryMutedText)
                Text("Search books or authors...", modifier = Modifier.weight(1f).padding(start = 10.dp), color = LibraryMutedText, fontSize = 14.sp)
                Icon(Icons.Default.Mic, contentDescription = "Voice search", tint = LibraryMutedText)
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 14.dp).horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.forEachIndexed { index, label ->
                    val selected = selectedFilter == index
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(if (selected) LibraryPrimary else Color.White).border(1.dp, if (selected) LibraryPrimary else LibraryBorder, RoundedCornerShape(10.dp)).clickable { selectedFilter = index }.padding(horizontal = 16.dp, vertical = 9.dp)
                    ) {
                        Text(label, color = if (selected) Color.White else LibraryText, fontSize = 14.sp)
                    }
                }
            }

            Row(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Tune, contentDescription = null, tint = LibraryMutedText, modifier = Modifier.size(17.dp))
                Text("Sort by:", modifier = Modifier.padding(start = 7.dp), color = LibraryMutedText, fontSize = 13.sp)
                Text("Recently Added  ⌄", modifier = Modifier.padding(start = 7.dp), color = LibraryPrimary, fontSize = 13.sp)
            }

            LibraryBookCard("Deep Work", "Cal Newport", "DEEPW\nORK", "Now Playing", "Today, 10:45 AM", "18m left", 68, true)
            Spacer(Modifier.height(12.dp))
            LibraryBookCard("Atomic Habits", "James Clear", "ATOMIC\nHABITS", "PDF", "Yesterday", "34m left", 42, false)

            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = {}, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = LibraryPrimary)) {
                    Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text("  Scan Document", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
                OutlinedButton(onClick = {}, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.UploadFile, contentDescription = null, tint = LibraryPrimary, modifier = Modifier.size(18.dp))
                    Text("  Upload PDF", color = LibraryPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clip(RoundedCornerShape(16.dp)).background(Color.White).border(1.dp, LibraryBorder, RoundedCornerShape(16.dp)).padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(34.dp).clip(RoundedCornerShape(9.dp)).background(LibraryPrimary), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Book, contentDescription = null, tint = Color.White, modifier = Modifier.size(19.dp))
                }
                Column(modifier = Modifier.weight(1f).padding(start = 9.dp)) {
                    Text("Deep Work – Ch. 4", color = LibraryText, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Cal Newport • 18m left", color = LibraryMutedText, fontSize = 11.sp)
                }
                Text("1.15x", modifier = Modifier.clip(RoundedCornerShape(7.dp)).background(LibraryPrimarySoft).padding(horizontal = 7.dp, vertical = 5.dp), color = LibraryPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = { isPlaying = !isPlaying }) {
                    Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = if (isPlaying) "Pause" else "Play", tint = Color.White, modifier = Modifier.clip(CircleShape).background(LibraryPrimary).padding(8.dp).size(20.dp))
                }
            }
        }
    }
}

@Composable
private fun LibraryBookCard(title: String, author: String, cover: String, badge: String, time: String, left: String, progress: Int, playing: Boolean) {
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).border(1.dp, if (playing) LibraryTeal else LibraryBorder, RoundedCornerShape(16.dp)).padding(13.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(56.dp).height(80.dp).clip(RoundedCornerShape(9.dp)).background(LibraryCover).padding(7.dp), contentAlignment = Alignment.TopStart) {
                Text(cover, fontSize = 8.sp, lineHeight = 10.sp, fontWeight = FontWeight.Bold, color = LibraryText)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LibraryText)
                        Text(author, fontSize = 13.sp, color = LibrarySecondaryText)
                    }
                    Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = LibraryMutedText)
                }
                Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(badge, modifier = Modifier.clip(RoundedCornerShape(5.dp)).background(LibraryPrimarySoft).padding(horizontal = 7.dp, vertical = 4.dp), fontSize = 11.sp, color = LibraryPrimary)
                    Text("  ◷  $time", fontSize = 11.sp, color = LibraryMutedText)
                }
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("◖  $left", fontSize = 12.sp, color = if (playing) LibraryTeal else LibrarySecondaryText)
                    Text("$progress%", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = LibraryText)
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().padding(top = 9.dp).height(5.dp).clip(CircleShape).background(LibraryBorder)) {
            Box(modifier = Modifier.fillMaxWidth(progress / 100f).height(5.dp).clip(CircleShape).background(if (playing) LibraryTeal else LibraryPrimary))
        }
    }
}

@Composable
private fun LibraryBottomBar() {
    Row(modifier = Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 12.dp, vertical = 9.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
        BottomItem("Home", Icons.Default.Home, false)
        BottomItem("Library", Icons.Default.Book, true)
        BottomItem("History", Icons.Default.History, false)
        BottomItem("Profile", Icons.Default.Person, false)
    }
}

@Composable
private fun BottomItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = if (selected) LibraryPrimary else LibraryMutedText, modifier = Modifier.size(22.dp))
        Text(label, fontSize = 11.sp, color = if (selected) LibraryPrimary else LibraryMutedText)
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
fun MyLibraryPreview() {
    Page2VoiceTheme {
        MyLibraryBody()
    }
}
