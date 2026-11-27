package com.example.page2voice.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.page2voice.R
import com.example.page2voice.model.DocumentCard
import com.example.page2voice.ui.theme.Background
import com.example.page2voice.ui.theme.Dimens
import com.example.page2voice.ui.theme.MainText
import com.example.page2voice.ui.theme.Page2VoiceTheme
import com.example.page2voice.ui.theme.Primary
import com.example.page2voice.ui.theme.ProgressTrack
import com.example.page2voice.ui.theme.SecondaryText
import com.example.page2voice.ui.theme.Surface
import com.example.page2voice.viewmodel.HomeViewModel

class HomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Page2VoiceTheme {
                HomepageBody()
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageBody() {
    val activity = LocalActivity.current
    val viewModel: HomeViewModel = viewModel()

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Library", Icons.Default.Book),
        BottomNavItem("History", Icons.Default.History),
        BottomNavItem("Profile", Icons.Default.Person)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    val documents = viewModel.documents

    Scaffold(
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
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedIndex) {
                0 -> Home(
                    documents = documents,
                    onDocumentClick = { document ->
                        viewModel.togglePlayPause(document)
                    }
                )
                1 -> Library()
                2 -> History()
                3 -> Profile()
            }
        }
    }
}

@Composable
fun Home(
    documents: List<DocumentCard> = emptyList(),
    onDocumentClick: (DocumentCard) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(Dimens.screenPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Session Active",
                color = Primary,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "● Ready to Listen",
                color = SecondaryText,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(Dimens.sm))

        Text(
            text = "Welcome back, Manish",
            style = MaterialTheme.typography.displayLarge,
            color = MainText
        )

        Spacer(modifier = Modifier.height(Dimens.sm))

        Text(
            text = "Ready to convert your documents to voice",
            style = MaterialTheme.typography.bodyLarge,
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(Dimens.lg))

        ActionSection(
            onScanClick = {
                // Open Camera Scanner
            },
            onUploadClick = {
                // Open PDF Picker
            }
        )

        Spacer(modifier = Modifier.height(Dimens.lg))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Continue Reading",
                style = MaterialTheme.typography.headlineMedium,
                color = MainText
            )

            Text(
                text = "${documents.size} in progress",
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText
            )
        }

        Spacer(modifier = Modifier.height(Dimens.md))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(documents) { document ->
                DocumentCardItem(
                    document = document,
                    onClick = { onDocumentClick(document) }
                )
            }
        }
    }
}

@Composable
fun Library() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("Library Screen")
    }
}

@Composable
fun History() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("History Screen")
    }
}

@Composable
fun Profile() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("Profile Screen")
    }
}

@Composable
fun ActionSection(
    onScanClick: () -> Unit,
    onUploadClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionCard(
            modifier = Modifier.weight(1f),
            title = "Scan Document",
            description = "",
            icon = Icons.Default.DocumentScanner,
            onClick = onScanClick
        )

        ActionCard(
            modifier = Modifier.weight(1f),
            title = "Upload PDF",
            description = "",
            icon = Icons.Default.UploadFile,
            onClick = onUploadClick
        )
    }
}

@Composable
fun ActionCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.height(80.dp),
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = Surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.sm)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MainText
                )
            }

            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun DocumentCardItem(
    document: DocumentCard,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.elevatedCardColors(
            containerColor = Surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.cardPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (document.isNowPlaying) {
                        Text(
                            text = "Now Playing",
                            color = Primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .background(
                                    Primary.copy(alpha = 0.15f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp
                                )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = document.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MainText
                    )

                    if (document.subtitle.isNotEmpty()) {
                        Text(
                            text = document.subtitle,
                            fontSize = 12.sp,
                            color = SecondaryText
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = Primary)
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (document.isNowPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (document.isNowPlaying) "Pause" else "Play",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.sm))

            LinearProgressIndicator(
                progress = { document.progress },
                color = Primary,
                trackColor = ProgressTrack,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.sm))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${(document.progress * 100).toInt()}% completed",
                    fontSize = 12.sp,
                    color = SecondaryText
                )

                Text(
                    text = document.timeLeft,
                    fontSize = 12.sp,
                    color = SecondaryText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    Page2VoiceTheme {
        HomepageBody()
    }
}
