package com.enesky.evimiss.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.enesky.evimiss.App
import com.enesky.evimiss.data.repo.CalendarDataSource
import com.enesky.evimiss.utils.AuthUtil
import com.enesky.evimiss.utils.GoogleSignInUtil
import com.enesky.evimiss.utils.PermissionsUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleSignInUtil: GoogleSignInUtil

    @Inject
    lateinit var permissionsUtil: PermissionsUtil

    @Inject
    lateinit var calendarDataSource: CalendarDataSource

    @Inject
    lateinit var authUtil: AuthUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleSignInUtil.registerForGoogleSignIn()

        setContent {
            NavigationFromSplash()
            if (App.mAuth?.currentUser != null)
                permissionsUtil.RequestCalendarPermissions {
                    LaunchedEffect(Unit) {
                        calendarDataSource.init()
                    }
                }
        }

    }

}