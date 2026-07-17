package com.example.skillcheckerapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcheckerapp.model.LoginRequest
import com.example.skillcheckerapp.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateEmail(
        value: String
    ) {
        email = value

        errorMessage = null
    }

    fun updatePassword(
        value: String
    ) {
        password = value

        errorMessage = null
    }

    fun login(
        onSuccess: () -> Unit
    ) {
        val normalizedEmail =
            email.trim()

        if (
            normalizedEmail.isBlank() ||
            password.isBlank()
        ) {
            errorMessage =
                "メールアドレスとパスワードを入力してください。"

            return
        }

        if (
            isLoading
        ) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response =
                    RetrofitClient.authApi.login(
                        LoginRequest(
                            email = normalizedEmail,
                            password = password
                        )
                    )

                RetrofitClient
                    .getTokenManager()
                    .saveToken(
                        response.token
                    )

                password = ""

                onSuccess()

            } catch (
                exception: HttpException
            ) {
                errorMessage =
                    when (
                        exception.code()
                    ) {
                        401,
                        422 -> {
                            "メールアドレスまたはパスワードが正しくありません。"
                        }

                        else -> {
                            "ログインに失敗しました。"
                        }
                    }

            } catch (
                exception: IOException
            ) {
                errorMessage =
                    "サーバーに接続できませんでした。"

            } catch (
                exception: Exception
            ) {
                errorMessage =
                    "ログイン処理中にエラーが発生しました。"

                exception.printStackTrace()

            } finally {
                isLoading = false
            }
        }
    }

    fun clearErrorMessage() {
        errorMessage = null
    }
}