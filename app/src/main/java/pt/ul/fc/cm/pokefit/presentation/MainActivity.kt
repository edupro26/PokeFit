package pt.ul.fc.cm.pokefit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.ul.fc.cm.pokefit.presentation.navigation.NavGraph
import pt.ul.fc.cm.pokefit.presentation.ui.theme.PokeFitTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeFitTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}