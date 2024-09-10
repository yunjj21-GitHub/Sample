package com.yunjung.sample.presentation.login.manager

import android.content.Context
import android.widget.Button
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.yunjung.sample.constanct.Google
import com.yunjung.sample.domain.model.User
import com.yunjung.sample.util.SmplLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleLoginManager(
    private val context: Context?,
    loginBtn: Button
) {
    private lateinit var loginSuccessListener: (User) -> Unit
    private lateinit var googleIdOption: GetGoogleIdOption

    init {
        setLoginBtn(loginBtn)
        initGoogleIdOption()
    }

    private fun setLoginBtn(loginBtn: Button){
        loginBtn.setOnClickListener {
            logoutOnGoogle()
            loginWithGoogle()
        }
    }

    fun setLoginSuccessListener(callback: (User) -> Unit){
        loginSuccessListener = callback
    }

    private fun initGoogleIdOption(){
        googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(Google.WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun logoutOnGoogle(){
        val clearCredentialStateRequest = ClearCredentialStateRequest()

        CoroutineScope(Dispatchers.Default).launch {
            context?.let {
                CredentialManager.create(it)
                    .clearCredentialState(clearCredentialStateRequest)
            }
        }
    }

    private fun loginWithGoogle(){
        val credentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        CoroutineScope(Dispatchers.Default).launch {
            try {
                var credentialResponse: GetCredentialResponse? = null
                context?.let {
                    credentialResponse = CredentialManager.create(it).getCredential(
                        request = credentialRequest,
                        context = it
                    )
                }
                handleSignIn(credentialResponse)
            }catch (e: GetCredentialException) {
                SmplLogger.e("Google Login Error -> $e")
            }
        }
    }

    private fun handleSignIn(credentialResponse: GetCredentialResponse?){
        if(credentialResponse == null) return

        when(val credential = credentialResponse.credential) {
            is PublicKeyCredential -> {
                // TODO: 결과값 확인 후 후속 처리 구현 필요함
                val response = credential.authenticationResponseJson
                SmplLogger.d("Google Login (PublicKeyCredential) -> $response")
            }
            is PasswordCredential -> {
                // TODO: 결과값 확인 후 후속 처리 구현 필요함
                val userName = credential.id
                val pw = credential.password
                val data = credential.data
                SmplLogger.d("Google Login (PasswordCredential) -> $userName, $pw, $data")
            }
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val user = User(
                        email = googleIdTokenCredential.id,
                        gender = null,
                        birth = null,
                        profileImage = googleIdTokenCredential.profilePictureUri.toString(),
                        phoneNumber = googleIdTokenCredential.phoneNumber
                    )
                    loginSuccessListener(user)
                    SmplLogger.d("Google Login (CustomCredential) -> $user")
                }else {
                    SmplLogger.d("Google Login (CustomCredential) -> Unexpected type of credential")
                }
            }
            else -> {
                SmplLogger.d("Google Login -> Unexpected type of credential")
            }
        }
    }
}