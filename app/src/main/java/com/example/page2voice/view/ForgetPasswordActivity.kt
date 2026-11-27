package com.example.page2voice.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.page2voice.ui.theme.*

class ForgetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Page2VoiceTheme {
                ForgetPasswordScreen(
                    onBackClick = { finish() },
                    onSendResetLinkClick = { _ ->
                        // Handle send reset link action
                    },
                    onLoginClick = { finish() }
                )
            }
        }
    }
}

@Composable
fun ForgetPasswordScreen(
    onBackClick: () -> Unit = {},
    onSendResetLinkClick: (String) -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
    horizontal = Dimens.screenPadding,
    vertical = Dimens.lg
),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Top Back Button Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
    Primary.copy(alpha = 0.08f)
)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MainText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logo Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.p2v_logo),
                    contentDescription = "Logo",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "Page2Voice",
                style = MaterialTheme.typography.headlineLarge,
                color = MainText
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Heading & Subtitle
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MainText
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Don't worry! Enter your registered email address and we'll send you a link to reset your password.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryText,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Registered Email Address Label & Field
            Text(
                text = "Registered Email Address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MainText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("", color = SecondaryText) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = SecondaryText
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(Dimens.buttonRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Border,
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedTextColor = MainText,
                    unfocusedTextColor = MainText
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.textFieldHeight)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Send Reset Link Button
            Button(
                onClick = { onSendResetLinkClick(email) },
                shape = RoundedCornerShape(Dimens.buttonRadius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.buttonHeight)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Send Reset Link",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer: Remember your password? Login
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remember your password? ",
                    fontSize = 14.sp,
                    color = SecondaryText
                )
                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgetPasswordScreenPreview() {
    Page2VoiceTheme {
        ForgetPasswordScreen()
    }
}
