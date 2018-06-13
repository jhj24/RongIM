package com.jhj.rongim

import android.app.Activity
import android.content.Context
import android.widget.Toast

/**
 * Created by jhj on 18-6-12.
 */
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}