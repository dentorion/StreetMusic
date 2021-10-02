package com.example.streetmusic2.ui.authorizate

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streetmusic2.R
import com.example.streetmusic2.common.model.responce.AuthorizeResponse
import com.example.streetmusic2.ui.start.components.BackgroundImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Authorize(
    viewModel: AuthorizeViewModel = hiltViewModel(),
    navToPreConcert: (String) -> Unit,
    navToConcert: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BackgroundImage()

        Log.i("MyMusic", "7.Authorize")

        when (val state = viewModel.authorizeState) {
            is AuthorizeResponse.Error -> {
                Log.i("MyMusic", "7.Authorize.E")
                ErrorAuth()
            }

            is AuthorizeResponse.Initial -> {
                Log.i("MyMusic", "7.Authorize.I")
                InitialAuth(
                    currentUser = Firebase.auth.currentUser,
                    signWithCredential = { viewModel.signWithCredential(it) },
                    checkOnLineConcertsByUser = { viewModel.checkOnlineConcertByUser(it) },
                )
            }

            is AuthorizeResponse.Load -> {
                Log.i("MyMusic", "7.Authorize.L")
                LoadAuth()
            }

            is AuthorizeResponse.Success -> {
                Log.i("MyMusic", "7.Authorize.S")
                state.user?.let { user ->
                    viewModel.checkOnlineConcertByUser(user.uid)
                }
            }

            is AuthorizeResponse.Navigate -> {
                Log.i("MyMusic", "7.Authorize.N")
                if (state.value) {
                    Log.i(
                        "MyMusic",
                        "7.Authorize.CheckAndNavigate is running.[T] UID: ${state.userId}"
                    )
                    navToPreConcert(state.userId)
                } else {
                    Log.i(
                        "MyMusic",
                        "7.Authorize.CheckAndNavigate is running.[F] UID: ${state.userId}"
                    )
                    navToConcert(state.userId)
                }
            }
        }
    }
}

@Composable
fun LoadAuth() {
    Column(modifier = Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = Color.White)
        Text("Please, wait", color = Color.White)
    }
}

@Composable
fun ErrorAuth() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gradientBottom)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.error), color = Color.White)
    }
}

@Composable
private fun InitialAuth(
    signWithCredential: (AuthCredential) -> Unit,
    checkOnLineConcertsByUser: (String) -> Unit,
    currentUser: FirebaseUser?
) {
    Log.i("MyMusic", "7.Authorize.InitialAuth")

    if (currentUser != null) {
        checkOnLineConcertsByUser(currentUser.uid)
    } else {
        AuthorizationContent(signWithCredential = { signWithCredential(it) })
    }
}

@Composable
fun AuthorizationContent(
    signWithCredential: (AuthCredential) -> Unit
) {
    Log.i("MyMusic", "7.Authorize.AuthorizationContent")

    // Equivalent of onActivityResult
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                signWithCredential(credential)
            } catch (e: ApiException) {
                Log.i("MyMusic", "Google sign in failed", e)
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.secondaryVariant,
                        MaterialTheme.colors.secondary
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current
            val token = stringResource(R.string.default_web_client_id)

            Image(
                modifier = Modifier
                    .fillMaxSize(0.5F)
                    .padding(bottom = 8.dp),
                painter = painterResource(id = R.drawable.ic_concert_auth),
                contentDescription = null,
            )

            Text(
                text = "Sign in with:", fontSize = 14.sp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            )

            OutlinedButton(
                border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                tint = Color.Unspecified,
                                painter = painterResource(id = R.drawable.googleg_standard_color_18),
                                contentDescription = null,
                            )
                            Text(
                                style = MaterialTheme.typography.button,
                                color = MaterialTheme.colors.onSurface,
                                text = "Google"
                            )
                            Icon(
                                tint = Color.Transparent,
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                            )
                        }
                    )
                }
            )
        }
    }
}