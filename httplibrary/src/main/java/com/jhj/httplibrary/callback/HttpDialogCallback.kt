package com.jhj.httplibrary.callback

import android.app.Activity
import com.jhj.httplibrary.callback.base.HttpCallback
import com.jhj.httplibrary.httpcall.HttpCall
import com.jhj.httplibrary.result.Result
import com.jhj.prompt.progress.LoadingFragment

/**
 * 返回对象为集合，带提示框的Callback
 * Created by jhj on 2017-12-30 0030.
 */
abstract class HttpDialogCallback<T>(private val activity: Activity, private val msg: String) : HttpCallback<T>() {


    private var loadingDialog = LoadingFragment.Builder(activity)

    override fun onFailure(msg: String?, errorCode: Int) {
        loadingDialog.dismiss()
    }

    /*override fun onSuccess(data: T?) {
        loadingDialog.dismiss()
    }*/

    override fun onStart() {
        loadingDialog
                .setOutSideCanccel(false)
                .setText(msg)
                .show()
    }
}