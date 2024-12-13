package pt.ul.fc.cm.pokefit.ui.screens.auth

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.navigation.Screen
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.Divider
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.GeneralTextField
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.ContinueWithButton
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.AuthenticationButton
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.PasswordTextField
import pt.ul.fc.cm.pokefit.ui.theme.Primary

@Composable
fun LoginScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(42.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginScreenHeader()
            Spacer(modifier = Modifier.size(24.dp))
            GeneralTextField(labelValue = stringResource(R.string.email))
            PasswordTextField(labelValue = stringResource(R.string.password))
            Spacer(modifier = Modifier.size(12.dp))
            NavigateToPasswordReset()
            Spacer(modifier = Modifier.size(8.dp))
            AuthenticationButton(
                navController = navController,
                labelValue = stringResource(R.string.sign_in)
            )
            Divider(top = 18.dp, bottom = 16.dp)
            ContinueWithButton(
                labelValue = stringResource(R.string.continue_with_google),
                painter = R.drawable.ic_google_logo
            )
            Spacer(modifier = Modifier.size(8.dp))
            ContinueWithButton(
                labelValue = stringResource(R.string.continue_with_apple),
                painter = R.drawable.ic_apple_logo
            )
            Spacer(modifier = Modifier.size(86.dp))
            NavigateToSignup(navController)
        }
    }
}

@Composable
private fun NavigateToSignup(navController: NavController) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(stringResource(R.string.dont_have_an_account))
            }
            append(" ")
            withLink(
                LinkAnnotation.Url(
                    Screen.Signup.route,
                    TextLinkStyles(style = SpanStyle(color = Primary))
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
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(stringResource(R.string.forgot_your_password))
            }
        },
    )
}

@Composable
private fun LoginScreenHeader() {
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
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(stringResource(R.string.poke))
            }
            withStyle(style = SpanStyle(color = Primary)) {
                append(stringResource(R.string.fit))
            }
        },
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.Bold
    )
}