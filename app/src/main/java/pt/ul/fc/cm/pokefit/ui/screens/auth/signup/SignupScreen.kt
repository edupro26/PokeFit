package pt.ul.fc.cm.pokefit.ui.screens.auth.signup

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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.navigation.Screen
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.ContinueWithButton
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.Divider
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.GeneralTextField
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.AuthenticationButton
import pt.ul.fc.cm.pokefit.ui.screens.auth.components.PasswordTextField

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
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
            SignupScreenHeader()
            Spacer(modifier = Modifier.size(24.dp))
            GeneralTextField(
                value = name,
                labelValue = stringResource(R.string.name),
                onValueChange = { value -> name = value }
            )
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
            AuthenticationButton(
                navController = navController,
                state = state,
                labelValue = stringResource(R.string.sign_up),
                onClick = { viewModel.signUp(email, password, name) }
            )
            Divider(top = 18.dp, bottom = 16.dp)
            ContinueWithButton(
                navController = navController,
                state = state,
                labelValue = stringResource(R.string.continue_with_google),
                painter = R.drawable.ic_logo_google
            ) { credential ->
                viewModel.signUpWithGoogle(credential)
            }
            Spacer(modifier = Modifier.size(96.dp))
            NavigateToSignin(navController)
        }
    }
}

@Composable
private fun NavigateToSignin(navController: NavController) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.already_have_an_account))
            append(" ")
            withLink(
                LinkAnnotation.Url(
                    Screen.Signin.route,
                    TextLinkStyles(SpanStyle(color = MaterialTheme.colorScheme.primary))
                ) {
                    val route = (it as LinkAnnotation.Url).url
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        navController.graph.setStartDestination(route)
                    }
                }
            ) {
                append(stringResource(R.string.sign_in))
            }
        },
    )
}

@Composable
private fun SignupScreenHeader() {
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
            append(stringResource(R.string.create_an_account))
        },
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.Bold
    )
}