package com.development.github.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import androidx.core.app.NotificationManagerCompat
import com.development.github.R

object NotificationPermissionDialog {

    fun showPermissionDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.notification_permission_title)
            .setMessage(R.string.notification_permission_message)
            .setPositiveButton(R.string.notification_permission_positive_button) { _, _ ->
                openNotificationSettings(context)
            }
            .setNegativeButton(R.string.notification_permission_negative_button, null)
            .show()
    }

    private fun openNotificationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        context.startActivity(intent)
    }

    fun isNotificationPermissionGranted(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}
