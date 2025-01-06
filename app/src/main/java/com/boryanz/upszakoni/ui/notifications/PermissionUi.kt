package com.boryanz.upszakoni.ui.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionUi(
    permission: String,
    onDenied: () -> Unit,
    onApproved: () -> Unit,
) {

    val permissionState = rememberPermissionState(permission) { isApproved ->
        if (isApproved) {
            onApproved()
        } else {
            onDenied()
        }
    }

    LaunchedEffect(permissionState) {
        when (permissionState.status) {
            is PermissionStatus.Denied -> {
                permissionState.launchPermissionRequest()
            }

            PermissionStatus.Granted -> {
                onApproved()
            }
        }
    }
}