package com.example.skillcheckerapp.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillcheckerapp.ui.theme.DashboardAccent
import com.example.skillcheckerapp.ui.theme.DashboardBackground
import com.example.skillcheckerapp.ui.theme.DashboardBorder
import com.example.skillcheckerapp.ui.theme.DashboardCard
import com.example.skillcheckerapp.ui.theme.DashboardSubText
import com.example.skillcheckerapp.ui.theme.RepositoryInputBackground
import com.example.skillcheckerapp.ui.theme.RepositoryPlaceholder

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val focusManager =
        LocalFocusManager.current

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    DashboardBackground
                )
                .padding(
                    horizontal = 24.dp
                ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = DashboardCard,
                        shape = RoundedCornerShape(
                            16.dp
                        )
                    )
                    .padding(
                        24.dp
                    ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Skill Checker",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(
                    8.dp
                )
            )

            Text(
                text = "アカウントにログインしてください。",
                color = DashboardSubText,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(
                    32.dp
                )
            )

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = viewModel::updateEmail,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "メールアドレス"
                    )
                },
                placeholder = {
                    Text(
                        text = "test@example.com"
                    )
                },
                singleLine = true,
                enabled = !viewModel.isLoading,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                keyboardActions =
                    KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        }
                    ),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedTextColor =
                            MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor =
                            MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor =
                            RepositoryInputBackground,
                        unfocusedContainerColor =
                            RepositoryInputBackground,
                        focusedBorderColor =
                            DashboardAccent,
                        unfocusedBorderColor =
                            DashboardBorder,
                        focusedLabelColor =
                            DashboardAccent,
                        unfocusedLabelColor =
                            DashboardSubText,
                        focusedPlaceholderColor =
                            RepositoryPlaceholder,
                        unfocusedPlaceholderColor =
                            RepositoryPlaceholder,
                        cursorColor =
                            DashboardAccent
                    )
            )

            Spacer(
                modifier = Modifier.height(
                    16.dp
                )
            )

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::updatePassword,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "パスワード"
                    )
                },
                singleLine = true,
                enabled = !viewModel.isLoading,
                visualTransformation =
                    PasswordVisualTransformation(),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()

                            viewModel.login(
                                onSuccess =
                                    onLoginSuccess
                            )
                        }
                    ),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedTextColor =
                            MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor =
                            MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor =
                            RepositoryInputBackground,
                        unfocusedContainerColor =
                            RepositoryInputBackground,
                        focusedBorderColor =
                            DashboardAccent,
                        unfocusedBorderColor =
                            DashboardBorder,
                        focusedLabelColor =
                            DashboardAccent,
                        unfocusedLabelColor =
                            DashboardSubText,
                        cursorColor =
                            DashboardAccent
                    )
            )

            viewModel.errorMessage?.let {
                Spacer(
                    modifier = Modifier.height(
                        12.dp
                    )
                )

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(
                modifier = Modifier.height(
                    24.dp
                )
            )

            Button(
                onClick = {
                    focusManager.clearFocus()

                    viewModel.login(
                        onSuccess =
                            onLoginSuccess
                    )
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            50.dp
                        ),
                enabled = !viewModel.isLoading,
                shape = RoundedCornerShape(
                    10.dp
                ),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            DashboardAccent
                    )
            ) {
                if (
                    viewModel.isLoading
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(
                            24.dp
                        ),
                        strokeWidth = 2.dp,
                        color = DashboardBackground
                    )
                } else {
                    Text(
                        text = "ログイン",
                        color = DashboardBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}