package pt.ul.fc.cm.pokefit.presentation.common

import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import pt.ul.fc.cm.pokefit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    firstIcon: Int,
    firstDescription: String,
    onFirstIconClick: () -> Unit,
    secondIcon: Int? = null,
    secondDescription: String? = null,
    onSecondIconClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.poke))
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(stringResource(id = R.string.fit))
                        }
                    },
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        actions = {
            IconButton(onClick = onFirstIconClick) {
                Icon(
                    painter = painterResource(firstIcon),
                    contentDescription = firstDescription
                )
            }
            if (secondIcon != null) {
                IconButton(onClick = onSecondIconClick) {
                    Icon(
                        painter = painterResource(secondIcon),
                        contentDescription = secondDescription!!
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}