package com.example.pokefit.ui

import com.example.pokefit.ui.graphs.BottomBarItem
import com.example.pokefit.ui.graphs.BottomBarNavGraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope

@Composable
fun PokeFitApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val items = listOf(
                BottomBarItem.Home,
                BottomBarItem.Pokemon,
                BottomBarItem.Leaderboards,
                BottomBarItem.Profile
            )

            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination

            NavigationBar {
                items.forEach { item ->
                    AddItem(
                        item = item,
                        currentRoute = currentRoute,
                        navController = navController
                    )
                }
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Hello, PokeFit!",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        BottomBarNavGraph(navController = navController)
    }
}


@Composable
private fun RowScope.AddItem(
    item: BottomBarItem,
    currentRoute: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem (
        label = {
            Text(text = item.title)
        },
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = "Icon"
            )
        },
        selected = currentRoute?.hierarchy?.any {
            it.route == item.route
        } == true,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}