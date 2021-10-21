package com.example.streetmusic2.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.streetmusic2.common.navigation.MainNavGraph
import com.example.streetmusic2.ui.theme.StreetMusic2Theme
import com.example.streetmusic2.util.time.LocalTimeUtil
import com.example.streetmusic2.util.time.TimeUtil
import com.example.streetmusic2.util.user.LocalUserPref
import com.example.streetmusic2.util.user.UserSharedPreferences
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalAnimatedInsets
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userUtil: UserSharedPreferences
    @Inject
    lateinit var timeUtil: TimeUtil

    @ExperimentalCoroutinesApi
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController: NavHostController = rememberNavController()
            val systemUiController = rememberSystemUiController()

            SideEffect {
                // Update all of the system bar colors to be transparent
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = false
                )
            }

            CompositionLocalProvider(
                LocalUserPref provides userUtil,
                LocalTimeUtil provides timeUtil,
                LocalSystemUiController provides systemUiController
            ) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    StreetMusic2Theme {
                        Surface(modifier = Modifier.background(MaterialTheme.colors.background)) {
                            Log.i("MyMusic", "1. Activity")
                            MainNavGraph(navController)
                        }
                    }
                }
            }
        }
    }
}

val LocalSystemUiController = staticCompositionLocalOf<SystemUiController> {
    error("No SystemUiController found!")
}