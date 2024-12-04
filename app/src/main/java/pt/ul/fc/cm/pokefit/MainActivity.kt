package pt.ul.fc.cm.pokefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pt.ul.fc.cm.pokefit.ui.PokeFitApp
import pt.ul.fc.cm.pokefit.ui.theme.PokeFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeFitTheme {
                PokeFitApp()
            }
        }
    }
}