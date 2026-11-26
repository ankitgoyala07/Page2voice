package com.example.page2voice.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.page2voice.R
import com.example.page2voice.ui.theme.Page2VoiceTheme

private object CalmIndigoColors {
    val Primary = Color(0xFF4F46E5)
    val PrimaryDark = Color(0xFF3730A3)
    val PrimarySoft = Color(0xFFEEF2FF)
    val CoverBg = Color(0xFFEAEFF8)
    val Accent = Color(0xFF14B8A6)
    val AccentDark = Color(0xFF0D9488)
    val Background = Color(0xFFF8FAFC)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceSubtle = Color(0xFFF1F5F9)
    val MainText = Color(0xFF1E293B)
    val SecondaryText = Color(0xFF64748B)
    val MutedText = Color(0xFF94A3B8)
    val Border = Color(0xFFE2E8F0)
    val Success = Color(0xFF16A34A)
    val Error = Color(0xFFDC2626)
}

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

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

private enum class LibraryDocType(val label: String) {
    PDF("PDF"),
    SCANNED("Scanned"),
    SUMMARY("Summary")
}

private enum class LibraryFilter(val label: String) {
    ALL("All"),
    PDFS("PDFs"),
    SCANNED("Scanned"),
    SUMMARY("Summary")
}

private enum class LibrarySortOption(val label: String) {
    RECENTLY_ADDED("Recently Added"),
    TITLE_AZ("Title (A–Z)"),
    PROGRESS("Progress")
}

private data class LibraryDocument(
    val id: String,
    val title: String,
    val author: String,
    val coverLabel: String,
    val docType: LibraryDocType,
    val lastOpenedLabel: String,
    val timeLeftLabel: String,
    val progress: Float,
    val currentChapter: String,
    val addedOrder: Int
)

private val SampleDocuments = listOf(
    LibraryDocument("1", "Deep Work", "Cal Newport", "DEEPW\nORK", LibraryDocType.PDF, "Today, 10:45 AM", "18m left", 0.68f, "Ch. 4", 1),
    LibraryDocument("2", "Atomic Habits", "James Clear", "ATOMIC\nHABITS", LibraryDocType.PDF, "Yesterday", "34m left", 0.42f, "Ch. 2", 2)
)

private val PlaybackSpeeds = listOf("1.0x", "1.15x", "1.25x", "1.5x")

