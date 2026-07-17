package com.example.skillcheckerapp.security

import android.content.Context
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties

class TokenManager(
    context: Context
) {

    private val sharedPreferences =
        context.applicationContext
            .getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )

    fun saveToken(
        token: String
    ) {
        require(
            token.isNotBlank()
        ) {
            "Token must not be blank."
        }

        val cipher =
            Cipher.getInstance(
                TRANSFORMATION
            )

        cipher.init(
            Cipher.ENCRYPT_MODE,
            getOrCreateSecretKey()
        )

        val encryptedToken =
            cipher.doFinal(
                token.toByteArray(
                    Charsets.UTF_8
                )
            )

        val encodedToken =
            Base64.encodeToString(
                encryptedToken,
                Base64.NO_WRAP
            )

        val encodedInitializationVector =
            Base64.encodeToString(
                cipher.iv,
                Base64.NO_WRAP
            )

        sharedPreferences
            .edit()
            .putString(
                TOKEN_KEY,
                encodedToken
            )
            .putString(
                INITIALIZATION_VECTOR_KEY,
                encodedInitializationVector
            )
            .apply()
    }

    fun getToken(): String? {
        val encodedToken =
            sharedPreferences.getString(
                TOKEN_KEY,
                null
            )
                ?: return null

        val encodedInitializationVector =
            sharedPreferences.getString(
                INITIALIZATION_VECTOR_KEY,
                null
            )
                ?: return null

        return try {
            val encryptedToken =
                Base64.decode(
                    encodedToken,
                    Base64.NO_WRAP
                )

            val initializationVector =
                Base64.decode(
                    encodedInitializationVector,
                    Base64.NO_WRAP
                )

            val cipher =
                Cipher.getInstance(
                    TRANSFORMATION
                )

            cipher.init(
                Cipher.DECRYPT_MODE,
                getOrCreateSecretKey(),
                GCMParameterSpec(
                    GCM_TAG_LENGTH,
                    initializationVector
                )
            )

            cipher.doFinal(
                encryptedToken
            ).toString(
                Charsets.UTF_8
            )

        } catch (exception: Exception) {
            clearToken()

            null
        }
    }

    fun hasToken(): Boolean {
        return !getToken().isNullOrBlank()
    }

    fun clearToken() {
        sharedPreferences
            .edit()
            .remove(
                TOKEN_KEY
            )
            .remove(
                INITIALIZATION_VECTOR_KEY
            )
            .apply()
    }

    private fun getOrCreateSecretKey():
            SecretKey {

        val keyStore =
            KeyStore.getInstance(
                ANDROID_KEY_STORE
            ).apply {
                load(null)
            }

        val existingKey =
            keyStore.getKey(
                KEY_ALIAS,
                null
            )

        if (
            existingKey is SecretKey
        ) {
            return existingKey
        }

        val keyGenerator =
            KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEY_STORE
            )

        val keySpecification =
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(
                    KeyProperties.BLOCK_MODE_GCM
                )
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_NONE
                )
                .setKeySize(
                    AES_KEY_SIZE
                )
                .build()

        keyGenerator.init(
            keySpecification
        )

        return keyGenerator.generateKey()
    }

    private companion object {

        const val PREFERENCES_NAME =
            "skill_checker_secure_preferences"

        const val TOKEN_KEY =
            "authentication_token"

        const val INITIALIZATION_VECTOR_KEY =
            "authentication_token_iv"

        const val ANDROID_KEY_STORE =
            "AndroidKeyStore"

        const val KEY_ALIAS =
            "skill_checker_authentication_key"

        const val TRANSFORMATION =
            "AES/GCM/NoPadding"

        const val AES_KEY_SIZE =
            256

        const val GCM_TAG_LENGTH =
            128
    }
}