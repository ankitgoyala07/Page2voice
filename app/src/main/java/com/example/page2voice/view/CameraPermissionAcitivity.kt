package com.example.page2voice.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.page2voice.R
import com.example.page2voice.ui.theme.*
import com.example.page2voice.ui.theme.Page2VoiceTheme

class CameraPermissionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Page2VoiceTheme() {
                ScanDocumentScreen(onBack = ::finish)
            }
        }
    }
}

@Composable
private fun ScanDocumentScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity
    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var permanentlyDenied by remember { mutableStateOf(false) }
    var capturedPhoto by remember { mutableStateOf<Bitmap?>(null) }

    val cameraCapture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { photo -> capturedPhoto = photo }
    val cameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        if (granted) cameraCapture.launch(null)
        else {
            permanentlyDenied = activity?.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) != true
            showPermissionDialog = true
        }
    }
    val galleryPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { _: Uri? -> }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            containerColor = CameraPermissionWhite,
            titleContentColor = CameraPermissionTitle,
            textContentColor = CameraPermissionBody,
            iconContentColor = CameraPermissionPurple,
            title = { Text("Camera access required", color = CameraPermissionTitle) },
            text = {
                Text(
                    if (permanentlyDenied) "Camera access is turned off for Page2Voice. Open app settings and allow Camera to scan documents."
                    else "Page2Voice needs camera access to scan documents and book pages.",
                    color = CameraPermissionBody
                )
            },
            confirmButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = CameraPermissionPurple),
                    onClick = {
                        showPermissionDialog = false
                        if (permanentlyDenied) {
                            context.startActivity(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                            )
                        } else cameraPermission.launch(Manifest.permission.CAMERA)
                    }) { Text(if (permanentlyDenied) "Open Settings" else "Try again") }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = CameraPermissionPurple),
                    onClick = { showPermissionDialog = false }
                ) { Text("Cancel") }
            }
        )
    }

    Surface(modifier = Modifier.fillMaxSize(), color = CameraPermissionBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(42.dp)) {
                    Icon(painterResource(R.drawable.ic_back_arrow), contentDescription = "Back", tint = CameraPermissionPurple, modifier = Modifier.size(22.dp))
                }
                Text(
                    "Camera Permission",
                    color = CameraPermissionPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                IconButton(onClick = {}, enabled = false, modifier = Modifier.size(42.dp)) {
                    Icon(painterResource(R.drawable.ic_flash), contentDescription = "Flash disabled", tint = CameraPermissionFlash, modifier = Modifier.size(22.dp))
                }
            }

            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (capturedPhoto != null) {
                    Image(
                        bitmap = capturedPhoto!!.asImageBitmap(),
                        contentDescription = "Captured document photo",
                        modifier = Modifier.fillMaxWidth().height(230.dp).clip(RoundedCornerShape(18.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.height(24.dp))
                } else {
                    Box(
                        modifier = Modifier.size(88.dp).clip(CircleShape).background(CameraPermissionHalo),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier.size(58.dp).clip(CircleShape).background(CameraPermissionPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(painterResource(R.drawable.ic_scan_document), contentDescription = null, tint = CameraPermissionWhite, modifier = Modifier.size(30.dp))
                        }
                    }
                    Spacer(Modifier.height(22.dp))
                }

                Text(
                    if (capturedPhoto != null) "Document captured" else "Camera access required",
                    color = CameraPermissionTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    if (capturedPhoto != null) "Your photo is ready to scan." else "Allow camera access to scan your documents, capture book pages, and convert them to audiobooks.",
                    color = CameraPermissionBody,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
                )
                Spacer(Modifier.height(26.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(13.dp),
                    colors = CardDefaults.cardColors(containerColor = CameraPermissionWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    border = BorderStroke(0.7.dp, CameraPermissionCardBorder)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 13.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(38.dp).clip(RoundedCornerShape(10.dp)).background(CameraPermissionHalo),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(painterResource(R.drawable.ic_open_book), contentDescription = null, tint = CameraPermissionPurple, modifier = Modifier.size(22.dp))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Multi-page batch scanning", color = CameraPermissionTitle, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(4.dp))
                            Text("Capture complete chapters seamlessly in one go.", color = CameraPermissionBody, fontSize = 10.sp, lineHeight = 14.sp)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (permissionGranted) cameraCapture.launch(null)
                    else cameraPermission.launch(Manifest.permission.CAMERA)
                },
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CameraPermissionPurple, contentColor = CameraPermissionWhite)
            ) {
                Icon(painterResource(R.drawable.ic_camera), contentDescription = null, tint = CameraPermissionWhite, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(10.dp))
                Text("Allow Camera Access", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            TextButton(
                onClick = { galleryPicker.launch("image/*") },
                modifier = Modifier.fillMaxWidth().height(48.dp).padding(top = 1.dp)
            ) {
                Icon(painterResource(R.drawable.ic_gallery), contentDescription = null, tint = CameraPermissionPurple, modifier = Modifier.size(17.dp))
                Spacer(Modifier.width(7.dp))
                Text("Select from Gallery instead", color = CameraPermissionPurple, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740, name = "Camera Permission")
@Composable
private fun ScanDocumentScreenPreview() {
    Page2VoiceTheme() {
        ScanDocumentScreen(onBack = {})
    }
}
