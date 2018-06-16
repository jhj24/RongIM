package com.jhj.rongim.net

import android.app.Activity
import com.jhj.httplibrary.callback.HttpDialogCallback
import java.lang.Exception

/**
 * Created by jhj on 18-6-15.
 */
open class DataDialogCallback<T>(activity: Activity, msg: String) : HttpDialogCallback<T>(activity, msg) {

    override fun onStringResponse(str: String?) {
        val result: DataResult<T>
        try {
            result = DataResult<T>().parseJson(str, getTClazz())
        } catch (e: Exception) {
            hanlder.post { callFailure(4) }
            return
        }

        if (result.status != 200) {
            hanlder.post { callFailure(5, result.msg) }
            return
        }

        hanlder.post {
            onSuccess(result.items)
            onFinish()
        }
    }
}