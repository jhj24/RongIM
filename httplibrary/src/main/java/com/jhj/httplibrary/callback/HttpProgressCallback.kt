package com.jhj.httplibrary.callback

import android.app.Activity
import com.jhj.httplibrary.callback.base.BaseProgressCallback
import com.jhj.prompt.progress.PercentFragment

/**
 * 带有进度条的Callback
 * Created by jhj on 2017-12-31 0031.
 */
abstract class HttpProgressCallback<T>(private val activity: Activity, private val msg: String) : BaseProgressCallback<T>() {

    private var percentDialog = PercentFragment.Builder(activity)

    override fun onFailure(msg: String?, errorCode: Int) {
        percentDialog.dismiss()
    }

    override fun onSuccess(data: T?) {
        percentDialog.dismiss()
    }

    override fun onStart() {
        percentDialog
                .setText(msg)
                .setOutSideCanccel(false)
                .show()
    }

    override fun onProgress(current: Long, total: Long) {
        if (percentDialog.getMaxProgress() != current.toInt()) {
            val progress = (current * 100.0 / total).toInt()
            percentDialog.setScaleDisplay()
                    .setProgress(progress)
        } else {
            percentDialog.dismiss()
        }
    }
}