// Inline crisp vector icons matching the mockup
private object LibraryIcons {
    val Mic: ImageVector by lazy {
        vec("Mic") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 1.9f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 2f); arcTo(3f, 3f, 0f, false, true, 15f, 5f); verticalLineTo(11f)
                arcTo(3f, 3f, 0f, false, true, 9f, 11f); verticalLineTo(5f)
                arcTo(3f, 3f, 0f, false, true, 12f, 2f); close()
                moveTo(19f, 10f); verticalLineTo(11f); arcTo(7f, 7f, 0f, false, true, 5f, 11f); verticalLineTo(10f)
                moveTo(12f, 18f); verticalLineTo(22f); moveTo(8f, 22f); horizontalLineTo(16f)
            }
        }
    }
    val Sort: ImageVector by lazy {
        vec("Sort") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round) {
                moveTo(3f, 6f); horizontalLineTo(21f)
                moveTo(3f, 12f); horizontalLineTo(15f)
                moveTo(3f, 18f); horizontalLineTo(9f)
            }
        }
    }
    val Clock: ImageVector by lazy {
        vec("Clock") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 1.8f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 3f); arcTo(9f, 9f, 0f, true, true, 11.99f, 3f); close()
                moveTo(12f, 7.5f); verticalLineTo(12f); lineTo(15f, 13.5f)
            }
        }
    }
    val Volume: ImageVector by lazy {
        vec("Volume") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3f, 9f); verticalLineTo(15f); horizontalLineTo(7f); lineTo(12f, 20f); verticalLineTo(4f); lineTo(7f, 9f); close()
            }
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round) {
                moveTo(15.5f, 8.5f); curveTo(17f, 10f, 17f, 14f, 15.5f, 15.5f)
            }
        }
    }
    val Headphones: ImageVector by lazy {
        vec("Headphones") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 1.9f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(4f, 17f); verticalLineTo(12f); arcTo(8f, 8f, 0f, false, true, 20f, 12f); verticalLineTo(17f)
                moveTo(4f, 15f); horizontalLineTo(7f); verticalLineTo(20f); horizontalLineTo(5f)
                arcTo(2f, 2f, 0f, false, true, 3f, 18f); verticalLineTo(16f); close()
                moveTo(20f, 15f); horizontalLineTo(17f); verticalLineTo(20f); horizontalLineTo(19f)
                arcTo(2f, 2f, 0f, false, false, 21f, 18f); verticalLineTo(16f); close()
            }
        }
    }
    val Scan: ImageVector by lazy {
        vec("Scan") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(4f, 8f); verticalLineTo(5f); arcTo(1f, 1f, 0f, false, true, 5f, 4f); horizontalLineTo(8f)
                moveTo(16f, 4f); horizontalLineTo(19f); arcTo(1f, 1f, 0f, false, true, 20f, 5f); verticalLineTo(8f)
                moveTo(20f, 16f); verticalLineTo(19f); arcTo(1f, 1f, 0f, false, true, 19f, 20f); horizontalLineTo(16f)
                moveTo(8f, 20f); horizontalLineTo(5f); arcTo(1f, 1f, 0f, false, true, 4f, 19f); verticalLineTo(16f)
                moveTo(8f, 9f); horizontalLineTo(16f); moveTo(8f, 12f); horizontalLineTo(16f); moveTo(8f, 15f); horizontalLineTo(13f)
            }
        }
    }
    val UploadFile: ImageVector by lazy {
        vec("UploadFile") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 1.9f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(14f, 2f); horizontalLineTo(6f); arcTo(2f, 2f, 0f, false, false, 4f, 4f)
                verticalLineTo(20f); arcTo(2f, 2f, 0f, false, false, 6f, 22f); horizontalLineTo(18f)
                arcTo(2f, 2f, 0f, false, false, 20f, 20f); verticalLineTo(8f); close()
                moveTo(14f, 2f); verticalLineTo(8f); horizontalLineTo(20f)
                moveTo(12f, 18f); verticalLineTo(12f); moveTo(9.5f, 14.5f); lineTo(12f, 12f); lineTo(14.5f, 14.5f)
            }
        }
    }
    val Rewind10: ImageVector by lazy {
        vec("Rewind10") {
            path(stroke = SolidColor(Color.Black), strokeLineWidth = 1.9f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(4f, 12f); arcTo(8f, 8f, 0f, true, false, 6.5f, 6.2f)
                moveTo(4f, 4.5f); verticalLineTo(8.5f); horizontalLineTo(8f)
            }
        }
    }
}

private inline fun vec(name: String, block: ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(name, 24.dp, 24.dp, 24f, 24f).apply(block).build()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLibraryBody() {
    val activity = LocalActivity.current

    // for bottom icons
    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Library", Icons.Default.Book),
        BottomNavItem("History", Icons.Default.History),
        BottomNavItem("Profile", Icons.Default.Person)
    )

    // Default to Library tab (index 1)
    var selectedIndex by remember { mutableIntStateOf(1) }

    Scaffold(
        containerColor = CalmIndigoColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CalmIndigoColors.Background,
                    titleContentColor = CalmIndigoColors.MainText,
                    navigationIconContentColor = Color.Unspecified,
                    actionIconContentColor = CalmIndigoColors.SecondaryText
                ),
                title = {
                    Text(
                        text = "Page2Voice",
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = CalmIndigoColors.MainText
                    )
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
        // for bottom buttons icons
        bottomBar = {
            Column {
                HorizontalDivider(thickness = 1.dp, color = CalmIndigoColors.Border)
                NavigationBar(
                    containerColor = CalmIndigoColors.Surface,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedIndex == index) FontWeight.SemiBold else FontWeight.Medium
                                )
                            },
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = CalmIndigoColors.Primary,
                                selectedTextColor = CalmIndigoColors.Primary,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = CalmIndigoColors.MutedText,
                                unselectedTextColor = CalmIndigoColors.MutedText
                            )
                        )
                    }
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
                0 -> Home1()
                1 -> Home2()
                2 -> Home3()
                3 -> Home4()
            }
        }
    }
}

