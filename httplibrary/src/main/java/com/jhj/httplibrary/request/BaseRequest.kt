package com.jhj.httplibrary.request

import android.content.Context
import android.os.Handler
import android.util.Log
import com.jhj.httplibrary.callback.base.BaseProgressCallback
import com.jhj.httplibrary.callback.base.HttpCallback
import com.jhj.httplibrary.httpcall.Attribute
import com.jhj.httplibrary.model.HttpHeaders
import com.jhj.httplibrary.model.HttpMethod
import com.jhj.httplibrary.model.HttpParams
import com.jhj.httplibrary.model.Progress
import okhttp3.*
import org.jetbrains.anko.runOnUiThread

/**
 * 网络请求基类
 * Created by jhj on 2017-12-26 0026.
 */
abstract class BaseRequest<T : BaseRequest<T>>(context: Context?, url: String, attribute: Attribute?) {

    private val context: Context
    protected var url: String = ""
    private var headers: HttpHeaders = HttpHeaders()
    protected var params: HttpParams = HttpParams()
    private val builder = OkHttpClient.Builder()
    private var okHttpClient: OkHttpClient? = null
    private val mHandler = Handler()
    private var tag: Any = Any()

    init {
        if (url.isBlank()) {
            throw NullPointerException("url can not be null.")
        }

        if (attribute == null) {
            throw NullPointerException("When using HttpCall, it must be initialized in Application!")
        }

        if (context == null) {
            throw NullPointerException("When using HttpCall, it must be initialized in Application!")
        }

        this.url = url
        this.context = context

        attribute.getParams()?.let {
            this.params.putAll(it)
        }

        attribute.getHeaders()?.let {
            this.headers.putAll(it)
        }
        okHttpClient = attribute.getClient()

    }


    @Suppress("UNCHECKED_CAST")
    fun tag(tag: Any): T {
        this.tag = tag
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun addHeader(key: String, value: String): T {
        headers.put(key, value)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun addHeaders(map: Map<String, String>): T {
        headers.putAll(map)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun addHeaders(httpHeaders: HttpHeaders): T {
        headers.putAll(httpHeaders)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun addParam(key: String, value: String): T {
        params.put(key, value)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun addParams(map: HashMap<String, String>): T {
        params.putAll(map)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun addParams(httpParams: HttpParams): T {
        params.putAll(httpParams)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setClient(client: OkHttpClient): T {
        this.okHttpClient = client
        return this as T
    }


    /**
     * 获取Call对象
     */
    private fun getCall(body: RequestBody?): Call? {
        val request = Request.Builder()
                .url(getRequestUrl())
                .tag(tag)
                .method(getMethod().method, body)
                .headers(appendHeaders())
                .build()
        return okHttpClient?.newCall(request)

    }

    /**
     * 处理请求头
     */
    private fun appendHeaders(): Headers {
        val headerBuilder = Headers.Builder()
        if (headers.headersMap.isEmpty()) return headerBuilder.build()
        for ((key, value) in headers.headersMap) {
            val sb = StringBuffer()
            //请求头部不支持
            value.forEach { char ->
                if (char <= '\u001f' || char >= '\u007f') {
                    sb.append(String.format("\\u%04x", char.toInt()))
                } else {
                    sb.append(char)
                }
            }

            headerBuilder.add(key, sb.toString())
        }
        return headerBuilder.build()
    }

    /**
     * 异步
     */
    fun enqueue(callback: Callback): Call? {

        if (callback is HttpCallback<*>){
            callback.onStart()
        }

        val body = if (callback is BaseProgressCallback<*>) {
            when {
                generateRequest() is FormBody -> progress(callback)
                generateRequest() is MultipartBody -> progress(callback)
                else -> progress(callback)
            }
        } else {
            generateRequest()
        }

        val call = getCall(body)
        call?.enqueue(callback)
        return call
    }

    /**
     * 上传进度
     */
    private fun progress(callback: BaseProgressCallback<*>): Progress? {
        return generateRequest()?.let {
            Progress(it, { current, total ->
                context.runOnUiThread {
                    callback.onProgress(current, total)
                }
                Log.w("progress", (current * 100.0 / total).toInt().toString())

            })
        }
    }

    /**
     * 同步
     */
    fun execute() {
        val body = generateRequest()
        getCall(body)?.execute()
    }

    protected abstract fun getRequestUrl(): String

    protected abstract fun getMethod(): HttpMethod

    protected abstract fun generateRequest(): RequestBody?


}