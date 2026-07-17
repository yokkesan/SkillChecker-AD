package com.example.skillcheckerapp.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcheckerapp.ui.theme.DashboardAccent
import com.example.skillcheckerapp.ui.theme.DashboardBorder
import com.example.skillcheckerapp.ui.theme.DashboardCard
import com.example.skillcheckerapp.ui.theme.DashboardSubText
import com.example.skillcheckerapp.ui.theme.RepositoryInputBackground
import com.example.skillcheckerapp.ui.theme.RepositoryPlaceholder

@Composable
fun RepositoryForm(
    isRegistering: Boolean,
    errorMessage: String?,
    onRegister: (
        githubUrl: String,
        onSuccess: () -> Unit
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var githubUrl by remember {
        mutableStateOf("")
    }

    val focusManager =
        LocalFocusManager.current

    fun submit() {
        if (isRegistering) {
            return
        }

        onRegister(
            githubUrl
        ) {
            githubUrl = ""

            focusManager.clearFocus()
        }
    }

    Card(
        modifier =
            modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    DashboardCard
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
    ) {
        Column(
            modifier =
                Modifier.padding(24.dp),
            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Repository Register",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight =
                    FontWeight.SemiBold
            )

            OutlinedTextField(
                value = githubUrl,
                onValueChange = { value ->
                    githubUrl = value
                },
                modifier =
                    Modifier.fillMaxWidth(),
                enabled =
                    !isRegistering,
                singleLine = true,
                placeholder = {
                    Text(
                        text =
                            "https://github.com/user/repository",
                        color =
                            RepositoryPlaceholder,
                        fontSize = 14.sp
                    )
                },
                shape =
                    RoundedCornerShape(12.dp),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedTextColor =
                            Color.White,
                        unfocusedTextColor =
                            Color.White,
                        disabledTextColor =
                            DashboardSubText,
                        focusedContainerColor =
                            RepositoryInputBackground,
                        unfocusedContainerColor =
                            RepositoryInputBackground,
                        disabledContainerColor =
                            RepositoryInputBackground,
                        focusedBorderColor =
                            DashboardAccent,
                        unfocusedBorderColor =
                            DashboardBorder,
                        disabledBorderColor =
                            DashboardBorder,
                        cursorColor =
                            DashboardAccent
                    ),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Uri,
                        imeAction =
                            ImeAction.Done
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            submit()
                        }
                    )
            )

            Button(
                onClick = {
                    submit()
                },
                modifier =
                    Modifier.fillMaxWidth(),
                enabled =
                    !isRegistering,
                shape =
                    RoundedCornerShape(12.dp),
                border =
                    BorderStroke(
                        width = 1.dp,
                        color =
                            DashboardAccent
                    ),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            DashboardAccent,
                        contentColor =
                            Color.White,
                        disabledContainerColor =
                            DashboardAccent.copy(
                                alpha = 0.5f
                            ),
                        disabledContentColor =
                            Color.White.copy(
                                alpha = 0.7f
                            )
                    ),
                contentPadding =
                    PaddingValues(
                        vertical = 14.dp,
                        horizontal = 24.dp
                    )
            ) {
                if (isRegistering) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "登録",
                        fontSize = 16.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
    }
}