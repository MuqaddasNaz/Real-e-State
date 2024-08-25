package com.gulfrealestates.realestate.Utills

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.Window
import com.gulfrealestates.realestate.R
import com.google.android.material.snackbar.Snackbar


object Functions {

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


    fun setTransparentStatusBar(context: Context) {

        val activity = context as Activity

        val window: Window = activity.window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

    }

    fun pickImageFromGallery(context: Context) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        (context as Activity).startActivityForResult(intent, Const.PICK_FILE_REQUEST_CODE)
    }
    fun showSnackBar(context: Context, message: String) {
        val activity = context as Activity
        Snackbar.make(
            activity.findViewById(android.R.id.content), Html.fromHtml(
                "<i>$message</i>"
            ), Snackbar.LENGTH_LONG
        )
            .setBackgroundTint(context.getResources().getColor(R.color.white))
            .setTextColor(context.getResources().getColor(R.color.black))
            .setAction(
                Html.fromHtml("<b> OK </b>")
            ) { view: View? -> }
            .setActionTextColor(context.getResources().getColor(R.color.lightBlue)).show()
    }



}


