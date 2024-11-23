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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokefit.R
import com.example.pokefit.ui.theme.Primary
import com.example.pokefit.ui.theme.Transparent

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
            NavigationBar (
                modifier = Modifier.clip(MaterialTheme.shapes.medium)
            ) {
                items.forEach {
                    item -> AddItem(item, currentRoute, navController)
                }
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier.size(55.dp),
                    painter = painterResource(id = R.drawable.ic_app_logo),
                    contentDescription = "App Logo"
                )
                Text(
                    text = "PokéFit",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
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
            Text(
                text = item.title,
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
            selectedIconColor = Primary,
            selectedTextColor = Primary,
            indicatorColor = Transparent
        ),
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