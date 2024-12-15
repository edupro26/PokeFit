package pt.ul.fc.cm.pokefit.ui.screens.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import pt.ul.fc.cm.pokefit.R

@Composable
fun DisplayName(
    displayName: String,
    onSignout: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = displayName,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        IconButton(
            onClick = onSignout,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_signout),
                contentDescription = "Sign out",
                tint = Color.Red
            )
        }
    }
}