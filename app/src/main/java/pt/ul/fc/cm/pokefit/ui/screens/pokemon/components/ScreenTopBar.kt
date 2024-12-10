package pt.ul.fc.cm.pokefit.ui.screens.pokemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
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
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("POKÉ")
                        }
                        withStyle(style = SpanStyle(color = Primary)) {
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
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Filters"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}