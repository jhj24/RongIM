package com.jhj.httplibrary.request

import android.content.Context
import com.jhj.httplibrary.httpcall.Attribute
import com.jhj.httplibrary.model.HttpMethod
import com.jhj.httplibrary.utils.HttpUtils
import okhttp3.RequestBody
import java.io.File

/**
 * Post请求
 * Created by jhj on 2018-2-23 0023;
 */

class PostRequest(context: Context?, url: String, attribute: Attribute?) : BaseRequest<PostRequest>(context, url, attribute) {


    fun addFile(key: String, file: File): PostRequest {
        params.put(key, file)
        return this
    }

    fun addFiles(key: String, files: List<File>): PostRequest {
        params.put(key, files)
        return this
    }


    override fun getRequestUrl(): String {
        return url
    }

    override fun getMethod(): HttpMethod {
        return HttpMethod.POST
    }

    override fun generateRequest(): RequestBody? {
        return HttpUtils.generateRequestBody(params)
    }


}
