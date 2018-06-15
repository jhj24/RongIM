package com.jhj.rongim.net

import android.app.Activity
import com.jhj.httplibrary.callback.HttpDialogCallback

/**
 * Created by jhj on 18-6-14.
 */
abstract class IMCallback<T>(activity: Activity, msg: String) : HttpDialogCallback<T>(activity, msg) {


    override fun onStringResponse(str: String?) {
        val result: IMResult<T>
        try {
            result = IMResult<T>().parseJson(str, getTClazz())
        } catch (e: Exception) {
            hanlder.post { callFailure(4) }
            return
        }
        if (result.code != 200) {
            hanlder.post { callFailure(5) }
            return
        }

        hanlder.post {
            onSuccess(result.token)
            onFinish()
        }
    }

}