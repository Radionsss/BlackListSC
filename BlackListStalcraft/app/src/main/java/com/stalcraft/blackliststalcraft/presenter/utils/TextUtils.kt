package com.stalcraft.blackliststalcraft.presenter.utils

import android.widget.TextView

object TextUtils {
    fun TextView.setTextSize(size:String?){
        if(size != null)  this.textSize=size.toFloat()
    }
}