package pt.ul.fc.cm.pokefit.ui.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.ui.theme.PrimaryGrey

@Composable
fun ProfilePicture(user: User) {
    if (user.photoUrl != null) {
        AsyncImage(
            model = user.photoUrl,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.trainer),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(1.dp, PrimaryGrey, CircleShape)
        )
    }
}