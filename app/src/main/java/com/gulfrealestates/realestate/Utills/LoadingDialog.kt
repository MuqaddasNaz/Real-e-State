package com.gulfrealestates.realestate.Utills

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.gulfrealestates.realestate.R

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.loading_dialog)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
