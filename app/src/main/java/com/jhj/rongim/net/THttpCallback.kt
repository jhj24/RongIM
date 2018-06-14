package com.jhj.rongim.net

import com.jhj.httplibrary.callback.base.HttpCallback

/**
 * Created by jhj on 18-6-14.
 */
abstract class THttpCallback<T> : HttpCallback<T>() {


    override fun onStringResponse(str: String?) {
        val result: TResult<T>
        try {
            result = TResult<T>().parseJson(str, getTClazz())
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