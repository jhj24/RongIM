package com.jhj.httplibrary.callback.base

import android.os.Handler
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 网络请求基础Callback
 * Created by jhj on 2017-12-28 0028.
 */
abstract class HttpCallback<T> : Callback {


    var error: HashMap<Int, String> = HashMap()

    init {
        error[1] = "请求失败，请重试"
        error[2] = "请求失败"
        error[3] = "数据异常"
        error[4] = "数据格式异常"
        error[5] = "服务器异常" //网络没返回异常原因时，默认是服务器异常
        error[6] = "请求取消"
    }

    val hanlder = Handler()

    override fun onFailure(call: Call, e: IOException) {
        if (call.isCanceled) {
            hanlder.post { callFailure(6) }
            return
        }

        hanlder.post { callFailure(1) }
    }

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            hanlder.post { callFailure(2) }
            return
        }
        val str = response.body()?.string()
        if (str.isNullOrBlank()) {
            hanlder.post { callFailure(3) }
            return
        }
        onStringResponse(str)
    }

    fun callFailure(errorCode: Int, msg: String? = null) {
        onFailure(msg ?: error[errorCode] ?: "", errorCode)
        onFinish()
    }


    open fun onStart() {
    }

    open fun onFinish() {
    }

    /**
     * 获取泛参数实际类型
     */
    fun getTClazz(): Type {
        //获取当前类带有泛型的父类
        val clazz = this.javaClass.genericSuperclass
        return if (clazz is ParameterizedType) {
            //获取父类的泛型参数（参数可能有多个，获取第一个）
            clazz.actualTypeArguments[0]
        } else {
            clazz
        }
    }

    abstract fun onSuccess(data: T?)

    abstract fun onFailure(msg: String?, errorCode: Int)

    /**
     * 由于后台处理差异，返回的json数据格式以及名字不一样，需要进行单独的处理。首先json解析，一般可以根据返回码、返回msg、返回数据进行处理
     */
    abstract fun onStringResponse(str: String?)

}