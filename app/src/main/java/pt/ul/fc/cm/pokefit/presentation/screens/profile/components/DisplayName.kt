package pt.ul.fc.cm.pokefit.presentation.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import pt.ul.fc.cm.pokefit.R

@Composable
fun DisplayName(
    displayName: String,
    username: String,
    onSignout: () -> Unit
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Text(
                text = displayName,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "@$username",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                modifier = Modifier.alpha(0.5f)
            )
        }
        IconButton(onClick = onSignout) {
            Icon(
                painter = painterResource(id = R.drawable.ic_signout),
                contentDescription = "Sign out",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}