@Composable
fun Home1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CalmIndigoColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Text("Home Screen", color = CalmIndigoColors.MainText)
    }
}

@Composable
fun Home2() {
    var documents by remember { mutableStateOf(SampleDocuments) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf(LibraryFilter.ALL) }
    var sortOption by rememberSaveable { mutableStateOf(LibrarySortOption.RECENTLY_ADDED) }
    var showSortMenu by remember { mutableStateOf(false) }

    var activeDocId by rememberSaveable { mutableStateOf(SampleDocuments.first().id) }
    var isPlaying by rememberSaveable { mutableStateOf(true) }
    var speedIndex by rememberSaveable { mutableIntStateOf(1) }

    val visibleDocs = remember(documents, searchQuery, selectedFilter, sortOption) {
        documents.filter { doc ->
            val matchesFilter = when (selectedFilter) {
                LibraryFilter.ALL -> true
                LibraryFilter.PDFS -> doc.docType == LibraryDocType.PDF
                LibraryFilter.SCANNED -> doc.docType == LibraryDocType.SCANNED
                LibraryFilter.SUMMARY -> doc.docType == LibraryDocType.SUMMARY
            }
            val q = searchQuery.trim()
            val matchesSearch = q.isEmpty() ||
                    doc.title.contains(q, ignoreCase = true) ||
                    doc.author.contains(q, ignoreCase = true)
            matchesFilter && matchesSearch
        }.let { list ->
            when (sortOption) {
                LibrarySortOption.RECENTLY_ADDED -> list.sortedBy { it.addedOrder }
                LibrarySortOption.TITLE_AZ -> list.sortedBy { it.title.lowercase() }
                LibrarySortOption.PROGRESS -> list.sortedByDescending { it.progress }
            }
        }
    }

    val activeDoc = documents.find { it.id == activeDocId } ?: documents.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CalmIndigoColors.Background)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Title & Subtitle
            item {
                Column {
                    Text(
                        text = "My Library",
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            lineHeight = 34.sp
                        ),
                        color = CalmIndigoColors.MainText
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "All your saved documents in one place.",
                        fontSize = 14.sp,
                        color = CalmIndigoColors.SecondaryText
                    )
                }
            }

            // Search Bar with Mic Icon
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Search books or authors...",
                            fontSize = 14.sp,
                            color = CalmIndigoColors.MutedText
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = CalmIndigoColors.MutedText,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = CalmIndigoColors.MutedText,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = LibraryIcons.Mic,
                                    contentDescription = "Voice search",
                                    tint = CalmIndigoColors.MutedText,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CalmIndigoColors.Surface,
                        unfocusedContainerColor = CalmIndigoColors.Surface,
                        focusedBorderColor = CalmIndigoColors.Primary,
                        unfocusedBorderColor = CalmIndigoColors.Border,
                        focusedTextColor = CalmIndigoColors.MainText,
                        unfocusedTextColor = CalmIndigoColors.MainText
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 52.dp)
                )
            }

            // Filter Chips Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LibraryFilter.entries.forEach { filter ->
                        val selected = selectedFilter == filter
                        Surface(
                            onClick = { selectedFilter = filter },
                            shape = RoundedCornerShape(10.dp),
                            color = if (selected) CalmIndigoColors.Primary else CalmIndigoColors.Surface,
                            border = if (selected) null else BorderStroke(1.dp, CalmIndigoColors.Border)
                        ) {
                            Text(
                                text = filter.label,
                                fontSize = 13.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                color = if (selected) Color.White else CalmIndigoColors.MainText,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Sort Row
            item {
                Box {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showSortMenu = true }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = LibraryIcons.Sort,
                            contentDescription = null,
                            tint = CalmIndigoColors.MutedText,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Sort by:",
                            fontSize = 13.sp,
                            color = CalmIndigoColors.SecondaryText
                        )
                        Text(
                            text = "${sortOption.label} ⌄",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CalmIndigoColors.Primary
                        )
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        LibrarySortOption.entries.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option.label,
                                        color = if (option == sortOption) CalmIndigoColors.Primary else CalmIndigoColors.MainText
                                    )
                                },
                                onClick = {
                                    sortOption = option
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
            }

            // Document Cards
            items(visibleDocs, key = { it.id }) { doc ->
                val isNowPlaying = doc.id == activeDocId && isPlaying
                LibraryBookItemCard(
                    document = doc,
                    isNowPlaying = isNowPlaying,
                    onClick = {
                        activeDocId = doc.id
                        isPlaying = true
                    },
                    onRemove = {
                        documents = documents.filterNot { it.id == doc.id }
                        if (activeDocId == doc.id) {
                            activeDocId = documents.firstOrNull()?.id ?: ""
                        }
                    }
                )
            }
        }

        // Scan Document + Upload PDF CTA Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CalmIndigoColors.Primary,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 12.dp),
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 48.dp)
            ) {
                Icon(
                    imageVector = LibraryIcons.Scan,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Scan Document",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, CalmIndigoColors.Border),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CalmIndigoColors.Surface,
                    contentColor = CalmIndigoColors.Primary
                ),
                contentPadding = PaddingValues(horizontal = 12.dp),
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 48.dp)
            ) {
                Icon(
                    imageVector = LibraryIcons.UploadFile,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Upload PDF",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Mini Audio Player Bar
        if (activeDoc != null) {
            MiniAudioBar(
                title = "${activeDoc.title} — ${activeDoc.currentChapter}",
                subtitle = "${activeDoc.author} • ${activeDoc.timeLeftLabel}",
                progress = activeDoc.progress,
                isPlaying = isPlaying,
                speedLabel = PlaybackSpeeds[speedIndex],
                onPlayPauseClick = { isPlaying = !isPlaying },
                onSpeedClick = { speedIndex = (speedIndex + 1) % PlaybackSpeeds.size }
            )
        }
    }
}

