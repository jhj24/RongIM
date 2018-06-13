package com.jhj.httplibrary.httpcall

import android.content.Context
import com.jhj.httplibrary.request.GetRequest
import com.jhj.httplibrary.request.PostRequest
import java.lang.NullPointerException

/**
 * Created by jhj on 2018-2-23 0023;
 */

class HttpRequest {

    private var attribute: Attribute? = null
    private var context: Context? = null

    fun init(context: Context?): Attribute {
        this.context = context
        this.attribute = Attribute()
        return attribute as Attribute
    }

    fun get(url: String): GetRequest {
        if (context == null) {
            throw NullPointerException("When using HttpCall, it must be initialized in Application!")
        }

        if (attribute == null) {
            throw NullPointerException("When using HttpCall, it must be initialized in Application!")
        }
        return GetRequest(context, url, attribute)
    }

    fun post(url: String): PostRequest {
        if (context == null) {
            throw NullPointerException("When using HttpCall, it must be initialized in Application!")
        }

        if (attribute == null) {
            throw NullPointerException("When using HttpCall, it must be initialized in Application!")
        }
        return PostRequest(context, url, attribute)
    }

    /**
     * 根据tag取消网络请求
     */
    fun cancel(tag: Any?) {
        attribute?.let {
            if (tag == null) return

            it.getClient().dispatcher()?.queuedCalls()?.forEach {
                if (tag == it.request().tag()) {
                    it.cancel()
                }
            }

            it.getClient().dispatcher()?.runningCalls()?.forEach {
                if (tag == it.request().tag()) {
                    it.cancel()
                }
            }
        }
    }

    /**
     * 取消所有的网络请求
     */
    fun cancelAll() {
        attribute?.let {

            it.getClient().dispatcher()?.queuedCalls()?.forEach {
                it.cancel()
            }
            it.getClient().dispatcher()?.runningCalls()?.forEach {
                it.cancel()
            }
        }
    }

}
