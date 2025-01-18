package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun DetailButton(
    text: String,
    containerColor: Color,
    textColor: Color,
    painter: Painter? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = { onClick() },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    containerColor,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
                if (painter != null) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        painter = painter,
                        contentDescription = "FitCoins",
                        tint = textColor
                    )
                }
            }
        }
    }
}