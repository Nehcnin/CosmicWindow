package com.example.cosmicwindow.feature.earth.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import androidx.core.content.edit
import androidx.core.net.toUri

@Composable
fun LocationPermissionButton(
    enabled: Boolean,
    onEnableChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permission = Manifest.permission.ACCESS_COARSE_LOCATION

    val preferences = remember(context) {
        context.getSharedPreferences(
            "location_permission",
            android.content.Context.MODE_PRIVATE
        )
    }

    var requestedBefore by remember {
        mutableStateOf(
            preferences.getInt("requested_before", 0)
        )
    }

    var pendingSettings by rememberSaveable {
        mutableStateOf(false)
    }

    fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            requestedBefore++

            preferences.edit {
                putInt("requested_before", requestedBefore)
            }
        }
        onEnableChange(granted)
    }

    DisposableEffect(lifecycleOwner, enabled, pendingSettings) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val granted = hasPermission()

                if (pendingSettings) {
                    pendingSettings = false
                    onEnableChange(granted)
                } else if (!granted && enabled) {
                    onEnableChange(false)
                }

            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    IconButton(
        onClick = {
            when {
                enabled -> {
                    onEnableChange(false)
                }

                hasPermission() -> {
                    onEnableChange(true)
                }

                requestedBefore >= 2 -> {
                    pendingSettings = true

                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        "package:${context.packageName}".toUri()
                    )

                    context.startActivity(intent)
                }

                else -> {
                    launcher.launch(permission)
                }
            }
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(
                if (enabled) {
                    R.drawable.ic_location
                } else {
                    R.drawable.ic_location_off
                }
            ),
            contentDescription = "",
            tint = CosmicTheme.colors.selected,
        )
    }


}
