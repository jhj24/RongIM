package com.jhj.httplibrary.httpcall

import android.annotation.SuppressLint
import android.content.Context
import com.jhj.httplibrary.request.GetRequest
import com.jhj.httplibrary.request.PostRequest

/**
 * Created by jhj on 2018-2-23 0023;
 */
object HttpCall {

    @SuppressLint("StaticFieldLeak")
    private var httpRequest = HttpRequest()

    fun init(context: Context): Attribute {
        httpRequest = HttpRequest()
        return httpRequest.init(context)
    }

    fun get(url: String): GetRequest {
        return httpRequest.get(url)
    }

    fun post(url: String): PostRequest {
        return httpRequest.post(url)
    }

    fun download(url: String) {

    }

    fun cancel(tag: Any) {
        httpRequest.cancel(tag)
    }

    fun cancelAll() {
        httpRequest.cancelAll()
    }

}