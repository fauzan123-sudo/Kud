package com.example.kud.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kud.R
import com.example.kud.ui.activity.LoginActivity
import com.google.android.material.snackbar.Snackbar


fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.handleApiError(
    error: String?
) {
    when (error) {
        getString(R.string.failed_connect) -> Toast.makeText(
            context,
            "can`t connect to server, maybe server is bad server",
            Toast.LENGTH_SHORT
        ).show()
        getString(R.string.bad_connection) -> Toast.makeText(
            context,
            "Bad connection, check your connection",
            Toast.LENGTH_SHORT
        )
            .show()
        "{\"message\":\"Unauthenticated.\"}Wrong Email or Password" -> {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        else -> Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
    }

}