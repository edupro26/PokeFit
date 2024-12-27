package pt.ul.fc.cm.pokefit.ui.screens.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.navigation.Screen
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.Divider
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.GeneralTextField
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.ContinueWithButton
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.AuthenticationButton
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.PasswordTextField

@Composable
fun SigninScreen(
    navController: NavController,
    viewModel: SigninViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                navController = navController,
                state = state,
                labelValue = stringResource(R.string.sign_in),
                onClick = { viewModel.signIn(email, password) }
            )
            Divider(top = 18.dp, bottom = 16.dp)
            ContinueWithButton(
                navController = navController,
                state = state,
                labelValue = stringResource(R.string.continue_with_google),
                painter = R.drawable.ic_google_logo
            ) {credential ->
                viewModel.signInWithGoogle(credential)
            }
            Spacer(modifier = Modifier.size(106.dp))
            NavigateToSignup(navController)
        }
    }
}

@Composable
private fun NavigateToSignup(navController: NavController) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.dont_have_an_account))
            append(" ")
            withLink(
                LinkAnnotation.Url(
                    Screen.Signup.route,
                    TextLinkStyles(SpanStyle(color = MaterialTheme.colorScheme.primary))
                ) {
                    val route = (it as LinkAnnotation.Url).url
                    navController.navigate(route)
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
        painter = painterResource(id = R.drawable.ic_app_logo),
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