package com.jhj.httplibrary.httpcall

import com.jhj.httplibrary.interceptor.LoggerInterceptor
import com.jhj.httplibrary.model.HttpHeaders
import com.jhj.httplibrary.model.HttpParams

import okhttp3.OkHttpClient

/**
 * 用于设置通用的请求头、请求体以及OkHttpClient
 *
 * Created by jhj on 2018-2-23 0023;
 */

class Attribute {

    private var params: HttpParams? = null
    private var headers: HttpHeaders? = null
    private var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(LoggerInterceptor()).build()

    fun getParams(): HttpParams? {
        return params
    }

    fun setParams(params: HttpParams): Attribute {
        this.params = params
        return this
    }

    fun getHeaders(): HttpHeaders? {
        return headers
    }

    fun setHeaders(headers: HttpHeaders): Attribute {
        this.headers = headers
        return this
    }

    fun getClient(): OkHttpClient {
        return client
    }

    fun setClient(client: OkHttpClient): Attribute {
        this.client = client
        return this
    }

    fun build(): Attribute {
        return this
    }

}
