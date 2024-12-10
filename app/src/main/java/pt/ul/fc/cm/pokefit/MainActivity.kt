package pt.ul.fc.cm.pokefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import pt.ul.fc.cm.pokefit.ui.navigation.BottomBarNavGraph
import pt.ul.fc.cm.pokefit.ui.theme.PokeFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeFitTheme {
                val navController = rememberNavController()
                BottomBarNavGraph(navController = navController)
            }
        }
    }
}