package com.dicoding.jetreward

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.jetreward.ui.navigation.NavigationItem
import com.dicoding.jetreward.ui.navigation.Screen
import com.dicoding.jetreward.ui.screen.cart.CartScreen
import com.dicoding.jetreward.ui.screen.home.HomeScreen
import com.dicoding.jetreward.ui.screen.profile.ProfileScreen
import com.dicoding.jetreward.ui.theme.JetRewardTheme

@Composable
fun JetRewardApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomBar(navController) },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    BottomNavigation(modifier = modifier) {
        val navigationItems = listOf(
            NavigationItem(stringResource(R.string.menu_home), Icons.Default.Home, Screen.Home),
            NavigationItem(stringResource(R.string.menu_cart), Icons.Default.ShoppingCart, Screen.Cart),
            NavigationItem(stringResource(R.string.menu_profile), Icons.Default.AccountCircle, Screen.Profile)
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    selected = true,
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = { Text(item.title) },
                    onClick = {
                        navController.navigate(item.screen.route) { // to execute navigation based on route
                            popUpTo(navController.graph.findStartDestination().id) { // popUpTo to clear backstack
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetRewardTheme {
        JetRewardApp()
    }
}
