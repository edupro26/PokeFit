package pt.ul.fc.cm.pokefit.presentation.screens.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen
import pt.ul.fc.cm.pokefit.presentation.screens.auth.components.AuthenticationButton
import pt.ul.fc.cm.pokefit.presentation.screens.auth.components.ContinueWithGoogle
import pt.ul.fc.cm.pokefit.presentation.screens.auth.components.Divider
import pt.ul.fc.cm.pokefit.presentation.screens.auth.components.GeneralTextField
import pt.ul.fc.cm.pokefit.presentation.screens.auth.components.PasswordTextField

@Composable
fun SigninScreen(
    navigate: (String, Boolean) -> Unit,
    viewModel: SigninViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(42.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SigninContent(navigate, viewModel)
            }
            if (state.success) {
                navigate(Screen.Home.route, true)
            }
            if (!state.error.isNullOrBlank()) {
                Toast.makeText(
                    context,
                    state.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
private fun SigninContent(
    navigate: (String, Boolean) -> Unit,
    viewModel: SigninViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    SigninScreenHeader()
    Spacer(modifier = Modifier.size(24.dp))
    GeneralTextField(
        value = email,
        labelValue = stringResource(R.string.email),
        onValueChange = { value -> email = value }
    )
    PasswordTextField(
        value = password,
        labelValue = stringResource(R.string.password),
        onValueChange = { value -> password = value }
    )
    Spacer(modifier = Modifier.size(12.dp))
    NavigateToPasswordReset()
    Spacer(modifier = Modifier.size(8.dp))
    AuthenticationButton(
        labelValue = stringResource(R.string.sign_in),
        onClick = { viewModel.signIn(email, password) }
    )
    Divider(top = 18.dp, bottom = 16.dp)
    ContinueWithGoogle(
        labelValue = stringResource(R.string.continue_with_google)
    ) {
        credential -> viewModel.signInWithGoogle(credential)
    }
    Spacer(modifier = Modifier.size(106.dp))
    NavigateToSignup(navigate)
}

@Composable
private fun NavigateToSignup(navigate: (String, Boolean) -> Unit) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.dont_have_an_account))
            append(" ")
            withLink(
                LinkAnnotation.Clickable(
                    tag = stringResource(R.string.sign_up),
                    styles = TextLinkStyles(SpanStyle(MaterialTheme.colorScheme.primary))
                ) {
                    navigate(Screen.Signup.route, false)
                }
            ) {
                append(stringResource(R.string.sign_up))
            }
        }
    )
}

@Composable
private fun NavigateToPasswordReset() {
    Text(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        text = buildAnnotatedString {
            /* TODO: implement withLink functionality */
            append(stringResource(R.string.forgot_your_password))
        },
    )
}

@Composable
private fun SigninScreenHeader() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp),
        contentDescription = null,
        painter = painterResource(id = R.drawable.ic_logo_pokefit),
    )
    Spacer(modifier = Modifier.size(32.dp))
    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.poke))
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(R.string.fit))
            }
        },
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.Bold
    )
}