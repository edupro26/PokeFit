package pt.ul.fc.cm.pokefit.presentation.screens.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

@Composable
fun LocationUpdate(mapViewModel: MapViewModel) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Gerenciador de Permissões
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startLocationUpdates(fusedLocationClient, mapViewModel)
        } else {
            Toast.makeText(context, "Permissão de localização negada.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permissão concedida, inicie as atualizações de localização
                startLocationUpdates(fusedLocationClient, mapViewModel)
            }
            else -> {
                // Solicitar permissão
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}

private fun startLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    mapViewModel: MapViewModel
) {
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        5000L // Intervalo de 5 segundos
    ).apply {
        setMinUpdateIntervalMillis(2000L) // Intervalo mínimo de 2 segundos
        setWaitForAccurateLocation(true)
    }.build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach { location ->
                mapViewModel.saveRoutePoint(location)
            }
        }
    }

    try {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}
