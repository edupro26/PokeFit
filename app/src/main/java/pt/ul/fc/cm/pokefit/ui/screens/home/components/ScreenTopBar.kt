package pt.ul.fc.cm.pokefit.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(38.dp),
                    painter = painterResource(id = R.drawable.ic_app_logo),
                    contentDescription = "App Logo"
                )
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.poke))
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("FIT")
                        }
                    },
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Goals"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}