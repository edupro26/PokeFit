package pt.ul.fc.cm.pokefit.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen

@Composable
fun BottomAppBar(navController: NavController) {
    val items = listOf(
        BottomBarItem.Home,
        BottomBarItem.Pokemon,
        BottomBarItem.Leaderboards,
        BottomBarItem.Profile
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination
    NavigationBar (
        modifier = Modifier.clip(MaterialTheme.shapes.medium),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        items.forEach {
            item -> AddItem(item, currentRoute, navController)
        }
    }
}

@Composable
private fun RowScope.AddItem(
    item: BottomBarItem,
    currentRoute: NavDestination?,
    navController: NavController
) {
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
        colors =  NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.secondary
        ),
        selected = currentRoute?.hierarchy?.any {
            it.route == item.route
        } == true,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(Screen.Home.route)
                launchSingleTop = true
            }
        }
    )
}