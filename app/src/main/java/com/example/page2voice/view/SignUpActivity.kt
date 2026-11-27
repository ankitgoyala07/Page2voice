package com.example.page2voice.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.page2voice.R
import com.example.page2voice.ui.theme.*

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Page2VoiceTheme {
                SignUpScreen(
                    onSignUpClick = { _, _, _, _ ->
                        val intent = Intent(this, HomePageActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    onLoginClick = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SignUpScreen(
    onSignUpClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
    horizontal = Dimens.screenPadding,
    vertical = Dimens.xl
),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Dimens.cardRadius))
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


            Text(
                text = "Page2Voice",
                style = MaterialTheme.typography.headlineLarge,
                color = MainText
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Heading & Subtitle
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Create an account to get started with Page2Voice.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryText
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Full Name Label & Field
            Text(
                text = "Full Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MainText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("", color = SecondaryText) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person Icon",
                        tint = SecondaryText
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Email Address Label & Field
            Text(
                text = "Email Address",
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
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Password Label & Field
            Text(
                text = "Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MainText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("", color = SecondaryText) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock Icon",
                        tint = SecondaryText
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = SecondaryText)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Label & Field
            Text(
                text = "Confirm Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MainText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("", color = SecondaryText) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock Icon",
                        tint = SecondaryText
                    )
                },
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle confirm password visibility", tint = SecondaryText)
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Terms and Conditions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreedToTerms,
                    onCheckedChange = { agreedToTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = Primary)
                )
                Spacer(modifier = Modifier.width(4.dp))
                val annotatedString = buildAnnotatedString {
                    append("I agree to the ")
                    withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.Medium)) {
                        append("Terms and Conditions")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.Medium)) {
                        append("Privacy Policy")
                    }
                    append(".")
                }
                Text(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryText,
                    modifier = Modifier.clickable { agreedToTerms = !agreedToTerms }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Create Account Button
            Button(
                onClick = { onSignUpClick(fullName, email, password, confirmPassword) },
                shape = RoundedCornerShape(Dimens.buttonRadius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer: Already have an account? Login
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
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
fun SignUpScreenPreview() {
    Page2VoiceTheme {
        SignUpScreen()
    }
}
