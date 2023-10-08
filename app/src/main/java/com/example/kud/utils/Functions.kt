package com.example.kud.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kud.data.model.auth.login.Data
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

fun deleteDataUser() {
    Paper.book().delete("user")
}

fun saveAddress(data: com.example.kud.data.model.address.list.Data) {
    Paper.book().write("address", data)
}

fun getAddress(): com.example.kud.data.model.address.list.Data? {
    return Paper.book().read<com.example.kud.data.model.address.list.Data>("address")
}

fun saveListAddress(data: List<com.example.kud.data.model.address.list.Data>) {
    Paper.book().write("listAddress", data)
}

fun getListAddress(): List<com.example.kud.data.model.address.list.Data>? {
    return Paper.book().read<List<com.example.kud.data.model.address.list.Data>>("listAddress")
}

fun showSweetAlert(
    context: Context,
    type: Int,
    title: String,
    content: String,
    confirmClickListener: (() -> Unit)? = null
) {
    val sweetAlertDialog = SweetAlertDialog(context, type)
    sweetAlertDialog.titleText = title
    sweetAlertDialog.contentText = content

    confirmClickListener?.let {
        sweetAlertDialog.setConfirmClickListener { sDialog ->
            sDialog.dismissWithAnimation()
            it.invoke()
        }
    }

    sweetAlertDialog.show()
}

//fun showSuccessDialog(context: Context) {
//    SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//        .setTitleText("Success")
//        .setContentText("Upload successfully completed!")
//        .setConfirmClickListener {
//            it.dismissWithAnimation()
//        }
//        .show()
//}

//fun hideLoading(context: Context){
//    val progressDialog = SweetAlertDialog(context)
//    progressDialog.dismissWithAnimation()
//}


//fun showErrorDialog(context: Context, errorMessage: String) {
//    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//        .setTitleText("Error")
//        .setContentText(errorMessage)
//        .setConfirmClickListener {
//            it.dismissWithAnimation()
//        }
//        .show()
//}



