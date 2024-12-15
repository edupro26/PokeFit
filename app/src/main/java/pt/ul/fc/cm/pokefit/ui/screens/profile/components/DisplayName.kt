package pt.ul.fc.cm.pokefit.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import pt.ul.fc.cm.pokefit.R

@Composable
fun DisplayName(
    displayName: String,
    username: String,
    onSignout: () -> Unit
) {
    val info = buildNameString(displayName, username)
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = info,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        IconButton(onClick = onSignout) {
            Icon(
                painter = painterResource(id = R.drawable.ic_signout),
                contentDescription = "Sign out",
                tint = Color.Red
            )
        }
    }
}

@Composable
private fun buildNameString(
    displayName: String,
    username: String
): AnnotatedString {
    val info = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        ) {
            append(displayName)
        }
        append("\n")
        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        ) {
            append("@$username")
        }
    }
    return info
}