@Composable
private fun LibraryBookItemCard(
    document: LibraryDocument,
    isNowPlaying: Boolean,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    var menuOpen by remember { mutableStateOf(false) }
    val percent = (document.progress * 100).toInt().coerceIn(0, 100)
    val barColor = when {
        percent >= 100 -> CalmIndigoColors.Success
        isNowPlaying -> CalmIndigoColors.Accent
        else -> CalmIndigoColors.Primary
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CalmIndigoColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(
            width = if (isNowPlaying) 1.2.dp else 1.dp,
            color = if (isNowPlaying) CalmIndigoColors.Accent else CalmIndigoColors.Border
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Book Cover Thumbnail
            Column(
                modifier = Modifier
                    .size(width = 72.dp, height = 94.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CalmIndigoColors.CoverBg)
                    .border(1.dp, CalmIndigoColors.Border, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = document.coverLabel,
                    fontSize = 10.sp,
                    lineHeight = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isNowPlaying) CalmIndigoColors.Primary else CalmIndigoColors.MainText,
                    maxLines = 3
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(if (isNowPlaying) CalmIndigoColors.Primary else CalmIndigoColors.MainText),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isNowPlaying) LibraryIcons.Volume else Icons.Default.Book,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    Text(
                        text = document.docType.label,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium,
                        color = CalmIndigoColors.SecondaryText
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = document.title,
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = CalmIndigoColors.MainText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Box {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Options",
                            tint = CalmIndigoColors.MutedText,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { menuOpen = true }
                        )
                        DropdownMenu(
                            expanded = menuOpen,
                            onDismissRequest = { menuOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Open & Listen") },
                                onClick = {
                                    menuOpen = false
                                    onClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Remove from Library", color = CalmIndigoColors.Error) },
                                onClick = {
                                    menuOpen = false
                                    onRemove()
                                }
                            )
                        }
                    }
                }

                Text(
                    text = document.author,
                    fontSize = 14.sp,
                    color = CalmIndigoColors.SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                // Badge + Clock + Date Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CalmIndigoColors.PrimarySoft
                    ) {
                        Text(
                            text = if (isNowPlaying) "Now Playing" else document.docType.label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CalmIndigoColors.Primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = LibraryIcons.Clock,
                            contentDescription = null,
                            tint = CalmIndigoColors.MutedText,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = document.lastOpenedLabel,
                            fontSize = 12.sp,
                            color = CalmIndigoColors.MutedText
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Time Left + Percentage Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = if (isNowPlaying) LibraryIcons.Volume else LibraryIcons.Headphones,
                            contentDescription = null,
                            tint = if (isNowPlaying) CalmIndigoColors.AccentDark else CalmIndigoColors.SecondaryText,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = document.timeLeftLabel,
                            fontSize = 12.sp,
                            fontWeight = if (isNowPlaying) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isNowPlaying) CalmIndigoColors.AccentDark else CalmIndigoColors.SecondaryText
                        )
                    }
                    Text(
                        text = "$percent%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CalmIndigoColors.MainText
                    )
                }

                Spacer(Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(CalmIndigoColors.SurfaceSubtle)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(document.progress.coerceIn(0f, 1f))
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp))
                            .background(barColor)
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniAudioBar(
    title: String,
    subtitle: String,
    progress: Float,
    isPlaying: Boolean,
    speedLabel: String,
    onPlayPauseClick: () -> Unit,
    onSpeedClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CalmIndigoColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.dp, CalmIndigoColors.Border)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top teal progress strip inside mini player
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 6.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(CalmIndigoColors.SurfaceSubtle)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
                        .background(CalmIndigoColors.Accent)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Indigo rounded square with white soundwave bars
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CalmIndigoColors.Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        val w = size.width
                        val h = size.height
                        val barW = w * 0.14f
                        val bars = listOf(0.15f to 0.35f, 0.36f to 0.65f, 0.57f to 0.85f, 0.78f to 0.45f)
                        bars.forEach { (xR, hR) ->
                            val bh = h * hR
                            drawRoundRect(
                                color = Color.White,
                                topLeft = Offset(w * xR, (h - bh) / 2f),
                                size = Size(barW, bh),
                                cornerRadius = CornerRadius(barW / 2f, barW / 2f)
                            )
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CalmIndigoColors.MainText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = CalmIndigoColors.SecondaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CalmIndigoColors.PrimarySoft,
                    modifier = Modifier.clickable(onClick = onSpeedClick)
                ) {
                    Text(
                        text = speedLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CalmIndigoColors.Primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                    )
                }

                Icon(
                    imageVector = LibraryIcons.Rewind10,
                    contentDescription = "Rewind",
                    tint = CalmIndigoColors.SecondaryText,
                    modifier = Modifier.size(20.dp)
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CalmIndigoColors.Primary)
                        .clickable(onClick = onPlayPauseClick),
                    contentAlignment = Alignment.Center
                ) {
                    if (isPlaying) {
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val barW = size.width * 0.28f
                            drawRoundRect(
                                color = Color.White,
                                topLeft = Offset(size.width * 0.14f, 0f),
                                size = Size(barW, size.height),
                                cornerRadius = CornerRadius(2f, 2f)
                            )
                            drawRoundRect(
                                color = Color.White,
                                topLeft = Offset(size.width * 0.58f, 0f),
                                size = Size(barW, size.height),
                                cornerRadius = CornerRadius(2f, 2f)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Home3() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CalmIndigoColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Text("History Screen", color = CalmIndigoColors.MainText)
    }
}

@Composable
fun Home4() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CalmIndigoColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Text("Profile Screen", color = CalmIndigoColors.MainText)
    }
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    Page2VoiceTheme {
        MyLibraryBody()
    }
}