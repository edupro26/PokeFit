package pt.ul.fc.cm.pokefit.presentation.screens.auth.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen
import pt.ul.fc.cm.pokefit.utils.Constants.WEB_CLIENT_ID
import pt.ul.fc.cm.pokefit.utils.Response

@Composable
fun AuthenticationButton(
    navController: NavController,
    state: Response<Unit>,
    labelValue: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier.fillMaxWidth().heightIn(48.dp),
        onClick = { onClick() },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = labelValue,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
    HandleAuthResponse(state, navController)
}

@Composable
fun ContinueWithButton(
    navController: NavController,
    state: Response<Unit>,
    labelValue: String,
    painter: Int,
    onGetCredential: (Credential) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Surface (
        modifier = Modifier.fillMaxWidth().heightIn(48.dp),
        onClick = {
            handleGoogleRequest(coroutineScope, context, onGetCredential)
        },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(36.dp).padding(end = 8.dp),
                painter = painterResource(id = painter),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Text(
                text = labelValue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        HandleAuthResponse(state, navController)
    }
}

@Composable
private fun HandleAuthResponse(
    state: Response<Unit>,
    navController: NavController
) {
    when (state) {
        is Response.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    navController.graph.setStartDestination(Screen.Home.route)
                }
            }
        }
        is Response.Failure -> {
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_LONG).show()
        }
        else -> {}
    }
}

private fun handleGoogleRequest(
    coroutineScope: CoroutineScope,
    context: Context,
    onGetCredential: (Credential) -> Unit
) {
    val credentialManager = CredentialManager.create(context)
    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(WEB_CLIENT_ID)
        .build()
    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            onGetCredential(result.credential)
        } catch (e: Exception) {
            Log.d("GoogleAuthButton", e.message.toString())
        }
    }
}