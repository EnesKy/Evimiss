package com.enesky.evimiss.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.enesky.evimiss.App
import com.enesky.evimiss.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isUserAvailable: Boolean = false
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var credentialListener: ((credential: AuthCredential) -> Unit)? = null

    override fun onStart() {
        super.onStart()
        val currentUser = App.mAuth?.currentUser
        isUserAvailable = currentUser != null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NavigationFromSplash(isUserAvailable) }
        registerForGoogleSignIn()
    }

    private fun registerForGoogleSignIn() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    credentialListener?.invoke(credential)
                } catch (e: ApiException) {
                    Log.d("Google Sign-In", "Google sign in failed", e)
                }
            }
            Log.d("registerForActResult", result.toString())
        }
    }

    fun signInWithGoogle(listener: (credential: AuthCredential) -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            //.requestScopes(Scope("https://www.googleapis.com/auth/calendar"))
            //.requestScopes(Scope("https://www.googleapis.com/auth/calendar.events"))
            //.requestScopes(Scope("https://www.googleapis.com/auth/drive.file"))
            //.requestScopes(Scope("https://www.googleapis.com/auth/spreadsheets"))
            .build()

        credentialListener = listener

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        resultLauncher.launch(googleSignInClient.signInIntent)
    }

}