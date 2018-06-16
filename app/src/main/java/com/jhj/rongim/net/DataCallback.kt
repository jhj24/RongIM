package com.jhj.rongim.net

import com.jhj.httplibrary.callback.base.HttpCallback

/**
 * Created by jhj on 18-6-16.
 */
abstract class DataCallback<T> : HttpCallback<T>() {
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