package com.example.jetpack

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun Login(navController: NavController) {
    var orgNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var orgNumberError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Company avatar
            Image(
                painter = rememberAsyncImagePainter(""),
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp).padding(top = 16.dp)
            )

            // "Sign In" text
            Text(
                text = "Sign in",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )

            // Paragraph with rounded rectangle and hyperlink
            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                val annotatedText = buildAnnotatedString {
                    append("You are browsing")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Massive Dynamic")
                    }
                    append(". Click on \"Sing in\" to access.")
                }
                ClickableText(
                    text = annotatedText,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        if(offset in 14..27) { // "Massive Dynamic" range (characters 14 - 27)
                            // Handle hyperlink click
                            navController.navigate("webview_massive_dynamic")
                        }
                    }
                )
            }

            // Organization Number input
            OutlinedTextField(
                value = orgNumber,
                onValueChange = {
                    orgNumber = it
                    orgNumberError = it.isEmpty()
                },
                label = {Text("Organisation Number")},
                isError = orgNumberError,
                supportingText = {if(orgNumberError) Text("Required")},
                modifier = Modifier.fillMaxWidth()
            )

            // Email Address Input
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = it.isEmpty()
                },
                label = {Text("Email Address")},
                isError = emailError,
                supportingText = {if(emailError) Text("Required")},
                modifier = Modifier.fillMaxWidth()
            )

            // Password input
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = it.isEmpty()
                },
                label = {Text("Password")},
                visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {passwordVisible != passwordVisible}) {
                        Icon(
                            imageVector = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                isError = passwordError,
                supportingText = {if(passwordError) Text("Required")},
                modifier = Modifier.fillMaxWidth()
            )

            // Remember me checkbox and forgot password hyperlink
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = {rememberMe = it}
                    )
                    Text("Remember me")
                }
                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable{
                        navController.navigate("forgot_password")
                    }
                )
            }

            // Sign in button
            Button(
                onClick = {
                    orgNumberError = orgNumber.isEmpty()
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()
                    if(!orgNumberError && !emailError && !passwordError) {
                        // Handle sign-in logic
                        navController.navigate("attendance_history")
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Sign In")
            }
        }
    }
}