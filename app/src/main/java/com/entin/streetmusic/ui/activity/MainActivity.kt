package com.entin.streetmusic.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.navigation.MainNavGraph
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.data.auth.createAnonymousUser
import com.entin.streetmusic.data.auth.deleteAnonymousUser
import com.entin.streetmusic.data.time.LocalTimeUtil
import com.entin.streetmusic.data.time.TimeUtil
import com.entin.streetmusic.data.user.LocalUserPref
import com.entin.streetmusic.data.user.UserSession
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@ExperimentalAnimatedInsets
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userUtil: UserSession

    @Inject
    lateinit var timeUtil: TimeUtil

    @OptIn(InternalCoroutinesApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
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
                    StreetMusicTheme {
                        Surface {
                            MainNavGraph(navController)
                        }
                    }
                }
            }
        }
    }

    /**
     * Creating anon user to get concerts list
     */
    override fun onRestart() {
        super.onRestart()
        createAnonymousUser()
    }

    /**
     * Delete anon user if normal exit
     */
    override fun onStop() {
        super.onStop()
        deleteAnonymousUser()
    }
}

val LocalSystemUiController = staticCompositionLocalOf<SystemUiController> {
    error("No SystemUiController found!")
}