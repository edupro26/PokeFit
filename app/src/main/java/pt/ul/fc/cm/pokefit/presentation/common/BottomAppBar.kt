package pt.ul.fc.cm.pokefit.presentation.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomAppBar(
    navController: NavController,
    navigate: (String, Boolean) -> Unit
) {
    val items = listOf(
        BottomBarItem.Home,
        BottomBarItem.Pokemon,
        BottomBarItem.Leaderboards,
        BottomBarItem.Profile
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem (
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = "Icon"
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent
                ),
                onClick = { navigate(item.route, false) },
                selected = item.route == currentRoute
            )
        }
    }
}