package pt.ul.fc.cm.pokefit.ui.screens.auth.components

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.ui.navigation.Screen
import pt.ul.fc.cm.pokefit.ui.theme.Primary
import pt.ul.fc.cm.pokefit.ui.theme.PrimaryGrey
import pt.ul.fc.cm.pokefit.utils.Response

@Composable
fun AuthenticationButton(
    navController: NavController,
    state: Response<Unit>,
    labelValue: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(42.dp),
        onClick = { onClick() },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    Primary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = labelValue,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
    HandleButtonResponse(state, navController)
}

@Composable
private fun HandleButtonResponse(
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
            Toast.makeText(
                LocalContext.current,
                state.error,
                Toast.LENGTH_LONG
            ).show()
        }
        else -> {}
    }
}

@Composable
fun ContinueWithButton(
    labelValue: String,
    painter: Int
) {
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(15.dp),
        color = PrimaryGrey,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = painter),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Text(
                text = labelValue,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}