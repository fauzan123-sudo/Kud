package com.example.kud.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import io.paperdb.Paper

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun saveDataUser(data: Data) {
    Paper.book().write("user", data)
}

fun getDataUser(): Data? {
    return Paper.book().read<Data>("user")
}

fun deleteDataUser(){
    Paper.book().delete("user")
}

